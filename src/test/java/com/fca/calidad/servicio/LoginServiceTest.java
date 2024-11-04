package com.fca.calidad.servicio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.modelo.User;

class LoginServiceTest {

	LoginService service;
	IDAOUser dao;
	
	@Test
	void loginTest() {
		//Inicializar
		dao = mock(IDAOUser.class);
		service = new LoginService(dao);
		User usuario = new User("nombre1","email@email.com","123456");
		when(dao.findByUserName("nombre1")).thenReturn(usuario);
		
		//Ejercicio
		boolean result = service.login("nombre1", "123456");
		//Verificacion
		assertThat(result,is(true));
		
	}

}
