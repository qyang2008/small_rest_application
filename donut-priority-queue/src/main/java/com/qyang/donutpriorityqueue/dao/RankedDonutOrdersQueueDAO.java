package com.qyang.donutpriorityqueue.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.qyang.donutpriorityqueue.model.OrderItem;

public interface RankedDonutOrdersQueueDAO {

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param orderItem
	 * @return {@link Boolean}
	 */
	public Boolean enque(final OrderItem orderItem);

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue
	 * is empty.
	 * 
	 * @return {@link Optional<OrderItem>}
	 */

	public Optional<OrderItem> deque();

	/**
	 * Removes a single instance of the specified element for the request id from
	 * this queue, if it is present.
	 * 
	 * @param clientId
	 * @return {@link Boolean}
	 */

	public Boolean remove(final Long clientId);

	/**
	 * Returns a List containing the elements in the priority queue. The List
	 * maintains the order of elements same as the order of elements in the Priority
	 * Queue.
	 * 
	 * @return {@link List<OrderItem>}
	 */

	public List<OrderItem> getQueue();

	/**
	 * Returns a Map of orders with client's id as the key.
	 * 
	 * @return {@link Map<String, OrderItem>}
	 */
	public Map<String, OrderItem> getOrderItemByIdMap();

	/**
	 * Returns a Map of orders with client's id as the key.
	 * 
	 * @return {@link Map<String, OrderItem>}
	 */
	public List<OrderItem> getNextDeliveryList();

}
