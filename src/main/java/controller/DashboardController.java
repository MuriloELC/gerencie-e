package controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import view.HtmlMenu;
import view.HtmlPage;

public class DashboardController {

	public void dashboard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getContextPath();

		HttpSession session = request.getSession(false);
		Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

		if (usuario == null) {
			response.sendRedirect("?action=login");
			return;
		}

		HtmlPage page = new HtmlPage("Painel de Controle", contextPath);

		page.addToBody("<h1>Bem-vindo ao Painel</h1>");

		HtmlMenu menu = new HtmlMenu();

		// Menu de produtos / estoque
		menu.addItem("Cadastrar Produto", "cadastrarProdutoForm");
		menu.addItem("Listar Produtos", "listarProdutos");
		menu.addItem("Movimentar Produto", "movimentarProdutoForm");

		// Empresas
		menu.addItem("Cadastrar Empresa", "cadastrarEmpresaForm");
		menu.addItem("Listar Empresas", "listarEmpresas");

		// Usuários
		menu.addItem("Cadastrar Usuário", "cadastrarUsuarioForm");
		menu.addItem("Listar Usuários", "listarUsuarios");

		// Acesso
		menu.addItem("Logout", "logout");

		page.addToBody(menu.render());

		response.getWriter().println(page.render());
	}
}