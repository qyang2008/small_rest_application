package com.qyang.donutpriorityqueue.model;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.qyang.donutpriorityqueue.config.Properties;


@Valid
public class DonutOrderRequest {

	@NotNull(message = Properties.ERR_MSG_CLIENTID_CAN_NOT_BE_NULL)
	@Digits(fraction = 0, integer = 5, message = Properties.ERR_MSG_CLIENTID_CAN_HAVE_MAXIMUM_5_DIGITS_AND_NO_DECIMALS)
	@Range(min = 1L, max = 20000L, message = Properties.ERR_MSG_CLIENTID_CAN_NOT_BE_MORE_THAN_20000)
	private Long clientId;

	@NotNull(message = Properties.ERR_MSG_QUANTITY_CAN_NOT_BE_NULL)
	@Digits(fraction = 0, integer = 2, message = Properties.ERR_MSG_QUANTITY_CAN_HAVE_MAXIMUM_2_DIGITS_AND_NO_DECIMALS)
	@Range(min = 1, max = 50, message = Properties.ERR_MSG_QUANTITY_CAN_NOT_BE_MORE_THAN_50)
	private Integer quantity;

	public DonutOrderRequest(Long clientId, Integer quantity) {
		this.clientId = clientId;
		this.quantity = quantity;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


}
