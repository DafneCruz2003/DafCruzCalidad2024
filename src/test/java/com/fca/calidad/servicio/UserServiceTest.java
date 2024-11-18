/*IMPORTANTE
 * Nombre de la clase: MAYUSCULA
 * Metodos y variables: minuscula
 * NO USAR acentos ni la letra ñ
 * */
package com.fca.calidad.servicio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.modelo.User;


class UserServiceTest {
	
	private UserService servicio;
	private IDAOUser dao;
	//Simulacion de base de datos para busqueda
	private HashMap<Integer,User>baseDatos;
	//lo usare para instanciar y crear datos de prueba
	private User usuario;
	
	@BeforeEach
	void setup () {
		//inicializacion de datos
		dao = mock(IDAOUser.class);
		servicio = new UserService(dao);
		baseDatos = new HashMap<Integer, User>();
	}
	
	@Test
	void updateTest() {
		//actualizar datos mediante ID
		User usuarioViejo = new User ("nombre1","email","password");  
		usuarioViejo.setId(1);  
		baseDatos.put(usuarioViejo.getId(), usuarioViejo);  
		
		User usuarioNuevo = new User("nuevoNombre","email","nuevoPassword");
		usuarioNuevo.setId(1);
		
		when(dao.findById(1)).thenReturn(usuarioViejo);
		
		when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocation) throws Throwable{
			User arg = (User) invocation.getArguments()[0];
			baseDatos.replace(arg.getId(), arg);
			return baseDatos.get(arg.getId());
			}
		}
	);
		
		User result = servicio.updateUser(usuarioNuevo);
		
		assertThat("nuevoPassword",is(result.getPassword()));
	    assertThat("nuevoNombre",is(result.getName()));
	}
	
	
	@Test
	void deleteTest() {
	    //Borrar/Eliminar usuario
	    User usuarioViejo = new User("nombreEliminar", "email", "password");// Crear un usuario viejo
	    usuarioViejo.setId(1);
	    baseDatos.put(usuarioViejo.getId(), usuarioViejo);

	   
	    when(dao.findById(1)).thenReturn(usuarioViejo);  //simular que existe el usuario

	    
	    when(dao.deleteById(1)).thenAnswer(new Answer<Boolean>() {  //Simular eliminación en la base de datos 
	        public Boolean answer(InvocationOnMock invocation) throws Throwable {
	            Integer id = (Integer) invocation.getArguments()[0];
	            baseDatos.remove(id);  // Eliminar el usuario de la base de datos mock
	            return !baseDatos.containsKey(id);  // Verificamos que ya no existe
	        }
	    });

	    // Ejecutar  eliminación
	    boolean result = servicio.deleteUser(1);

	    // Verificar que el resultado sea verdadero
	    assertThat(result, is(true));
	    // Verificar que el usuario ya no exista en la base falsa
	    assertThat(baseDatos.containsKey(1), is(false));
	}
	

	 @Test
	    void createUserTest() {
		 //crear nuevo usuario
	        // Datos de entrada para crear un nuevo usuario
	        String nombre = "nuevoNombre";
	        String email = "nuevoEmail@gmail.com";
	        String password = "nuevaPassword";

	        // Simulamos que no existe un usuario con este email en la base de datos
	        when(dao.findUserByEmail(email)).thenReturn(null);
	        
	        // Simulamos el comportamiento de guardar un nuevo usuario
	        User usuarioNuevo = new User(nombre, email, password);
	        when(dao.save(any(User.class))).thenReturn(1);  // Simula que el ID generado es 1
	        
	        // Llamamos al método createUser del servicio
	        User result = servicio.createUser(nombre, email, password);

	        // Verificamos que el resultado es el esperado
	        assertThat(result, is(notNullValue())); // El usuario no debe ser nulo
	        assertThat(result.getName(), is("nuevoNombre"));
	        assertThat(result.getEmail(), is("nuevoEmail@gmail.com"));
	        assertThat(result.getPassword(), is("nuevaPassword"));
	        assertThat(result.getId(), is(1));  // El ID debe ser 1, que es el que simulamos

	        // Verificamos que el método findUserByEmail se haya llamado una vez
	        verify(dao, times(1)).findUserByEmail(email);

	        // Verificamos que el método save se haya llamado una vez con el usuario correcto
	        verify(dao, times(1)).save(any(User.class));
	    }

	    @Test
	    void createUserEmailExistsTest() {
	    	//usuario existente con ese email
	        // Datos de entrada
	        String nombre = "nuevoNombre";
	        String email = "nuevoEmail@gmail.com";
	        String password = "nuevaPassword";

	        //Simulamos que ya existe un usuario con ese email en la base de datos
	        User existingUser = new User("existingUser", email, "oldPassword");
	        when(dao.findUserByEmail(email)).thenReturn(existingUser);

	        //Llamamos al método createUser
	        User result = servicio.createUser(nombre, email, password);

	        // Verificamos que el usuario no haya sido creado
	        assertThat(result, is(equalTo(existingUser))); // El usuario retornado debe ser el ya existente

	        // Verificamos que el método save no se haya llamado
	        verify(dao, never()).save(any(User.class));
	    }

	    @Test
	    void createUserPasswordInvalidTest() {
	    	//el password es invalido 
	        // Datos de entrada
	        String nombre = "nuevoNombre";
	        String email = "nuevoEmail@gmail.com";
	        String password = "short";  // Contraseña que no cumple con la longitud mínima

	        // Llamamos al método createUser
	        User result = servicio.createUser(nombre, email, password);

	        // Verificamos que el resultado sea nulo debido a la contraseña inválida
	        assertThat(result, is(nullValue()));

	        // Verificamos que el método findUserByEmail no se haya llamado
	        verify(dao, never()).findUserByEmail(anyString());
	        
	        // Verificamos que el método save no se haya llamado
	        verify(dao, never()).save(any(User.class));
	    }
	    @Test
	    void findAllUsersTest() {
	    	//segun yo esta bien este, da verde:D 
	    	//buscar a todos los usuarios
	        // simulacion de BD HM obligatorio para busquedas en general (ID)
	        User user1 = new User("nombre1", "email1", "password1");
	        user1.setId(1);
	        User user2 = new User("nombre2", "email2", "password2");
	        user2.setId(2);

	        //put (poner mis datos en la "BD")
	        baseDatos.put(user1.getId(), user1);
	        baseDatos.put(user2.getId(), user2);

	        when(dao.findAll()).thenAnswer(invocation -> new ArrayList<>(baseDatos.values()));
	        //funcion corta(blanda), para reducir la funcion de thenAnswer
	        //ver si mejor escribo la funcion completa

	        List<User> result = servicio.findAllUsers();

	       //not null
	        assertThat(result, is(notNullValue()));

	       
	        assertThat(result.size(), is(2));

	
	        assertThat(result, hasItem(user1));
	        assertThat(result, hasItem(user2));

	        verify(dao, times(1)).findAll();
	    }

	    //falta por hacer
//busqueda de ID
	    //busqueda de email
	}
