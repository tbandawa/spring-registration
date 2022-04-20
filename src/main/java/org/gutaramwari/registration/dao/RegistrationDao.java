package org.gutaramwari.registration.dao;

import java.util.List;
import java.util.Optional;

import org.gutaramwari.registration.model.Attendant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RegistrationDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public Optional<Attendant> get(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return Optional.ofNullable((Attendant)session.get(Attendant.class, id));
	}

	@SuppressWarnings("unchecked")
	public List<Attendant> getAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Attendant> galleryList = session.createQuery("FROM Attendant a ORDER BY a.id ASC").list();
		return galleryList;
	}

	public Attendant save(Attendant attendent) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(attendent);
		return attendent;
	}

	public void delete(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Attendant attendent = (Attendant)session.load(Attendant.class, id);
		if (null != attendent) {
			session.delete(attendent);
		}
	}
	
	public void deleteAll() {
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("DELETE FROM Attendant");
	}

}