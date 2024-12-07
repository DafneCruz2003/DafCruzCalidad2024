package com.fca.calidad.integracion;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fca.calidad.dao.DAOUserSQLite;
import com.fca.calidad.modelo.User;
import com.fca.calidad.servicio.UserService;

class UserServiceTest extends DBTestCase {
	private DAOUserSQLite dao;
	private UserService userService;

	public UserServiceTest() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.sqlite.JDBC");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:sqlite:/Users/base2/OneDrive/Escritorio/users.db");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
	}

	@BeforeEach
	public void setUp() {
		dao = new DAOUserSQLite();
		userService = new UserService(dao);
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fallo");
		}
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\base2\\eclipse-workspace\\maven\\maven\\src\\resources\\createUser.xml"));
	}

	@Test
	void createUserTest() {
		User usuario = userService.createUser("nombre", "email", "password");
		int resultadoEsperado = 1;
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			IDataSet databaseDataSet = connection.createDataSet();
			ITable tablaReal = databaseDataSet.getTable("users");
			String nombreReal = (String) tablaReal.getValue(0, "name");
			String nombreEsperado = "nombre";
			assertEquals(nombreReal, nombreEsperado);
			String emailReal = (String) tablaReal.getValue(0, "email");
			String emailEsperado = "email";
			assertEquals(emailReal, emailEsperado);
			String passwordR = (String) tablaReal.getValue(0, "password");
			String passwordE = "password";
			assertEquals(passwordR, passwordE);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fallo");
		}
	}

	@Test
	void createUser2Test() {
		User usuario = userService.createUser("nombre", "email", "password");
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			IDataSet databaseDataSet = connection.createDataSet();
			ITable tablaReal = databaseDataSet.getTable("users");
			String nombreReal = (String) tablaReal.getValue(0, "name");
			String nombreEsperado = "nombre";
			System.out.println("Real =" + nombreReal);
			assertEquals(nombreReal, nombreEsperado);
			System.out.println("E=" + (String) tablaReal.getValue(0, "email"));
			System.out.println("P=" + (String) tablaReal.getValue(0, "password"));
			assertEquals((String) tablaReal.getValue(0, "email"), "email");
			assertEquals((String) tablaReal.getValue(0, "password"), "password");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fallo");
		}
	}

	void createUser3Test() {
		User usuario = userService.createUser("nombre", "email", "password");
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			IDataSet databaseDataSet = connection.createDataSet();
			ITable tablaReal = databaseDataSet.getTable("users");
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\base2\\eclipse-workspace\\maven\\maven\\src\\resources\\createUser.xml"));
			ITable expectedTable = expectedDataSet.getTable("users");
			ITable filteredTable = DefaultColumnFilter.includedColumnsTable(tablaReal, expectedTable.getTableMetaData().getColumns());
			Assertion.assertEquals(filteredTable, expectedTable);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fallo");
		}
	}

	@Test
	void searchUserTest() {
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			IDataSet databaseDataSet = connection.createDataSet();
			ITable tablaReal = databaseDataSet.getTable("users");

			assertEquals(1, tablaReal.getRowCount());
			String nombreReal = (String) tablaReal.getValue(0, "name");
			assertEquals("nombre", nombreReal);

			String emailReal = (String) tablaReal.getValue(0, "email");
			assertEquals("email", emailReal);

			String passwordReal = (String) tablaReal.getValue(0, "password");
			assertEquals("password", passwordReal);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error al realizar la búsqueda del usuario en la base de datos.");
		}
	}
}
