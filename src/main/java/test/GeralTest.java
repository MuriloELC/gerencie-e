package test;

import java.sql.Connection;

import connection.DatabaseConnection;

public class GeralTest {
	public static void main(String[] args) {
		Connection conn = DatabaseConnection.getConnection();
		
		System.out.println("\n=== TestDatabaseConnection ===");
		TestDatabaseConnection.main(args);
		
		System.out.println("\n=== EmpresaTest ===");
		EmpresaTest.main(args);
		
		System.out.println("\n=== UsuarioTest ===");
		UsuarioTest.main(args);
		
		System.out.println("\n=== ProdutoTest ===");
		ProdutoTest.main(args);
		
		System.out.println("\n=== ProdutoMovimentacaoTest ===");
		ProdutoMovimentacaoTest.main(args);
	}
}
