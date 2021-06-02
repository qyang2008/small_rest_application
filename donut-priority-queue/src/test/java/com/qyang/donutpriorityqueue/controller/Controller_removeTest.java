package com.qyang.donutpriorityqueue.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.qyang.donutpriorityqueue.config.Properties;
import com.qyang.donutpriorityqueue.model.DonutOrderRequest;
import com.qyang.donutpriorityqueue.model.DonutOrderResponse;
import com.qyang.donutpriorityqueue.model.OrderItem;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Controller_removeTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Long[] clientIds = { 30L, 50L, 6000L };
	private final Integer[] quantities = { 10, 30, 20 };
	private final Long clientId1 = 19L;
	private final Long clientId2 = 35L;

	@Before
	public void setUp() {
		int i = 0;
		for (Long clientId : clientIds) {
			restTemplate.postForEntity(Properties.PATH_ADD_ORDER, new DonutOrderRequest(clientId, quantities[i++]),
					DonutOrderResponse.class);

		}
	}

	@Test
	public void testRemove_withIdPresentInQueue() {

		restTemplate.delete(Properties.PATH_REMOVE.replaceFirst("\\{.*\\}", clientIds[0].toString()));

		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.getForEntity(Properties.PATH_GET_ORDER_LIST,
				DonutOrderResponse.class);
		DonutOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);

		final List<OrderItem> responseQueue = new ArrayList<>(response.getQueue());
		softly.assertThat(
				responseQueue.stream().map(e -> e.getClientId()).collect(Collectors.toList()).contains(clientIds[0]))
				.as("List do not contain clientId.").isFalse();

		softly.assertAll();
	}

	@Test
	public void testRemove_withIdNotPresentInQueue() {
		restTemplate.delete(Properties.PATH_REMOVE.replaceFirst("\\{.*\\}", clientId1.toString()));

		restTemplate.delete(Properties.PATH_REMOVE.replaceFirst("\\{.*\\}", clientId2.toString()));

		restTemplate.delete(Properties.PATH_REMOVE.replaceFirst("\\{.*\\}", clientId2.toString()));

		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.getForEntity(Properties.PATH_GET_ORDER_LIST,
				DonutOrderResponse.class);
		DonutOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);
		final List<OrderItem> responseQueue = new ArrayList<>(response.getQueue());

		softly.assertThat(
				responseQueue.stream().map(e -> e.getClientId()).collect(Collectors.toList()).contains(clientId1))
				.as("List do not contain clientid.").isFalse();
		softly.assertAll();
	}

	@Test(expected = Exception.class)
	public void testRemove_withIdNull() {
		restTemplate.delete(Properties.PATH_REMOVE.replaceFirst("\\{.*\\}", null));

		restTemplate.delete(Properties.PATH_REMOVE.replaceFirst("\\{.*\\}", null));

		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.getForEntity(Properties.PATH_GET_ORDER_LIST,
				DonutOrderResponse.class);
		DonutOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);
		final List<OrderItem> responseQueue = new ArrayList<>(response.getQueue());

		softly.assertThat(
				responseQueue.stream().map(e -> e.getClientId()).collect(Collectors.toList()).contains(clientId1))
				.as("List do not contain clientid.").isFalse();
		softly.assertAll();
	}

}
