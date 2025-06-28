package test;

import java.sql.Connection;

import connection.DatabaseConnection;
import dao.ProdutoMovimentacaoDAO;
import model.ProdutoMovimentacao;

public class ProdutoMovimentacaoTest {
	public static void main(String[] args) {
		Connection conn = DatabaseConnection.getConnection();

		String ID = "ea02c760-8920-4325-ac7b-f36409f6d333";
		ProdutoMovimentacao mov = new ProdutoMovimentacao();
		mov.setId(ID);
		mov.setIdProduto("e49eb22b-60d5-44c7-a19e-3d5f56480d9c");
		mov.setIdEmpresa("dc8cfc0c-34ab-475b-bf56-9b1cc7f4b87b");
		mov.setQuantidadeMovimentada(5);
		mov.setValorUnitario(850.00);
		mov.setTipoMovimentacao("venda");
		mov.setTipo("Saída");
		mov.setAtivo(1);

		ProdutoMovimentacaoDAO dao = new ProdutoMovimentacaoDAO(conn);
		dao.cadastrar(mov);
		System.out.println(dao.listar());
		System.out.println(dao.listarPorId(ID));
		dao.inativar(ID);
		dao.ativar(ID);
		mov.setQuantidadeMovimentada(6);
		dao.atualizar(mov);
	}
}