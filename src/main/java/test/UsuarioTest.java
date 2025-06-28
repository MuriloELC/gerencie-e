package test;

import java.sql.Connection;

import connection.DatabaseConnection;
import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioTest {
	public static void main(String[] args) {
		Connection conn = DatabaseConnection.getConnection();

		String ID = "dc8cfc0c-34ab-475b-bf56-9b1cc7f4b587";
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		usuario.setEmail("usuario@empresa.com");
		usuario.setSenha("123456");
		usuario.setIdEmpresa("dc8cfc0c-34ab-475b-bf56-9b1cc7f4b87b");
		usuario.setAtivo(1);

		UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
		usuarioDAO.cadastrar(usuario);
		System.out.println(usuarioDAO.listar());
		System.out.println(usuarioDAO.listarPorId(ID));
		usuarioDAO.inativar("ID");
		usuarioDAO.ativar("ID");
		usuario.setSenha("12345");
		usuarioDAO.atualizar(usuario);
	}
}