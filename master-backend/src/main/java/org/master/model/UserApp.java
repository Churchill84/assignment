package org.master.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserApp")
public class UserApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int ROLE_ADMIN = 1;
	public static final int ROLE_USER = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Column(name = "username", length = 20, nullable = false)
	private String userName;

	@Column(name = "password", length = 20, nullable = false)
	private String password;

	@Column(name = "status", length = 20, nullable = false)
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
