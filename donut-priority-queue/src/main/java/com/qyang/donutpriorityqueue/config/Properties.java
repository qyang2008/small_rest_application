package com.qyang.donutpriorityqueue.config;

/**
 * This class stores all the properties.
 * 
 *
 */
public class Properties {

	private Properties() {
	}

	/**
	 * the max capacity of the cart.
	 */
	public static final Integer MAX_CART_CAPACITY = 50;
	
	

	/**
	 * path
	 */
	public static final String PATH_GET_POSITION = "/getPosition/{clientId}";
	public static final String PATH_REMOVE = "/remove/{clientId}";
	public static final String PATH_GET_ORDER_LIST = "/getOrderList";
	public static final String PATH_ADD_ORDER = "/addOrder";
	public static final String PATH_NEXT_DELIVERY = "/getNextDelivery";
	
	/**
	 * response message
	 */
	public static final String MSG_ORDER_WAS_NOT_ADDED_IN_THE_QUEUE = "Order was not added in the queue.";
	public static final String MSG_ORDER_WAS_ADDED_IN_THE_QUEUE = "Order was added in the queue.";
	public static final String MSG_THE_QUEUE_IS_EMPTY = "The queue is empty.";
	public static final String MSG_NO_Order_WAS_FOUND_FOR_CLIENTID = "No Order was found for clientId %s.";
	public static final String MSG_DEQUEUED_ORDER_WITH_CLIENTID = "Dequeued request with clientId=%s.";

	/**
	 * error message of validation
	 */
	public static final String ERR_MSG_CLIENTID_CAN_NOT_BE_NULL = "clientId can not be null.";
	public static final String ERR_MSG_CLIENTID_CAN_NOT_BE_MORE_THAN_20000 = "clientId can not be more than 20000.";
	public static final String ERR_MSG_QUANTITY_CAN_NOT_BE_NULL = "quantity can not be null.";
	public static final String ERR_MSG_QUANTITY_CAN_NOT_BE_MORE_THAN_50 = "quantity can not be more than 50.";
	public static final String ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_CLIENTID = "Provide a valid numeric argument for clientId.";
	public static final String ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_QUANTITY = "Provide a valid numeric argument for quantity.";
	public static final String ERR_MSG_CLIENTID_CAN_HAVE_MAXIMUM_5_DIGITS_AND_NO_DECIMALS = "clientId can have maximum 5 digits and no decimals.";
	public static final String ERR_MSG_QUANTITY_CAN_HAVE_MAXIMUM_2_DIGITS_AND_NO_DECIMALS = "clientId can have maximum 2 digits and no decimals.";

	/**
	 * Initial size of the priority queue.
	 */
	public static final Integer INITIAL_QUEUE_SIZE = 32;

	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

	public static final String RESPONSE_HEADER_APP_DIAGNOSTIC = "x-app-diagnostic";
	
}
