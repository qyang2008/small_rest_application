package com.qyang.donutpriorityqueue.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.qyang.donutpriorityqueue.model.OrderItem;



@Component
public interface DonutPriorityQueueService {

	/**
	 * For adding an order to queue.
	 * 
	 * @param id
	 * @param timeOfRequest
	 * @return {@link OrderItem}
	 */
	public Boolean addOrder(Long clientId, Integer quantity);

	/**
	 * For getting the list of order items in the queue.
	 * 
	 * @return {@link List<OrderItem>}
	 */

	public List<OrderItem> getOrderList();

	/**
	 * For removing a speciﬁc ID from the queue.
	 * 
	 * @param id
	 * @return {@link Long}
	 */

	public Long remove(Long clientId);

	/**
	 * For getting the top ID from the queue and removing it (dequeue).
	 * 
	 * @return {@link Long}
	 */

	public Long removeNextOrder();

	/**
	 * To get the position of a specific ID in the queue.
	 * 
	 * @param id
	 * @return {@link Integer}
	 */

	public Integer getPosition(Long clientId);

	/**
	 * To get the wait time in queue(in seconds) of a specific ID in the queue.
	 * 
	 * @param id
	 * @return {@link Integer}
	 */

	public Long getTimeInQueue(Long clientId);

	/**
	 * For getting the list of order items for the next delivery.
	 * 
	 * @return {@link List<OrderItem>}
	 */
	public List<OrderItem> getNextDeliveryList(); 
	
	
}
