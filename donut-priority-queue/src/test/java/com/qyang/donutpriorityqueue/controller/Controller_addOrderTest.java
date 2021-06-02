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
public class Controller_addOrderTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	/**
	 * The following values are used to create order item to be inserted into the
	 * queue with the addOrder method
	 *
	 */
    //test1:AddOneOrder
	private final Long clientId1 = 100L;
	private final Integer quantity1 = 30;

	//test2:AddTwoOrderWithDifferentClientIds
	private final Long clientId2 = 1500L;
	private final Integer quantity2 = 20;

	private final Long clientId3 = 2000L;
	private final Integer quantity3 = 10;

	//test3:AddTwoOrderWithSameClientIds
	private final Long clientId4 = 3000L;
	private final Integer quantity4 = 16;
	
	private final Long clientId5 = 3000L;
	private final Integer quantity5 = 10;
	
	

	@Test
	public void testAddOrder_AddOneOrder() {
		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.postForEntity(
				Properties.PATH_ADD_ORDER,
				new DonutOrderRequest(clientId1, quantity1), DonutOrderResponse.class);
		DonutOrderResponse response = responseEntity.getBody();

		assert response != null;

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response.getSuccess()).as("Element was added in the queue").isTrue();
		softly.assertThat(response.getMessage()).as("Order was added in the queue.")
				.isEqualTo(Properties.MSG_ORDER_WAS_ADDED_IN_THE_QUEUE);
		softly.assertAll();
	}

	@Test
	public void testAddOrder_AddTwoOrderWithDifferentClientIds() {
		ResponseEntity<DonutOrderResponse> responseEntity_1 = restTemplate.postForEntity(
				Properties.PATH_ADD_ORDER,
				new DonutOrderRequest(clientId2, quantity2), DonutOrderResponse.class);
		DonutOrderResponse response1 = responseEntity_1.getBody();

		ResponseEntity<DonutOrderResponse> responseEntity_2 = restTemplate.postForEntity(
				Properties.PATH_ADD_ORDER,
				new DonutOrderRequest(clientId3, quantity3), DonutOrderResponse.class);
		DonutOrderResponse response2 = responseEntity_2.getBody();

		assert response1 != null && response2 != null;

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(responseEntity_1.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response1.getSuccess()).as("Order 2 was added in the queue").isTrue();
		softly.assertThat(response1.getMessage()).as("Response 1 contains success message")
				.isEqualTo(Properties.MSG_ORDER_WAS_ADDED_IN_THE_QUEUE);

		softly.assertThat(responseEntity_2.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response2.getSuccess()).as("Order 2 was added in the queue").isTrue();
		softly.assertThat(response2.getMessage()).as("Request 2 contains success message")
				.isEqualTo(Properties.MSG_ORDER_WAS_ADDED_IN_THE_QUEUE);

		softly.assertAll();
	}

	@Test
	public void testAddOrder_AddTwoOrderWithSameClientIds() {
		ResponseEntity<DonutOrderResponse> responseEntity_1 = restTemplate.postForEntity(
				Properties.PATH_ADD_ORDER,
				new DonutOrderRequest(clientId4, quantity4), DonutOrderResponse.class);
		DonutOrderResponse response1 = responseEntity_1.getBody();

		ResponseEntity<DonutOrderResponse> responseEntity_2 = restTemplate.postForEntity(
				Properties.PATH_ADD_ORDER,
				new DonutOrderRequest(clientId5, quantity5), DonutOrderResponse.class);
		
		List<String> response2 = responseEntity_2.getHeaders().get(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC);

		assert response1 != null && response2 != null;

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(responseEntity_1.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response1.getSuccess()).as("order 1 was added in the queue").isTrue();
		softly.assertThat(response1.getMessage()).as("Response 1 contains success message")
				.isEqualTo(Properties.MSG_ORDER_WAS_ADDED_IN_THE_QUEUE);

		softly.assertThat(responseEntity_2.getStatusCodeValue()).as("Status code is 204.").isEqualTo(204);
		softly.assertThat(response2).as("Response conatains message").contains(Properties.MSG_ORDER_WAS_NOT_ADDED_IN_THE_QUEUE);

		softly.assertAll();
	}

}
