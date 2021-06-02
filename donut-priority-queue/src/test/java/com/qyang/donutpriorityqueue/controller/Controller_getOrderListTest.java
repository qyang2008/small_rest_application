package com.qyang.donutpriorityqueue.controller;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
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

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Controller_getOrderListTest {

	@Autowired
	private TestRestTemplate restTemplate;

	// order information to be used for test 2
	private final Long[] clientIds = { 1600L, 10L, 500L };
	private final Integer[] quantity = { 20, 10, 28 };

	@Test
	public void testGetOrderList_withEmptyQueue() {
		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.getForEntity(Properties.PATH_GET_ORDER_LIST,
				DonutOrderResponse.class);
		List<String> response = responseEntity.getHeaders().get(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC);

		assert response != null;

		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 204.").isEqualTo(204);
		softly.assertThat(response).as("Response conatains message").contains(Properties.MSG_THE_QUEUE_IS_EMPTY);
		softly.assertAll();

		softly.assertAll();

	}

	@Test
	public void testGetOrderList_withNonEmptyQueue() {
		int i = 0;

		for (Long clientId : clientIds) {
			restTemplate.postForEntity(Properties.PATH_ADD_ORDER, new DonutOrderRequest(clientId, quantity[i++]),
					DonutOrderResponse.class);

		}

		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.getForEntity(Properties.PATH_GET_ORDER_LIST,
				DonutOrderResponse.class);
		DonutOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getSuccess()).as("Successfully returned a list").isTrue();
		softly.assertThat(response.getQueue().size()).as("List has 3 ids").isEqualTo(3);

		softly.assertAll();
	}

}
