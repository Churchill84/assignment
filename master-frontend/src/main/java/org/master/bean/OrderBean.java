package org.master.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.master.front.model.Order;
import org.master.front.model.Person;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Named
@ViewScoped
public class OrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CloseableHttpClient CLIENT = HttpClients.createDefault();
	List<Order> orderList;
	
	public List<Order> getOrderList() {
		if (orderList == null) {
			orderList = new ArrayList<>();
			CLIENT = HttpClients.createDefault();
			HttpGet request = new HttpGet("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getOrders");

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
				Order[] model = mapper.readValue((EntityUtils.toString(entity)), Order[].class);

				for (Order order : model) {
					orderList.add(order);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderList;
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
	
}
