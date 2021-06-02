package com.qyang.donutpriorityqueue.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class is a POJO containing all possible response fields.
 * 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class DonutOrderResponse {

	private Long clientId;
	private List<OrderItem> queue;
	private Integer position;
	private Boolean success;
	private String message;
	private Long timeInQueue;

	public DonutOrderResponse() {
	}



	public List<OrderItem> getQueue() {
		return queue;
	}

	public void setQueue(List<OrderItem> queue) {
		this.queue = queue;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientID) {
		this.clientId = clientID;
	}


	public Long getTimeInQueue() {
		return timeInQueue;
	}


	public void setTimeInQueue(Long timeInQueue) {
		this.timeInQueue = timeInQueue;
	}
	
}
