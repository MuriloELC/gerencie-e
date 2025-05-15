package test;

import java.sql.Connection;

import connection.DatabaseConnection;
import dao.ProdutoDAO;
import model.Produto;

public class ProdutoTest {
	public static void main(String[] args) {
		Connection conn = DatabaseConnection.getConnection();

		String ID = "e49eb22b-60d5-44c7-a19e-3d5f56480d9c";
		Produto produto = new Produto();
		produto.setDescricao("Monitor 66'' Full HD");
		produto.setQuantidade(10);
		produto.setValorCusto(600.00);
		produto.setValorVenda(850.00);
		produto.setIdEmpresa("dc8cfc0c-34ab-475b-bf56-9b1cc7f4b87b");
		produto.setQuantidadeEstoque(10);
		produto.setAtivo(1);

		ProdutoDAO produtoDAO = new ProdutoDAO(conn);
		produtoDAO.cadastrar(produto);
		System.out.println(produtoDAO.listar());
		System.out.println(produtoDAO.listarPorId(ID));
		produtoDAO.inativar(ID);
		produtoDAO.ativar(ID);
		produto.setDescricao("Monitor diferente");
		produtoDAO.atualizar(produto);
	}
}