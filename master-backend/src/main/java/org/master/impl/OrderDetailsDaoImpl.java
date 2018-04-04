package org.master.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.master.model.OrderDetails;

@Stateless
public class OrderDetailsDaoImpl {
	
	public long id;
	
	@PersistenceContext(unitName = "shop")
	EntityManager entityManager;

	public List<OrderDetails> getAllOrders() {
		return entityManager.createQuery("SELECT o FROM OrderDetails o", OrderDetails.class).getResultList();
	}
	
	public void addOrderDetails(OrderDetails orderDetails) {
		entityManager.persist(orderDetails);
		entityManager.flush();
		id = orderDetails.getId();
	}
}
