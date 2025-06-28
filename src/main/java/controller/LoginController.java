package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import connection.DatabaseConnection;
import dao.UsuarioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import util.HashUtil;
import view.HtmlForm;
import view.HtmlPage;

public class LoginController {

	public void loginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getContextPath();
		HtmlPage page = new HtmlPage("Login", contextPath);

		page.addToBody("<h1>Login</h1>");

		HtmlForm form = new HtmlForm("post", "?action=loginUsuario");
		form.addInput("Email", "email", "email");
		form.addInput("Senha", "senha", "password");
		form.addButton("Entrar");

		page.addToBody(form.render());

		// Link para cadastro caso não tenha conta
		page.addToBody("<p>Não tem cadastro? <a href='?action=cadastrarEmpresaForm'>Clique aqui para cadastrar sua empresa</a></p>");

		response.getWriter().println(page.render());
	}

	public void loginUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String senha = HashUtil.sha256(request.getParameter("senha"));

		try (Connection conn = DatabaseConnection.getConnection()) {
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			List<Usuario> usuarios = usuarioDAO.listar();

			for (Usuario u : usuarios) {
				if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
					HttpSession session = request.getSession();
					session.setAttribute("usuarioLogado", u);

					// Redireciona para dashboard após login bem-sucedido
					response.sendRedirect("?action=dashboard");
					return;
				}
			}

			// Login inválido
			String contextPath = request.getContextPath();
			HtmlPage page = new HtmlPage("Login Inválido", contextPath);
			page.addToBody("<h1>Login inválido</h1>");
			page.addToBody("<p>Email ou senha incorretos.</p>");
			page.addToBody("<a href='?action=login'>Tentar novamente</a>");
			response.getWriter().println(page.render());

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Erro ao realizar login.");
		}
	}
}
