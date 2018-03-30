package org.master.rest;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.master.impl.OrderDaoImpl;
import org.master.impl.OrderDetailsDaoImpl;
import org.master.impl.OrderReportDaoImpl;
import org.master.impl.PersonDAOImpl;
import org.master.impl.ProductDaoImpl;
import org.master.impl.UserDaoImpl;
import org.master.model.Order;
import org.master.model.OrderDetails;
import org.master.model.Person;
import org.master.model.Product;

@Path("/")
@Stateless
public class RestService {

	@EJB
	PersonDAOImpl personDAO;
	@EJB
	UserDaoImpl userApp;
	@EJB
	ProductDaoImpl productDao;
	@EJB
	OrderDaoImpl orderDao;
	@EJB
	OrderReportDaoImpl orderReportDao;
	@EJB
	OrderDetailsDaoImpl orderDetailsDaoImpl;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getPersons")
	public Response getPersons() {
		HashMap<String, Object> json = null;

		try {
			return Response.ok().entity(personDAO.getAllPersons()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getPerson")
	public Response getPerson(Person person) {
		HashMap<String, Object> json = null;

		try {
			return Response.ok().entity(personDAO.getPersonById()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getOrder")
	public Response getOrder(Order order) {
		HashMap<String, Object> json = null;

		try {
			return Response.ok().entity(orderDao.getOrderById()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getProducts")
	public Response getProducts() {
		HashMap<String, Object> json = null;

		try {
			return Response.ok().entity(productDao.getAllProducts()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getOrderReport")
	public Response getOrderReport() {
		HashMap<String, Object> json = null;

		try {
			return Response.ok().entity(orderReportDao.getOrderReport()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getAllProducts")
	public Response getUserAllProducts() {
		HashMap<String, Object> json = null;

		try {
			return Response.ok().entity(productDao.getUserAllProducts()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("addProduct")
	public Response addProduct(Product product) {
		try {
			productDao.addProduct(product);
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("addPerson")
	public Response addPerson(Person person) {
		try {
			personDAO.addPerson(person);
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("editProduct")
	public Response editProduct(Product product) {
		try {
			productDao.editProduct(product);
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("deleteProduct")
	public Response deleteProduct(Product product) {
		try {
			
			System.out.println("daj ovde nesto " + product.getDescription());
			
			productDao.deleteProduct(product);
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("addOrder")
	public Response addOrder(Order order) {
		try {
			
			System.out.println(order.getPersonid());
			System.out.println(order.getDatecreate());
			orderDao.addOrder(order);
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("addOrderDetails")
	public Response addOrderDetail(OrderDetails orderDetails) {
		try {
			orderDetailsDaoImpl.addOrderDetails(orderDetails);
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getOrders")
	public Response getOrders() {
		HashMap<String, Object> json = null;

		try {
			json = new HashMap<>();
			json.put("listaUsera", "");
			return Response.ok().entity(orderDao.getAllOrders()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}


	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("getUsers")
	public Response getUsers() {
		HashMap<String, Object> json = null;

		try {
			json = new HashMap<>();
			json.put("listaUsera", "");
			return Response.ok().entity(userApp.getAllUsers()).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok().entity(json).build();
		}

	}

}
