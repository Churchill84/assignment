package org.master.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.master.model.UserApp;

@Stateless
public class UserDaoImpl {
	
	@PersistenceContext(unitName="shop")
	EntityManager entityManager;

	public List<UserApp> getAllUsers() {
		return entityManager.createQuery("SELECT u FROM UserApp u", UserApp.class).getResultList();
	}
}
