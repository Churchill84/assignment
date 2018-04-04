package org.master.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.master.report.OrderReport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean(name="orderDetialsBean")
@ViewScoped
public class OrderDetialsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CloseableHttpClient CLIENT = HttpClients.createDefault();
	List<OrderReport> orderReportList;
	
	public List<OrderReport> getOrderReportList() {
		if (orderReportList == null) {
			orderReportList = new ArrayList<>();
			CLIENT = HttpClients.createDefault();
			HttpGet request = new HttpGet("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getOrderReport");

			// StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.addHeader("charset", "UTF-8");
			// request.setEntity(params);
			HttpResponse response;

			try {

				response = (HttpResponse) CLIENT.execute(request);
				HttpEntity entity = response.getEntity();
				ObjectMapper mapper = new ObjectMapper();
//				mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
				mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
				OrderReport[] model = mapper.readValue((EntityUtils.toString(entity)), OrderReport[].class);

				for (OrderReport report : model) {
					orderReportList.add(report);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderReportList;
	}
}
