package com.qyang.donutpriorityqueue.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;



/**
 * This class is used for creating data repository based on
 * {@link PriorityBlockingQueue}. This class is model class for maintaining the
 * state of a {@link OrderItem}s data repository.
 * 
 * This class can be used to provide Create, Read, Update, Delete (CRUD) feature
 * and rules for managing CRUD operations using a priority queue and a hash map.
 * 
 *
 */
@Component
public class DataRepository implements Serializable {

	private static final long serialVersionUID = -6853776598961875L;

	private volatile PriorityBlockingQueue<OrderItem> pq;
	private volatile HashMap<String, OrderItem> OrderItemsMap;

	/**
	 * Returns an unbounded blocking(concurrent) queue that uses the same ordering
	 * rules as class {@link PriorityQueue} and supplies blocking retrieval
	 * operations.
	 * 
	 * @return {@link PriorityBlockingQueue}
	 */
	public PriorityBlockingQueue<OrderItem> getQueue() {
		return pq;
	}

	/**
	 * Set the value of Priority Blocking Queue
	 * 
	 * @param {@link PriorityBlockingQueue<OrderItem>}
	 */
	public synchronized void setQueue(PriorityBlockingQueue<OrderItem> priorityBlockingQueue) {
		this.pq = priorityBlockingQueue;
	}

	/**
	 * This implementation provides a hash map. This class makes no guarantees as to
	 * the order of the map; in particular, it does not guarantee that the order
	 * will remain constant over time.
	 * 
	 * This could be used for storing metadata of records/requests, helpful in
	 * enforcing rules on the priority queue.
	 * 
	 * @return {@link HashMap<Long, OrderItem>}
	 */
	public synchronized HashMap<String, OrderItem> getOrderItemsMap() {
		return OrderItemsMap;
	}

	/**
	 * Set the value of HashMap.
	 * 
	 * @param {@link HashMap<Long, OrderItem>}
	 */
	public synchronized void setOrderItemsMap(HashMap<String, OrderItem> OrderItemsMap) {
		this.OrderItemsMap = OrderItemsMap;
	}

}
