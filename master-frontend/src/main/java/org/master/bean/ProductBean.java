package org.master.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.master.front.model.Order;
import org.master.front.model.OrderDetails;
import org.master.front.model.Person;
import org.master.front.model.Product;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean(name="productBean")
@ViewScoped
public class ProductBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CloseableHttpClient CLIENT = HttpClients.createDefault();

	List<Product> productList;
	
	List<Product> productAllList;

	List<Product> productBucketList;

	private Product product = new Product();

	private Product selectedProduct;

	HttpSession session;

	@PostConstruct
	public void init() {
		productBucketList = new ArrayList<>();
		session = getSession();
		selectedProduct = new Product();
		selectedProduct.setQuantity(0);
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public void addProductToBucket() {

		if (session == null) {
			return;
		}

		session.setAttribute("bucketList", productBucketList);
	}

	public List<Product> getProductBucketList() {
		return productBucketList;
	}

	public void setProductBucketList(List<Product> productBucketList) {
		this.productBucketList = productBucketList;
	}

	@SuppressWarnings("unchecked")
	public List<Product> getProductFromBucket() {
		// HttpSession session = getSession();
		// if (session == null) {
		// return null;
		// }
		//
		// System.out.println(((List<Product>)
		// session.getAttribute("bucketList")).size());

		return (List<Product>) session.getAttribute("bucketList");
	}

	public void showBucketPage() {
		try {
			addProductToBucket();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/userproductbucket.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateProductQuantity(Product product) {
		session.setAttribute(String.valueOf(product.getId()), product.getQuantity());
	}

	public List<Product> getProductList() {
		if (productList == null) {

			productList = new ArrayList<>();
			CLIENT = HttpClients.createDefault();
			HttpGet request = new HttpGet("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getProducts");

			// StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.addHeader("charset", "UTF-8");
			// request.setEntity(params);
			HttpResponse response;

			try {

				response = (HttpResponse) CLIENT.execute(request);
				HttpEntity entity = response.getEntity();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
				Product[] model = mapper.readValue((EntityUtils.toString(entity)), Product[].class);

				for (Product product : model) {
					productList.add(product);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return productList;
	}
	
	public List<Product> getProductAllList() {
		if (productAllList == null) {

			productAllList = new ArrayList<>();
			CLIENT = HttpClients.createDefault();
			HttpGet request = new HttpGet("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getAllProducts");

			// StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.addHeader("charset", "UTF-8");
			// request.setEntity(params);
			HttpResponse response;

			try {

				response = (HttpResponse) CLIENT.execute(request);
				HttpEntity entity = response.getEntity();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
				Product[] model = mapper.readValue((EntityUtils.toString(entity)), Product[].class);

				for (Product product : model) {
					productAllList.add(product);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return productAllList;
	}

	public Product deliteProductRecord(Product product) {
		try {
			CLIENT = HttpClients.createDefault();
			HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/deleteProduct");
			JSONObject json = new JSONObject();
			json.put("id", product.getId());
			json.put("description", product.getDescription());
			json.put("price", product.getPrice());
			json.put("stock", product.getStock());
			json.put("image", product.getImage());
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			product = mapper.readValue((EntityUtils.toString(entity)), Product.class);
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));

		} catch (IOException ex) {
			RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));
			// ex.printStackTrace();
		}

		productList = null;
		return product;
	}

	public Product insertProductRecord() {
		try {
			CLIENT = HttpClients.createDefault();
			HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/addProduct");
			JSONObject json = new JSONObject();
			// json.put("id", 10);
			json.put("description", product.getDescription());
			json.put("price", product.getPrice());
			json.put("stock", product.getStock());
			json.put("image", product.getImage());
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			product = mapper.readValue((EntityUtils.toString(entity)), Product.class);
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));

		} catch (IOException ex) {
			RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data unsuccesfully saved"));
			// ex.printStackTrace();
		}

		productList = null;
		return product;
	}

	public Order insertOrder(Order order) {
		try {
			CLIENT = HttpClients.createDefault();
			HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/addOrder");
			// JSONObject jsonPro = new JSONObject();
			// jsonPro.put("id", order.getPerson().getId());
			// jsonPro.put("name", order.getPerson().getName());
			// jsonPro.put("surname", order.getPerson().getSurname());
			// jsonPro.put("email", order.getPerson().getEmail());
			// jsonPro.put("address", order.getPerson().getAddress());

			JSONObject json = new JSONObject();
			// json.put("id", 10);
			json.put("personid", order.getPersonid());
			json.put("datecreate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(order.getDatecreate()));
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValueAsString(order);
			order = mapper.readValue((EntityUtils.toString(entity)), Order.class);
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));

		} catch (IOException ex) {
			RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data unsuccesfully saved"));
			// ex.printStackTrace();
		}
		session.setAttribute("order", getBackOrder(order));
		return order;
	}

	public Order getBackOrder(Order order) {
		CLIENT = HttpClients.createDefault();
		HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getOrder");

		// StringEntity params = new StringEntity(json.toString(), "UTF-8");
		request.addHeader("content-type", "application/json;charset=UTF-8");
		request.addHeader("charset", "UTF-8");
		// request.setEntity(params);
		HttpResponse response;
		try {

			response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
			order = mapper.readValue((EntityUtils.toString(entity)), Order.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}

	public OrderDetails insertOrderDetails(OrderDetails orderDetails) {
		try {
			CLIENT = HttpClients.createDefault();
			HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/addOrderDetails");
			JSONObject json = new JSONObject();
			// json.put("id", 10);
			System.out.println(orderDetails.getProductid());
			System.out.println(orderDetails.getOrderid());
			System.out.println(orderDetails.getQuantity());

			json.put("productid", orderDetails.getProductid());
			json.put("orderid", orderDetails.getOrderid());
			json.put("quantity", orderDetails.getQuantity());
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			orderDetails = mapper.readValue((EntityUtils.toString(entity)), OrderDetails.class);
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));

		} catch (IOException ex) {
			RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data unsuccesfully saved"));
			// ex.printStackTrace();
		}
		return orderDetails;
	}

	public Product editProductRecord() {

		try {
			CLIENT = HttpClients.createDefault();
			HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/editProduct");
			JSONObject json = new JSONObject();
			// json.put("id", 10);
			json.put("description", selectedProduct.getDescription());
			json.put("id", selectedProduct.getId());
			json.put("price", selectedProduct.getPrice());
			json.put("stock", selectedProduct.getStock());
			json.put("image", selectedProduct.getImage());
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			product = mapper.readValue((EntityUtils.toString(entity)), Product.class);

			// RequestContext context = RequestContext.getCurrentInstance();
			// context.execute("PF('editProductDlg').hide();");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));

		} catch (IOException ex) {
			RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data unsuccesfully saved"));
			// ex.printStackTrace();
		}

		productList = null;
		return product;
	}

	public void createBucketList(Product product) {
		if (!productBucketList.contains(product)) {
			product.setQuantity(0);
			System.out.println("ovde puni bucket");
			productBucketList.add(product);
			session.setAttribute("productBucketList", productBucketList);

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Product already in bucket"));
		}

	}

	public void logout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(
					FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectProduct(Product p) {
		selectedProduct = p;
	}

	@SuppressWarnings("unchecked")
	public void createOrderWithOrderDetails() {
		if (session.getAttribute("person") != null) {
			Order order = new Order();
			order.setPersonid(((Person) session.getAttribute("person")).getId());
			order.setDatecreate(new Date());
			Order or = insertOrder(order);
			productBucketList = (List<Product>) session.getAttribute("productBucketList");
			System.out.println("productBucketList " + productBucketList.size());

			for (Product product : productBucketList) {
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setQuantity((double) session.getAttribute(String.valueOf(product.getId())));
				orderDetails.setProductid(product.getId());
				orderDetails.setOrderid((long) ((Order) session.getAttribute("order")).getId());
				insertOrderDetails(orderDetails);

				selectedProduct = product;
				selectedProduct.setStock(product.getStock() - orderDetails.getQuantity());
				editProductRecord();

			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/registerPerson.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/userproduct.xhtml");
		} catch (Exception e) {
		}

	}
}
