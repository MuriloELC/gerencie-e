package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.UsuarioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

public class UsuarioController {

    public void cadastrarForm(HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
        out.println("<html><head><title>Cadastro de Usuário</title></head><body>");
        out.println("<h1>Cadastro de Usuário</h1>");
        out.println("<form method='post' action='AppServlet?acao=usuario-cadastrar'>");
        out.println("Nome: <input type='text' name='nome'><br><br>");
        out.println("Email: <input type='email' name='email'><br><br>");
        out.println("Senha: <input type='password' name='senha'><br><br>");
        out.println("<input type='submit' value='Cadastrar'>");
        out.println("</form>");
        out.println("<br><a href='?action=usuario-listar'>Voltar à Lista</a>");
        out.println("</body></html>");
    }

    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
     //   String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario();
     //   usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.cadastrar(usuario);
        
        response.sendRedirect("?action=usuario-listar");
    }

    public void listar(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	List<Usuario> usuarios = usuarioDAO.listar();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Lista de Usuários</title></head><body>");
        out.println("<h1>Lista de Usuários</h1>");
        out.println("<a href='AppServlet?acao=usuario-cadastrar-form'>Novo Usuário</a><br><br>");
        out.println("<ul>");
        for (Usuario u : usuarios) {
    //        out.println("<li>" + u.getNome() + " - " + u.getEmail() + "</li>");
        }
        out.println("</ul>");
        out.println("</body></html>");
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
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario();
        usuario.setId(id);
  //      usuario.setNome(nome);
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
    //        out.println("<p>Nome: " + usuario.getNome() + "</p>");
            out.println("<p>Email: " + usuario.getEmail() + "</p>");
        } else {
            out.println("<p>Usuário não encontrado!</p>");
        }
        out.println("<a href='AppServlet?acao=usuario-listar'>Voltar</a>");
        out.println("</body></html>");
    }
}
