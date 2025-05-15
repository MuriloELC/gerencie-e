package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }
    
    public UsuarioDAO() {
    	
    };

    public void cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuario (id, email, senha, id_empresa, ativo) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getId());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getIdEmpresa());
            ps.setInt(5, usuario.getAtivo());

            ps.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET email = ?, senha = ?, id_empresa = ?, ativo = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getIdEmpresa());
            ps.setInt(4, usuario.getAtivo());
            ps.setString(5, usuario.getId());

            ps.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE ativo = 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setIdEmpresa(rs.getString("id_empresa"));
                usuario.setAtivo(rs.getInt("ativo"));

                lista.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }

        return lista;
    }

    public Usuario listarPorId(String id) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getString("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setIdEmpresa(rs.getString("id_empresa"));
                usuario.setAtivo(rs.getInt("ativo"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }

        return usuario;
    }

    public void inativar(String id) {
        String sql = "UPDATE usuario SET ativo = 0 WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

            System.out.println("Usuário inativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inativar usuário: " + e.getMessage());
        }
    }
    
    public void ativar(String id) {
        String sql = "UPDATE usuario SET ativo = 1 WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

            System.out.println("Usuário ativado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao ativar usuário: " + e.getMessage());
        }
    }
}
