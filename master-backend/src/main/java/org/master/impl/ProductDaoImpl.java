package org.master.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.master.model.Product;

@Stateless
public class ProductDaoImpl {
	
	@PersistenceContext(unitName = "shop")
	EntityManager entityManager;

	public List<Product> getAllProducts() {
		return entityManager.createQuery("SELECT p FROM Product p where p.stock > 0.00", Product.class).getResultList();
	}
	
	public List<Product> getUserAllProducts() {
		return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
	}

	public void addProduct(Product product) {
		entityManager.persist(product);
	}

	public void editProduct(Product product) {
		entityManager.merge(product);
	}

	public void deleteProduct(Product product) {
		entityManager.remove(entityManager.contains(product) ? product : entityManager.merge(product));
	}
}
