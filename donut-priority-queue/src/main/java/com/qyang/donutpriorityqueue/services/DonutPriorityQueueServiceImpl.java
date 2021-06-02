package com.qyang.donutpriorityqueue.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qyang.donutpriorityqueue.dao.RankedDonutOrdersQueueDAOImpl;
import com.qyang.donutpriorityqueue.model.OrderItem;

/**
 * Service Implementation.
 *
 */
@Component
public class DonutPriorityQueueServiceImpl implements DonutPriorityQueueService {

	@Autowired
	RankedDonutOrdersQueueDAOImpl donutOrderRequestDAO;

	@Override
	public Boolean addOrder(Long clientId, Integer quantity) {
		final Long timeInSec = System.currentTimeMillis() / 1000;
		OrderItem oderItem = new OrderItem(clientId, quantity, timeInSec);

		return donutOrderRequestDAO.enque(oderItem) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public List<OrderItem> getOrderList() {
		final List<OrderItem> sortedListOfRequests = new ArrayList<>(donutOrderRequestDAO.getQueue());

		return sortedListOfRequests;
	}

	@Override
	public Long remove(final Long clientId) {
		return donutOrderRequestDAO.remove(clientId) ? clientId : -1L;
	}

	@Override
	public Long removeNextOrder() {
		final Optional<OrderItem> requestOptional = donutOrderRequestDAO.deque();

		if (requestOptional.isPresent()) {
			return requestOptional.get().getClientId();
		}

		return -1L;
	}

	@Override
	public Integer getPosition(Long clientId) {
		
		

		if (donutOrderRequestDAO.getOrderItemByIdMap()!=null && donutOrderRequestDAO.getOrderItemByIdMap().get(clientId.toString())!=null) {
			return donutOrderRequestDAO.getQueue()
					.indexOf(donutOrderRequestDAO.getOrderItemByIdMap().get(clientId.toString()));
		}

		return -1;
		
	}



	@Override
	public Long getTimeInQueue(Long clientId) {
		
		if (donutOrderRequestDAO.getOrderItemByIdMap()!=null && donutOrderRequestDAO.getOrderItemByIdMap().get(clientId.toString())!=null) {
			return donutOrderRequestDAO.getOrderItemByIdMap().get(clientId.toString()).getTimeInQueue();
		}

		return -1L;
		
		
	}

	@Override
	public List<OrderItem> getNextDeliveryList() {
		final List<OrderItem> nextDeliveryList = new ArrayList<>(donutOrderRequestDAO.getNextDeliveryList());

		return nextDeliveryList;
	}

}
