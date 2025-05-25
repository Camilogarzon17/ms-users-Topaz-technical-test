package com.topaz.ms_users;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsUsersApplicationTests {

	@Test
	void contextLoads() {
		// This test is intentionally left empty to verify that the Spring application context loads successfully.
	}

	@Test
	void mainMethodRunsSuccessfully() {

		try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
			MsUsersApplication.main(new String[] {});
			mockedStatic.verify(() -> SpringApplication.run(MsUsersApplication.class, new String[] {}));
		}
	}
}
