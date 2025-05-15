package connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	private static final String PROPERTIES_FILE = "/env.properties";

	public static Connection getConnection() {
		try {
			InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE);

			if (input == null) {
				System.out.println("Arquivo de configuração não encontrado: " + PROPERTIES_FILE);
				return null;
			}

			Properties props = new Properties();
			props.load(input);

			String url = props.getProperty("db.url");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");

			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Conexão com o banco de dados estabelecida com sucesso! hehe");
			return conn;
		} catch (SQLException e) {
			System.out.println("Falha ao conectar ao banco de dados: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro ao carregar propriedades do banco: " + e.getMessage());
		}
		return null;
	}
}