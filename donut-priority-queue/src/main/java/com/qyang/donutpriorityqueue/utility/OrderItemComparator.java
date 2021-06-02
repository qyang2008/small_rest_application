package com.qyang.donutpriorityqueue.utility;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.qyang.donutpriorityqueue.model.OrderItem;

/**
 * Comparator describes comparison of OrderItem based on client type and the number of seconds
 * they’ve been in the queue
 * 
 * Ranking is based on following rule:
 * 
 * (1) Normal clients (IDs equal or more than 1000) are given a rank equal to the number of seconds they’ve been
 * in the queue.
 * 
 * (2) Premium clients (IDs less than 1000) are always ranked ahead of normal IDs and are
 * ranked among themselves according to the number of seconds they’ve been in
 * the queue.
 * 
 *
 */
@Component
public class OrderItemComparator implements Comparator<OrderItem> {

	@Override
	public int compare(OrderItem orderItem1, OrderItem orderItem2) {
		
		
		if (orderItem1.getIsPremium() == orderItem2.getIsPremium()) {
			return (int) (orderItem2.getTimeInQueue() - orderItem1.getTimeInQueue());
		} else if (orderItem1.getIsPremium() &&  !orderItem2.getIsPremium()) {
			return -1;
		} else if (!orderItem1.getIsPremium() && orderItem2.getIsPremium()) {
			return 1;
		}
		
		return (int) (orderItem2.getTimeInQueue() - orderItem1.getTimeInQueue());
	}
}
