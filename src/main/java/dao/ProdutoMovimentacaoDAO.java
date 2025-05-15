package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ProdutoMovimentacao;

public class ProdutoMovimentacaoDAO {
    private Connection conn;

    public ProdutoMovimentacaoDAO(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(ProdutoMovimentacao mov) {
        String sql = "INSERT INTO produtomovimentacao (id, id_produto, id_empresa, quantidade_movimentada, valor_unitario, tipo_movimentacao, tipo, ativo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mov.getId());
            ps.setString(2, mov.getIdProduto());
            ps.setString(3, mov.getIdEmpresa());
            ps.setInt(4, mov.getQuantidadeMovimentada());
            ps.setDouble(5, mov.getValorUnitario());
            ps.setString(6, mov.getTipoMovimentacao());
            ps.setString(7, mov.getTipo());
            ps.setInt(8, mov.getAtivo());

            ps.executeUpdate();
            System.out.println("Movimentação cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar movimentação: " + e.getMessage());
        }
    }

    public void atualizar(ProdutoMovimentacao mov) {
        String sql = "UPDATE produtomovimentacao SET id_produto = ?, id_empresa = ?, quantidade_movimentada = ?, "
                + "valor_unitario = ?, tipo_movimentacao = ?, tipo = ?, ativo = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mov.getIdProduto());
            ps.setString(2, mov.getIdEmpresa());
            ps.setInt(3, mov.getQuantidadeMovimentada());
            ps.setDouble(4, mov.getValorUnitario());
            ps.setString(5, mov.getTipoMovimentacao());
            ps.setString(6, mov.getTipo());
            ps.setInt(7, mov.getAtivo());
            ps.setString(8, mov.getId());

            ps.executeUpdate();
            System.out.println("Movimentação atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar movimentação: " + e.getMessage());
        }
    }

    public List<ProdutoMovimentacao> listar() {
        List<ProdutoMovimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtomovimentacao WHERE ativo = 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdutoMovimentacao mov = new ProdutoMovimentacao();
                mov.setId(rs.getString("id"));
                mov.setIdProduto(rs.getString("id_produto"));
                mov.setIdEmpresa(rs.getString("id_empresa"));
                mov.setQuantidadeMovimentada(rs.getInt("quantidade_movimentada"));
                mov.setValorUnitario(rs.getDouble("valor_unitario"));
                mov.setTipoMovimentacao(rs.getString("tipo_movimentacao"));
                mov.setTipo(rs.getString("tipo"));
                mov.setAtivo(rs.getInt("ativo"));

                lista.add(mov);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar movimentações: " + e.getMessage());
        }
        return lista;
    }

    public ProdutoMovimentacao listarPorId(String id) {
        ProdutoMovimentacao mov = null;
        String sql = "SELECT * FROM produtomovimentacao WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                mov = new ProdutoMovimentacao();
                mov.setId(rs.getString("id"));
                mov.setIdProduto(rs.getString("id_produto"));
                mov.setIdEmpresa(rs.getString("id_empresa"));
                mov.setQuantidadeMovimentada(rs.getInt("quantidade_movimentada"));
                mov.setValorUnitario(rs.getDouble("valor_unitario"));
                mov.setTipoMovimentacao(rs.getString("tipo_movimentacao"));
                mov.setTipo(rs.getString("tipo"));
                mov.setAtivo(rs.getInt("ativo"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar movimentação por ID: " + e.getMessage());
        }
        return mov;
    }

    public void inativar(String id) {
        String sql = "UPDATE produtomovimentacao SET ativo = 0 WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

            System.out.println("Movimentação inativada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inativar movimentação: " + e.getMessage());
        }
    }
    
    public void ativar(String id) {
    	String sql = "UPDATE produtomovimentacao SET ativo = 1 WHERE id = ?";
    	
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, id);
    		ps.executeUpdate();
    		
    		System.out.println("Movimentação ativada com sucesso!");
    	} catch (Exception e) {
    		System.out.println("Erro ao ativar movimentação: " + e.getMessage());
    	}
    }
}
