package com.webapplication.JSONBeans;

import java.util.Date;

public class DateBean {
	
	private Long companyID;
	
	private Date startDate;
	private Date endDate;
	
	private String strStartDate;
	private String strEndDate;
	
	public DateBean() {
		
	}

	
	
	

	public DateBean(Long companyID, Date startDate, Date endDate, String strStartDate, String strEndDate) {
		
		this.companyID = companyID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.strStartDate = strStartDate;
		this.strEndDate = strEndDate;
	}





	public Long getCompanyID() {
		return companyID;
	}



	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}



	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStrStartDate() {
		return strStartDate;
	}

	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}
	
	
	
}
