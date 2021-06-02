package com.qyang.donutpriorityqueue.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;

import com.qyang.donutpriorityqueue.config.Properties;
import com.qyang.donutpriorityqueue.model.DataRepository;
import com.qyang.donutpriorityqueue.model.OrderItem;

/**
 * This class implements {@link RankedDonutOrdersQueueDAO}.
 * 
 *
 */
@Component
public class RankedDonutOrdersQueueDAOImpl implements RankedDonutOrdersQueueDAO {

	DataRepository repository;

	public RankedDonutOrdersQueueDAOImpl() {
		repository = new DataRepository();
		this.repository.setQueue(new RankedDonutOrdersQueue().getQueue());
		this.repository.setOrderItemsMap(new HashMap<>(Properties.INITIAL_QUEUE_SIZE));
	}

	public synchronized Boolean enque(final OrderItem oderitem) {
		if (!repository.getOrderItemsMap().containsKey(oderitem.getClientId().toString())) {
			repository.getQueue().add(oderitem);
			repository.getOrderItemsMap().put(oderitem.getClientId().toString(), oderitem);
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	public synchronized List<OrderItem> getQueue() {
		final PriorityBlockingQueue<OrderItem> tmpPQ;
		final List<OrderItem> tmpQueueCopyAsList = new ArrayList<>();

		tmpPQ = new PriorityBlockingQueue<>(repository.getQueue());
		tmpPQ.drainTo(tmpQueueCopyAsList);

		return Collections.unmodifiableList(tmpQueueCopyAsList);
	}

	public synchronized Boolean remove(final Long clientId) {
		final OrderItem request = repository.getOrderItemsMap().get(clientId.toString());

		if (request == null) {
			return Boolean.FALSE;
		}

		repository.getQueue().remove(request);
		repository.getOrderItemsMap().remove(clientId.toString());

		return Boolean.TRUE;
	}

	public synchronized Optional<OrderItem> deque() {
		final OrderItem request;

		request = repository.getQueue().poll();
		if (request != null) {
			repository.getOrderItemsMap().remove(request.getClientId().toString());
		}

		return Optional.ofNullable(request);

	}

	public synchronized Map<String, OrderItem> getOrderItemByIdMap() {
		return Collections.unmodifiableMap(repository.getOrderItemsMap());
	}

	public synchronized List<OrderItem> getNextDeliveryList() {

		final List<OrderItem> tmpDeliveryList = new ArrayList<OrderItem>();
		final OrderItem[] orderArray = repository.getQueue().toArray(new OrderItem[repository.getQueue().size()]);
		int restCartCapacity = Properties.MAX_CART_CAPACITY;

		Arrays.sort(orderArray);
		for (int i = 0; i < orderArray.length && orderArray[i].getQuantity() <= restCartCapacity; i++) {

			restCartCapacity -= orderArray[i].getQuantity();
			tmpDeliveryList.add(orderArray[i]);
		}

		return Collections.unmodifiableList(tmpDeliveryList);
	}
}
