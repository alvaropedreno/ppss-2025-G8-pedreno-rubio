package ppss;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import static org.junit.jupiter.api.Assertions.*;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

/* IMPORTANTE:
    Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
    vamos a usar "throws Esception" en los métodos, para que el código quede más
    legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit 
    con un assertDoesNotThrow()
    Es decir, que vamos a primar la legibilidad de los tests.
    Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
    invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
*/
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {


    String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?allowPublicKeyRetrieval=true&useSSL=false";
    databaseTester = new MiJdbcDatabaseTester("com.mysql.cj.jdbc.Driver", cadena_conexionDB
            , "ppss_user", "ppss-2025");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
     //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_insert_should_throw_exception_when_cliente_already_exists() throws Exception {
    Cliente cliente = new Cliente(1, "John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    // Inicializamos la BD con dos clientes
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/clientes-dos-clientes.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    // Intentamos insertar un cliente que ya existe
    SQLException thrown = assertThrows(SQLException.class, () -> {
      clienteDAO.insert(cliente);
    });

    // Verificamos que el mensaje de la excepción contiene "Duplicate entry"
    Assertions.assertTrue(thrown.getMessage().contains("Duplicate entry"));
  }

  @Test
  public void D4_delete_should_throw_exception_when_cliente_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(3, "Nonexistent", "Client");
    cliente.setDireccion("Unknown Street");
    cliente.setCiudad("Nowhere");

    // Inicializamos la BD con dos clientes
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/clientes-dos-clientes.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    // Intentamos eliminar un cliente que no existe
    SQLException thrown = assertThrows(SQLException.class, () -> {
      clienteDAO.delete(cliente);
    });

    // Verificamos que el mensaje de la excepción contiene "Delete failed"
    Assertions.assertTrue(thrown.getMessage().contains("Delete failed"));
  }

  @Test
  public void D5_update_should_modify_cliente_data_when_cliente_exists() throws Exception {
    Cliente cliente = new Cliente(1, "John", "Smith");
    cliente.setDireccion("Other Street");
    cliente.setCiudad("NewCity");

    // Inicializamos la BD con el cliente inicial
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    // Invocamos a la SUT para actualizar los datos del cliente
    Assertions.assertDoesNotThrow(() -> clienteDAO.update(cliente));

    // Recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");

    // Creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-actualizado.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D6_retrieve_should_return_cliente_data_when_cliente_exists() throws Exception {
    Cliente clienteEsperado = new Cliente(1, "John", "Smith");
    clienteEsperado.setDireccion("1 Main Street");
    clienteEsperado.setCiudad("Anycity");

    //Inicializamos la bd
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //Invocamos a nuestro sut
    int idaRecuperar = 1;

    Cliente clienteRecuperado = Assertions.assertDoesNotThrow(() -> clienteDAO.retrieve(idaRecuperar));

    Assertions.assertAll(
            () -> Assertions.assertEquals(clienteEsperado.getId(), clienteRecuperado.getId()),
            () -> Assertions.assertEquals(clienteEsperado.getNombre(), clienteRecuperado.getNombre()),
            () -> Assertions.assertEquals(clienteEsperado.getApellido(), clienteRecuperado.getApellido()),
            () -> Assertions.assertEquals(clienteEsperado.getDireccion(), clienteRecuperado.getDireccion()),
            () -> Assertions.assertEquals(clienteEsperado.getCiudad(), clienteRecuperado.getCiudad())
    );
  }
}
