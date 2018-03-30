package org.master.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.master.front.model.Person;
import org.master.front.model.Product;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Named
@ViewScoped
public class PersonBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CloseableHttpClient CLIENT = HttpClients.createDefault();

	List<Person> personList;
	private Person person = new Person();
	HttpSession session;

	@PostConstruct
	public void init() {
		session = getSession();
		personList = new ArrayList<>();
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public Person insertPerson() {
		try {
			CLIENT = HttpClients.createDefault();
			HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/addPerson");
			JSONObject json = new JSONObject();
			// json.put("id", 10);
			json.put("name", person.getName());
			json.put("surname", person.getSurname());
			json.put("email", person.getEmail());
			json.put("address", person.getAddress());
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			person = mapper.readValue((EntityUtils.toString(entity)), Person.class);
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data succesfully saved"));

		} catch (IOException ex) {
			RequestContext.getCurrentInstance().addCallbackParam("saved", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data unsuccesfully saved"));
			// ex.printStackTrace();
		}

		personList = null;
		session.setAttribute("person", getBackPerson(person));
		return person;
	}

	public Person getBackPerson(Person person) {
		CLIENT = HttpClients.createDefault();
		HttpPost request = new HttpPost("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getPerson");

		// StringEntity params = new StringEntity(json.toString(), "UTF-8");
		request.addHeader("content-type", "application/json;charset=UTF-8");
		request.addHeader("charset", "UTF-8");
		// request.setEntity(params);
		HttpResponse response;
		Person model = null;
		try {

			response = (HttpResponse) CLIENT.execute(request);
			HttpEntity entity = response.getEntity();
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
			model = mapper.readValue((EntityUtils.toString(entity)), Person.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public List<Person> getPersonList() {

		CLIENT = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getPersons");

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
			Person[] model = mapper.readValue((EntityUtils.toString(entity)), Person[].class);

			for (Person person : model) {
				personList.add(person);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
