package org.master.front.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long personid;
	private Date datecreate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPersonid() {
		return personid;
	}

	public void setPersonid(long personid) {
		this.personid = personid;
	}

	public Date getDatecreate() {
		return datecreate;
	}

	public void setDatecreate(Date datecreate) {
		this.datecreate = datecreate;
	}

}
