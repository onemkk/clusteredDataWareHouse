package com.progressSoft.clusteredDataWarehouse;

import com.progressSoft.clusteredDataWarehouse.controllers.responses.CustomResponse;
import com.progressSoft.clusteredDataWarehouse.controllers.responses.ImportDealResponse;
import com.progressSoft.clusteredDataWarehouse.models.dto.DealDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Log4j2
class DealControllerTest {
	private final static RestClient restClient = RestClient.create();

//	@Autowired
//	private ServerProperties properties;
	@LocalServerPort
	private int port;

	private ObjectMapper mapper = new ObjectMapper();

	@Container
	@ServiceConnection
	private static final MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

	@Test
	void createDeal() throws JsonProcessingException {
		log.debug("Port numberrrr :" + port);
		DealDTO dealRequestBody = DealDTO.builder().dealId(31L).dealAmount(new BigDecimal("2000")).
				toCurrencyIsoCode("USD").fromCurrencyIsoCode("EUR").build();
		String value = mapper.writeValueAsString(dealRequestBody);
		CustomResponse<ImportDealResponse> body = restClient.post().
				uri("http://localhost:%s/warehouse/api/deals".formatted(port)).
				contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(value)
				.retrieve().body(new ParameterizedTypeReference<>() {
				});
		assertThat(body).isNotNull();
		assertThat(body.getData()).isNotNull();
		assertThat(body.getMessage()).contains("Success");
	}


}
