package org.gutaramwari.registration.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gutaramwari.registration.dao.RegistrationDao;
import org.gutaramwari.registration.exception.InvalidFileTypeException;
import org.gutaramwari.registration.model.Attendant;
import org.gutaramwari.registration.model.RegistrationStatus;
import org.springframework.web.multipart.MultipartFile;

public class RegistrationService {
	
	private static final List<String> spreadSheetTypes = Arrays.asList("xlsx", "xls");
	
	private RegistrationDao registrationDao;
	
	public void setRegistrationDao(RegistrationDao registrationDao) {
		this.registrationDao = registrationDao;
	}
	
	public RegistrationStatus importRegister(MultipartFile register, String date) {
		
		// Get file extension
		String fileExtension = register.getOriginalFilename().split("\\.")[1];
		
		// Check if files type is a valid spread sheet
		if(!spreadSheetTypes.contains(fileExtension))
			throw new InvalidFileTypeException(register.getOriginalFilename() + " is not a valid spread sheet.");
		
		// Clear attendants table
		registrationDao.deleteAll();
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(register.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				
				// Create and save Attendant
				Date parsed;
				Attendant attendant = new Attendant();
				
				String attendentName = row.getCell(0).getStringCellValue();
				Double attendentAge = row.getCell(1) == null ? 0.0 : row.getCell(1).getNumericCellValue();	
				XSSFCell cardNumberCell = row.getCell(2) == null ? null : row.getCell(2);
				XSSFCell stringContentCell = cardNumberCell == null ? null : (cardNumberCell.getCellType() == CellType.STRING ? cardNumberCell : null);
				String attendentCardNumber = stringContentCell == null ? "" : stringContentCell.getStringCellValue();
				String attendentBranch = row.getCell(3).getStringCellValue();
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					parsed = format.parse(date);
					attendant.setName(attendentName);
					attendant.setAge(attendentAge.intValue());
					attendant.setCardNumber(attendentCardNumber);
					attendant.setBranch(attendentBranch);
					attendant.setAttended(false);
					attendant.setDate(parsed);
					registrationDao.save(attendant);
				} catch (ParseException e) {
					
				}
			}
		} catch (IOException e) {
			// Delete saved and throw Exception
			registrationDao.deleteAll();
			throw new InvalidFileTypeException("Could not import " + register.getOriginalFilename());
		}
		return new RegistrationStatus(200, "Success");
	}

	public Attendant saveAttendent(Attendant attendant) {
		return this.registrationDao.save(attendant);
	}

	public List<Attendant> getAllAttendants() {
		return this.registrationDao.getAll();
	}

	public Optional<Attendant> getAttendant(long id) {
		return this.registrationDao.get(id);
	}

	public void deleteAttendant(long id) {
		this.registrationDao.delete(id);
	}

}