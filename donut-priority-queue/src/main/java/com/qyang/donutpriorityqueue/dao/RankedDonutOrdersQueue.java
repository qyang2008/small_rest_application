package com.qyang.donutpriorityqueue.dao;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;

import com.qyang.donutpriorityqueue.config.Properties;
import com.qyang.donutpriorityqueue.model.OrderItem;
import com.qyang.donutpriorityqueue.utility.OrderItemComparator;



/**
 * This class provides a priority queue sorted using {@link OrderItemComparator}
 * 
 *
 */
@Component
final class RankedDonutOrdersQueue {

	private PriorityBlockingQueue<OrderItem> queue;
	private final Comparator<OrderItem> compar;

	public RankedDonutOrdersQueue() {
		compar = new OrderItemComparator();
		queue = new PriorityBlockingQueue<OrderItem>(Properties.INITIAL_QUEUE_SIZE, compar);
	}

	/**
	 * Returns instance of concurrent priority queue.
	 * 
	 * @return {@link PriorityBlockingQueue<ServiceRequest>}
	 */
	public PriorityBlockingQueue<OrderItem> getQueue() {
		return queue;
	}



}
