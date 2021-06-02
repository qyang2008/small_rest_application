package com.qyang.donutpriorityqueue.controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qyang.donutpriorityqueue.config.Properties;
import com.qyang.donutpriorityqueue.model.DonutOrderRequest;
import com.qyang.donutpriorityqueue.model.DonutOrderResponse;
import com.qyang.donutpriorityqueue.model.OrderItem;
import com.qyang.donutpriorityqueue.services.DonutPriorityQueueService;


/**
 *
 */
@RestController
public class DonutPriorityQueueControllerImpl implements DonutPriorityQueueController {

	@Autowired
	DonutPriorityQueueService dservice;

	// add order items to the queue
	@RequestMapping(value = Properties.PATH_ADD_ORDER, method = RequestMethod.POST, produces = {
			Properties.CONTENT_TYPE_APPLICATION_JSON }, consumes = { Properties.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<DonutOrderResponse> addOrder(@RequestBody DonutOrderRequest request) {

		final DonutOrderResponse response = new DonutOrderResponse();
		final Boolean successStatus = dservice.addOrder(request.getClientId(), request.getQuantity());

		if (successStatus) {

			response.setSuccess(successStatus);
			response.setMessage(Properties.MSG_ORDER_WAS_ADDED_IN_THE_QUEUE);

		} else {

			response.setMessage(Properties.MSG_ORDER_WAS_NOT_ADDED_IN_THE_QUEUE);
			return ResponseEntity.noContent().header(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC, response.getMessage())
					.build();

		}

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// get all entries in the queue with the approximate wait time
	@RequestMapping(value = Properties.PATH_GET_ORDER_LIST, method = RequestMethod.GET, produces = {
			Properties.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<DonutOrderResponse> getOrderList() {

		final DonutOrderResponse response = new DonutOrderResponse();

		final List<OrderItem> orderList = dservice.getOrderList();

		if (!orderList.isEmpty()) {

			response.setSuccess(Boolean.TRUE);
			response.setQueue(orderList);
			return ResponseEntity.ok(response);

		}

		response.setMessage(Properties.MSG_THE_QUEUE_IS_EMPTY);
		return ResponseEntity.noContent().header(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC, response.getMessage())
				.build();

	}

	// cancel an order and don't return anything
	@RequestMapping(value = Properties.PATH_REMOVE, method = RequestMethod.DELETE, produces = {
			Properties.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<Void> remove(

			@PathVariable("clientId") @Valid @NotNull(message = Properties.ERR_MSG_CLIENTID_CAN_NOT_BE_NULL) @Pattern(regexp = "[0-9]+", message = Properties.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_CLIENTID) @Min(1L) @Max(20000L) final Long clientId) {

		final Long result = dservice.remove(clientId);

		if (result.toString().equals(clientId.toString())) {

			return ResponseEntity.status(HttpStatus.RESET_CONTENT)
					.header(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC,
							String.format(Properties.MSG_DEQUEUED_ORDER_WITH_CLIENTID, clientId.toString()))
					.build();

		}

		return ResponseEntity.noContent().header(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC,
				String.format(Properties.MSG_NO_Order_WAS_FOUND_FOR_CLIENTID, clientId)).build();

	}

	

	// An endpoint for the client to check his queue position and approximate wait
	// time. Counting starts at 1.
	@RequestMapping(value = Properties.PATH_GET_POSITION, method = RequestMethod.GET, produces = {
			Properties.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<DonutOrderResponse> getPositionAndWaitTime(

			@PathVariable("clientId") @Valid @NotNull(message = Properties.ERR_MSG_CLIENTID_CAN_NOT_BE_NULL) @Pattern(regexp = "[0-9]+", message = Properties.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_CLIENTID) @Min(1L) @Max(20000L) final Long clientId) {

		final Integer position = dservice.getPosition(clientId);
		final Long timeInQueue= dservice.getTimeInQueue(clientId);
		final DonutOrderResponse response = new DonutOrderResponse();

		if (position != -1) {

			response.setPosition(position + 1);
			response.setTimeInQueue(timeInQueue);
			response.setSuccess(Boolean.TRUE);

		} else {

			return ResponseEntity.noContent().header(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC,
					String.format(Properties.MSG_NO_Order_WAS_FOUND_FOR_CLIENTID, clientId)).build();

		}

		return ResponseEntity.ok(response);
	}
	

	//To get the information for next delivery
	@RequestMapping(value = Properties.PATH_NEXT_DELIVERY, method = RequestMethod.GET, produces = {
			Properties.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<DonutOrderResponse> retrieveNextDelivery() {

		final DonutOrderResponse response = new DonutOrderResponse();

		final List<OrderItem> nextDeliveryList = dservice.getNextDeliveryList();

		if (!nextDeliveryList.isEmpty()) {

			response.setSuccess(Boolean.TRUE);
			response.setQueue(nextDeliveryList);
			return ResponseEntity.ok(response);

		}

		response.setMessage(Properties.MSG_THE_QUEUE_IS_EMPTY);
	
		return ResponseEntity.noContent().header(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC, response.getMessage())
				.build();
	}
}
