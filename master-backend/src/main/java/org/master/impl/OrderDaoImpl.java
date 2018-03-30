package org.master.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.master.model.Order;

@Stateless
public class OrderDaoImpl {

	private long id;
	
	@PersistenceContext(unitName = "shop")
	EntityManager entityManager;

	public List<Order> getAllOrders() {
		return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}

	public void addOrder(Order order) {
		entityManager.persist(order);
		entityManager.flush();
		id = order.getId();
	}

	public Order getOrderById() {
		return entityManager.find(Order.class, id);
	}
}
