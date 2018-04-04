package org.master.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.master.front.model.UserApp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean(name="userLoginBean")
@ViewScoped
public class UserLoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CloseableHttpClient CLIENT = HttpClients.createDefault();
	List<UserApp> userAppList;
	private String username;
	private String password;
	HttpSession session;

	@PostConstruct
	public void init() {
		userAppList = new ArrayList<>();
		session = getSession();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserApp> getUserList() {
		userAppList = new ArrayList<>();
		CLIENT = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8080/master-backend-0.0.1-SNAPSHOT/rest/getUsers");

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
			UserApp[] model = mapper.readValue((EntityUtils.toString(entity)), UserApp[].class);

			for (UserApp user : model) {
				userAppList.add(user);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userAppList;
	}

	public void login() {
		boolean loggedAdmin = false;
		boolean loggedUser = false;
		getUserList();

		for (UserApp userApp : userAppList) {
			if (userApp.getStatus() == UserApp.ROLE_ADMIN) {
				if (username != null && username.equals(userApp.getUserName()) && password != null
						&& password.equals(userApp.getPassword())) {
					loggedAdmin = true;
					session.setAttribute("user", userApp);
				} else {
					loggedAdmin = false;
				}
			} else if (userApp.getStatus() == UserApp.ROLE_USER) {
				if (username != null && username.equals(userApp.getUserName()) && password != null
						&& password.equals(userApp.getPassword())) {
					loggedUser = true;
					session.setAttribute("user", userApp);
				} else {
					loggedUser = false;
				}
			}

		}

		if (loggedAdmin && !loggedUser) {
			try {

				FacesContext.getCurrentInstance().getExternalContext()
						.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/product.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!loggedAdmin && loggedUser) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/userproduct.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void logout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/login-demo.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showProducts() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/userproduct.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<UserApp> getUserAppList() {
		return userAppList;
	}

	public void setUserAppList(List<UserApp> userAppList) {
		this.userAppList = userAppList;
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public void showOrderDetails() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/orderdetails.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
