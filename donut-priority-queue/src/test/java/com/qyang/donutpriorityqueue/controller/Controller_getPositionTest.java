package com.qyang.donutpriorityqueue.controller;

import java.io.IOException;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qyang.donutpriorityqueue.config.Properties;
import com.qyang.donutpriorityqueue.model.DonutOrderRequest;
import com.qyang.donutpriorityqueue.model.DonutOrderResponse;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Controller_getPositionTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ObjectMapper mapper;


	private final Long[] clientIds = { 1800L, 3300L, 700L };
	private final Integer[] quantities = { 30, 20, 35 };
	private final Long clientId = 100L;
	private final Long premium1 = clientIds[2]; 


	@Before
	public void setUp() {
		int i = 0;
		for (Long clientId : clientIds) {
			restTemplate.postForEntity(Properties.PATH_ADD_ORDER,
					new DonutOrderRequest(clientId, quantities[i]), DonutOrderResponse.class);
			        i++;

		}
	}

	@Test
	public void testGetPosition_withIdNotPresentInQueue() throws JsonParseException, JsonMappingException, IOException {
		
		  ResponseEntity<DonutOrderResponse> responseEntity =
		  restTemplate.getForEntity(
		  Properties.PATH_GET_POSITION.replaceFirst("\\{.*\\}", clientId.toString()),
		  DonutOrderResponse.class);
		 
		
		List<String> response = responseEntity.getHeaders().get(Properties.RESPONSE_HEADER_APP_DIAGNOSTIC);
		
		assert response != null;
		
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 204.")
				.isEqualTo(204);
		softly.assertThat(response).as("Response for failure to find the is in the queue.")
				.contains(new String[]{String.format(Properties.MSG_NO_Order_WAS_FOUND_FOR_CLIENTID, clientId)});
		softly.assertAll();
	}

	
	  @Test public void testGetPosition_withIdPresentInQueue() {
	  
	  ResponseEntity<DonutOrderResponse> responseEntity =
	  restTemplate.getForEntity(
	  Properties.PATH_GET_POSITION.replaceFirst("\\{.*\\}",
			  premium1.toString()), DonutOrderResponse.class);
	  DonutOrderResponse  response = responseEntity.getBody();
	  
	  assert response != null;
	  
	  SoftAssertions softly = new SoftAssertions();
	  
	  softly.assertThat(responseEntity.getStatusCodeValue()).
	  as("Status code is 200.").isEqualTo(200);
	  softly.assertThat(response.getPosition()).
	  as("Position is equal to 1 for the premium id from the 3 test clients.").isEqualTo(1);

	  softly.assertThat(response.getSuccess()).as("Success is true").isTrue();
	  softly.assertAll(); }
	 

}
