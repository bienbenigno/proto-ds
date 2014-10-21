package com.protods.wo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Event extends BaseModel {
	
	private static final long serialVersionUID = 3448388818160377847L;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd");
	private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("EEE");
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM");
	private static final SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	private String event;
	
	private Date createdDate;
	
	private Boolean completed;
	
	private Date lastModifiedDate;
	
	public String getDayOfWeek() {
		if (createdDate != null) {
			return DAY_FORMAT.format(createdDate);
		}
		return "";
	}
	
	public String getDayOfMonth() {
		if (createdDate != null) {
			return DATE_FORMAT.format(createdDate);
		}
		return "";
	}
	
	public String getMonth() {
		if (createdDate != null) {
			return MONTH_FORMAT.format(createdDate);
		}
		return "";
	}
	
	public String getDateString() {
		if (createdDate != null) {
			return LONG_FORMAT.format(createdDate);
		}
		return "";
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the completed
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result
				+ ((getExternalId() == null) ? 0 : getExternalId().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (getExternalId() == null) {
			if (other.getExternalId() != null)
				return false;
		} else if (!getExternalId().equals(other.getExternalId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [event=" + event + ", createdDate=" + createdDate
				+ ", completed=" + completed + ", lastModifiedDate="
				+ lastModifiedDate + ", externalId=" + getExternalId()
				+ ", deleted=" + getDeleted() + ", synched="
				+ getSynched() + ", email=" + getEmail() + "]";
	}

}
