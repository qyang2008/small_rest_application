package com.qyang.donutpriorityqueue.controller;



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
public class Controller_getNextDeliveryTest {

	@Autowired
	private TestRestTemplate restTemplate;

	// order information to be used for test 2
	private final Long[] clientIds = { 100L, 2000L, 3000L };
	private final Integer[] quantitys = { 20, 30, 40 };



	@Test
	public void getNextDeliveryTest_checkFirstElementAndCartCapacityRule() {
		int i = 0;

		for (Long clientId : clientIds) {
			restTemplate.postForEntity(Properties.PATH_ADD_ORDER, new DonutOrderRequest(clientId, quantitys[i++]),
					DonutOrderResponse.class);

		}

		ResponseEntity<DonutOrderResponse> responseEntity = restTemplate.getForEntity(Properties.PATH_NEXT_DELIVERY,
				DonutOrderResponse.class);
		DonutOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getSuccess()).as("Successfully returned a list").isTrue();
		softly.assertThat(response.getQueue().size()).as("List has 2 ids").isEqualTo(2);
		softly.assertThat(response.getQueue().get(0).getClientId())
				.as("first element should be the premium client with id 100 ").isEqualTo(100);

		softly.assertAll();
	}

}
