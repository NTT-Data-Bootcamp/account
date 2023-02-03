package com.bootcamp.java.account;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;


import org.junit.jupiter.api.Test;

public class MockitoTestIT_V1_CrearMocks_ObjetosEnLineaTest {

	//Mocks de objetos en línea
	@Test
	void testInlineMock() {
		Map mapMock = mock(Map.class);
		assertEquals(mapMock.size(), 0);
	}

}
