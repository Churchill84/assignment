package org.master.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.master.report.OrderReport;

@Stateless
public class OrderReportDaoImpl {

	private List<OrderReport> reportList;
	
	@PersistenceContext(unitName = "shop")
	EntityManager entityManager;

	public List<OrderReport> getOrderReport() {
		List<Object[]> list = entityManager.createNativeQuery("select sum(od.quantity), pr.description, pr.image, p.name, p.address, p.email, p.id as personID, pr.id\r\n" + 
				"from \"order\" o, orderdetails od, person p, product pr\r\n" + 
				"where o.id = od.orderid and p.id = o.personid and pr.id = od.productid\r\n" + 
				"group by p.id,  pr.id").getResultList();
		
		reportList = new ArrayList<>();
		for(Object[] result : list){			
			int i = 0;
			OrderReport or =new OrderReport();
			or.setQuantity(((double)result[i++]));
			or.setProductDesc((String)result[i++]);
			or.setProductImage((String)result[i++]);
			or.setPersonName((String)result[i++]);
			or.setPersonAddress((String)result[i++]);
			or.setPersonEmail((String)result[i++]);			
			reportList.add(or);
			
		}
		return reportList;
	}
}
