package ppss.matriculacion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FuenteDatosJDBC {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String cadena_conexionDB = "jdbc:mysql://localhost:3306/matriculacion?allowPublicKeyRetrieval=true&useSSL=false";
	private final String dbUser = "ppss_user";
	private final String dbPassword = "ppss-2025";
	private static FuenteDatosJDBC me = null;

	private FuenteDatosJDBC() {
		try {
			Class.forName(JDBC_DRIVER);

		} catch (ClassNotFoundException e) {
			System.err.println("No se encuentra el driver de la BD");
		}
	}

	public static FuenteDatosJDBC getInstance() {
		if(me==null) {
			me = new FuenteDatosJDBC();
		}
		return me;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(cadena_conexionDB, dbUser, dbPassword);
	}
}