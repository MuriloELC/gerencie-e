package test;

import java.sql.Connection;

import connection.DatabaseConnection;
import dao.EmpresaDAO;
import model.Empresa;

public class EmpresaTest {
	public static void main(String[] args) {
		Connection conn = DatabaseConnection.getConnection();
		
		String ID = "dc8cfc0c-34ab-475b-bf56-9b1cc7f4b87b";
		Empresa empresa = new Empresa();
		empresa.setNomeRazao("Arroz 3");
		empresa.setApelidoFantasia("Batata");
		empresa.setCpfCnpj("75.923.047/0001-26");
		empresa.setBairro("Sla");
		empresa.setCep("76970-000");
		empresa.setCidade("Pimenta Bueno");
		empresa.setEstado("RO");
		empresa.setLogradouro("Rua sla, numero 14");
		empresa.setContato01("3451-3451");
		empresa.setEmail("arroba@gmail.com");
		empresa.setPais("Brasil");
		empresa.setAtivo(1);
		
		EmpresaDAO empresaDAO = new EmpresaDAO(conn);
		empresaDAO.cadastrar(empresa);
		System.out.println(empresaDAO.listar());
		System.out.println(empresaDAO.listarPorId(ID));
		empresaDAO.inativar(ID);
		empresaDAO.ativar(ID);
		empresa.setBairro("Não sei");
		empresaDAO.atualizar(empresa);
		
		
	}
}
