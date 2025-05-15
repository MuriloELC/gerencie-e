package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Empresa;

public class EmpresaDAO {
    private Connection conn;

    public EmpresaDAO(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(Empresa empresa) {
        String sql = "INSERT INTO empresa (id, nomerazao, apelidofantasia, cpfCnpj, "
                + "bairro, cep, cidade, pais, estado, logradouro, contato01, "
                + "contato02, email, emailFinanceiro, ativo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empresa.getId());
            ps.setString(2, empresa.getNomeRazao());
            ps.setString(3, empresa.getApelidoFantasia());
            ps.setString(4, empresa.getCpfCnpj());
            ps.setString(5, empresa.getBairro());
            ps.setString(6, empresa.getCep());
            ps.setString(7, empresa.getCidade());
            ps.setString(8, empresa.getPais());
            ps.setString(9, empresa.getEstado());
            ps.setString(10, empresa.getLogradouro());
            ps.setString(11, empresa.getContato01());
            ps.setString(12, empresa.getContato02());
            ps.setString(13, empresa.getEmail());
            ps.setString(14, empresa.getEmailFinanceiro());
            ps.setInt(15, empresa.getAtivo());

            ps.executeUpdate();

            System.out.println("Empresa cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    public void atualizar(Empresa empresa) {
        String sql = "UPDATE empresa SET nomerazao = ?, apelidofantasia = ?, cpfCnpj = ?, "
                + "bairro = ?, cep = ?, cidade = ?, pais = ?, estado = ?, logradouro = ?, "
                + "contato01 = ?, contato02 = ?, email = ?, emailFinanceiro = ?, ativo = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empresa.getNomeRazao());
            ps.setString(2, empresa.getApelidoFantasia());
            ps.setString(3, empresa.getCpfCnpj());
            ps.setString(4, empresa.getBairro());
            ps.setString(5, empresa.getCep());
            ps.setString(6, empresa.getCidade());
            ps.setString(7, empresa.getPais());
            ps.setString(8, empresa.getEstado());
            ps.setString(9, empresa.getLogradouro());
            ps.setString(10, empresa.getContato01());
            ps.setString(11, empresa.getContato02());
            ps.setString(12, empresa.getEmail());
            ps.setString(13, empresa.getEmailFinanceiro());
            ps.setInt(14, empresa.getAtivo());
            ps.setString(15, empresa.getId());

            ps.executeUpdate();

            System.out.println("Empresa atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    public List<Empresa> listar() {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM empresa WHERE ativo = 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getString("id"));
                empresa.setNomeRazao(rs.getString("nomerazao"));
                empresa.setApelidoFantasia(rs.getString("apelidofantasia"));
                empresa.setCpfCnpj(rs.getString("cpfCnpj"));
                empresa.setBairro(rs.getString("bairro"));
                empresa.setCep(rs.getString("cep"));
                empresa.setCidade(rs.getString("cidade"));
                empresa.setPais(rs.getString("pais"));
                empresa.setEstado(rs.getString("estado"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setContato01(rs.getString("contato01"));
                empresa.setContato02(rs.getString("contato02"));
                empresa.setEmail(rs.getString("email"));
                empresa.setEmailFinanceiro(rs.getString("emailFinanceiro"));
                empresa.setAtivo(rs.getInt("ativo"));

                lista.add(empresa);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar empresas: " + e.getMessage());
        }
        return lista;
    }

    public Empresa listarPorId(String id) {
        Empresa empresa = null;
        String sql = "SELECT * FROM empresa WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getString("id"));
                empresa.setNomeRazao(rs.getString("nomerazao"));
                empresa.setApelidoFantasia(rs.getString("apelidofantasia"));
                empresa.setCpfCnpj(rs.getString("cpfCnpj"));
                empresa.setBairro(rs.getString("bairro"));
                empresa.setCep(rs.getString("cep"));
                empresa.setCidade(rs.getString("cidade"));
                empresa.setPais(rs.getString("pais"));
                empresa.setEstado(rs.getString("estado"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setContato01(rs.getString("contato01"));
                empresa.setContato02(rs.getString("contato02"));
                empresa.setEmail(rs.getString("email"));
                empresa.setEmailFinanceiro(rs.getString("emailFinanceiro"));
                empresa.setAtivo(rs.getInt("ativo"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar empresa por ID: " + e.getMessage());
        }
        return empresa;
    }

    public void inativar(String id) {
        String sql = "UPDATE empresa SET ativo = 0 WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

            System.out.println("Empresa inativada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inativar empresa: " + e.getMessage());
        }
    }
    
    public void ativar(String id) {
    	String sql = "UPDATE empresa SET ativo = 1 WHERE id = ?";
    	
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, id);
    		ps.executeUpdate();
    		
    		System.out.println("Empresa ativada com sucesso!");
    	} catch (Exception e) {
    		System.out.println("Erro ao ativar empresa: " + e.getMessage());
    	}
    }
}
