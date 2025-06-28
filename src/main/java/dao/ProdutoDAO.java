package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Produto;

public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produto (id, descricao, quantidade, valorcusto, valorvenda, id_empresa, quantidade_estoque, ativo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getId());
            ps.setString(2, produto.getDescricao());
            ps.setInt(3, produto.getQuantidade());
            ps.setDouble(4, produto.getValorCusto());
            ps.setDouble(5, produto.getValorVenda());
            ps.setString(6, produto.getIdEmpresa());
            ps.setInt(7, produto.getQuantidadeEstoque());
            ps.setInt(8, produto.getAtivo());

            ps.executeUpdate();

            System.out.println("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET descricao = ?, quantidade = ?, valorcusto = ?, valorvenda = ?, "
                + "id_empresa = ?, quantidade_estoque = ?, ativo = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getDescricao());
            ps.setInt(2, produto.getQuantidade());
            ps.setDouble(3, produto.getValorCusto());
            ps.setDouble(4, produto.getValorVenda());
            ps.setString(5, produto.getIdEmpresa());
            ps.setInt(6, produto.getQuantidadeEstoque());
            ps.setInt(7, produto.getAtivo());
            ps.setString(8, produto.getId());

            ps.executeUpdate();

            System.out.println("Produto atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE ativo = 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getString("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValorCusto(rs.getDouble("valorcusto"));
                produto.setValorVenda(rs.getDouble("valorvenda"));
                produto.setIdEmpresa(rs.getString("id_empresa"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setAtivo(rs.getInt("ativo"));

                lista.add(produto);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public Produto listarPorId(String id) {
        Produto produto = null;
        String sql = "SELECT * FROM produto WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getString("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValorCusto(rs.getDouble("valorcusto"));
                produto.setValorVenda(rs.getDouble("valorvenda"));
                produto.setIdEmpresa(rs.getString("id_empresa"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setAtivo(rs.getInt("ativo"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar produto por ID: " + e.getMessage());
        }
        return produto;
    }
    
    public List<Produto> listarPorEmpresa(String idEmpresa) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE ativo = 1 AND id_empresa = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idEmpresa);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getString("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValorCusto(rs.getDouble("valorcusto"));
                produto.setValorVenda(rs.getDouble("valorvenda"));
                produto.setIdEmpresa(rs.getString("id_empresa"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setAtivo(rs.getInt("ativo"));

                lista.add(produto);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos da empresa: " + e.getMessage());
        }

        return lista;
    }


    public void inativar(String id) {
        String sql = "UPDATE produto SET ativo = 0 WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

            System.out.println("Produto inativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inativar produto: " + e.getMessage());
        }
    }
    
    public void ativar(String id) {
    	String sql = "UPDATE produto SET ativo = 1 WHERE id = ?";
    	
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, id);
    		ps.executeUpdate();
    		
    		System.out.println("Produto ativado com sucesso!");
    	} catch (Exception e) {
    		System.out.println("Erro ao ativar produto: " + e.getMessage());
    	}
    }
}
