package com.qyang.donutpriorityqueue.model;


/**
 * Describes an order item
 * 
 *
 */
public class OrderItem implements Comparable<OrderItem> {

	private final Long clientId;
	private final Integer quantity; 
	private final Long timeOfOrder;


	//private Long timeInQueue;//time in queue in second

	public OrderItem(Long clientID, Integer quantity, Long timeOfOrder) {
		this.clientId = clientID;
		this.quantity = quantity;
		this.timeOfOrder = timeOfOrder;

	}
	

	public Long getClientId() {
		return clientId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Long getTimeOfOrder() {
		return timeOfOrder;
	}

	public Boolean getIsPremium() {
		if (this.clientId!= null)
		{
			return 	this.clientId<1000 ? Boolean.TRUE: Boolean.FALSE;
		}
		
		return null;
	}
	
	public Long getTimeInQueue() {
		return System.currentTimeMillis()/1000- this.getTimeOfOrder();
	}

	

	/*
	 * This method is overridden to specify a natural sorting order.
	 *  1.Orders are sorted by the number of seconds they are in the queue
	 *  2.Orders from premium customers always have a higher priority than orders from regular customers
	 */
	@Override
	public int compareTo(OrderItem o) {

		
		if (this.getIsPremium() == o.getIsPremium()) {
			return (int) (o.getTimeInQueue() - this.getTimeInQueue());
		} else if (this.getIsPremium() &&  !o.getIsPremium()) {
			return -1;
		} else if (!this.getIsPremium() && o.getIsPremium()) {
			return 1;
		}
		
		return (int) (o.getTimeInQueue() - this.getTimeInQueue());
	}






}
