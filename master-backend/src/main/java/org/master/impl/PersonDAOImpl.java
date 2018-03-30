package org.master.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.master.model.Person;

@Stateless
public class PersonDAOImpl{
	
	private long id;
	
	@PersistenceContext(unitName="shop")
	EntityManager entityManager;

	public List<Person> getAllPersons() {
		return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
	}

	public Person getPersonById() {
		return entityManager.find(Person.class, id);
	}
	
	public void addPerson(Person person) {
		entityManager.persist(person);
		entityManager.flush();
		id = person.getId();
	}
//
//	public void updatePerson(Person person) {
//		Person per = getPersonById(person.getId());
//		per.setName(person.getName());
//		per.setSurname(person.getSurname());
//		per.setEmail(person.getEmail());
//		per.setAddress(person.getAddress());
//		entityManager.flush();
//	}

//	public void deletePerson(long personId) {
//		entityManager.remove(personId);
//	}
//
//	public boolean personExists(long personId) {
//		TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p WHERE p.id = ?1",
//				Person.class);
//		boolean vrednost = false;
//		try {
//			query.setParameter(1, personId).getSingleResult();
//			vrednost = true;
//		} catch (NoResultException e) {
//			// TODO: handle exception
//		}
//		return vrednost;
//	}

}
