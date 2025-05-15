package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection; //Porque
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import connection.DatabaseConnection;
import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioController {

    public void cadastrarForm(HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
        out.println("<html><head><title>Cadastro de Usuário</title></head><body>");
        out.println("<h1>Cadastro de Usuário</h1>");
        out.println("<form method='post' action='?action=cadastrarUsuario'>");
        out.println("Email: <input type='email' name='email' required><br><br>");
        out.println("Senha: <input type='password' name='senha' required><br><br>");
        out.println("<input type='submit' value='Cadastrar'>");
        out.println("</form>");
        out.println("<br><a href='?action=usuario-listar'>Voltar à Lista</a>");
        out.println("</body></html>");
    }

    public void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setIdEmpresa("dc8cfc0c-34ab-475b-bf56-9b1cc7f4b87b"); // ID fixo ou dinâmico depois

        try (Connection conn = DatabaseConnection.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            usuarioDAO.cadastrar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("?action=usuario-listar");
    }

    public void listar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            List<Usuario> usuarios = usuarioDAO.listar();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html><head><title>Lista de Usuários</title></head><body>");
            out.println("<h1>Lista de Usuários</h1>");
            out.println("<a href='?action=cadastrarUsuarioForm'>Novo Usuário</a><br><br>");
            out.println("<ul>");
            for (Usuario u : usuarios) {
                out.println("<li>" + u.getEmail() + "</li>");
            }
            out.println("</ul>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
        String id = (request.getParameter("id"));
        usuarioDAO.inativar(id);
        response.sendRedirect("AppServlet?acao=usuario-listar");
    }

    public void atualizar(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
        String id = (request.getParameter("id"));
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        usuarioDAO.atualizar(usuario);
        response.sendRedirect("AppServlet?acao=usuario-listar");
    }

    public void buscarPorId(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
        String id = (request.getParameter("id"));
        Usuario usuario = usuarioDAO.listarPorId(id);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Detalhes do Usuário</title></head><body>");
        if (usuario != null) {
            out.println("<h1>Detalhes do Usuário</h1>");
            out.println("<p>Email: " + usuario.getEmail() + "</p>");
        } else {
            out.println("<p>Usuário não encontrado!</p>");
        }
        out.println("<a href='AppServlet?acao=usuario-listar'>Voltar</a>");
        out.println("</body></html>");
    }
}
