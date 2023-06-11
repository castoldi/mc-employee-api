package com.mc.employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class McEmployeeApiApplicationTests {

	@LocalServerPort
	private Integer port;

	@Test
	void contextLoads(ApplicationContext context) {
	    assertThat(context).isNotNull();
	}

}
