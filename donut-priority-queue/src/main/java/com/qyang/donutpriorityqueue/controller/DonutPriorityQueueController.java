package com.qyang.donutpriorityqueue.controller;

import org.springframework.http.ResponseEntity;

import com.qyang.donutpriorityqueue.model.DonutOrderRequest;
import com.qyang.donutpriorityqueue.model.DonutOrderResponse;

/**
 *    interface for controller
 */
public interface DonutPriorityQueueController {

	/**
	 * For adding order items to the queue
	 *
	 * @param DonutOrderRequest
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<DonutOrderResponse> addOrder(DonutOrderRequest request);

	/**
	 * For getting the list of orderItems in the queue.
	 *
	 * @return {@link ResponseEntity}
	 */

	public ResponseEntity<DonutOrderResponse> getOrderList();

	/**
	 * For removing a speciﬁc ID from the queue.
	 * 
	 * @param id
	 * @return {@link ResponseEntity}
	 */

	public ResponseEntity<Void> remove(Long id);



	/**
	 * * To get the position and wait time of a specific ID in the queue.
	 * 
	 * @param id
	 * @return {@link ResponseEntity}
	 */

	public ResponseEntity<DonutOrderResponse> getPositionAndWaitTime(Long id);

	/**
	 * * To get the information for next delivery
	 * 
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<DonutOrderResponse> retrieveNextDelivery();
}
