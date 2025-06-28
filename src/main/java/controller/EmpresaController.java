package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import connection.DatabaseConnection;
import dao.EmpresaDAO;
import dao.UsuarioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import model.Usuario;
import util.HashUtil;
import view.HtmlForm;
import view.HtmlPage;
import view.HtmlTable;

public class EmpresaController {

	public void cadastrarForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getContextPath();
		HtmlPage page = new HtmlPage("Cadastro de Empresa", contextPath);

		page.addToBody("<h1>Cadastro de Empresa</h1>");

		HtmlForm form = new HtmlForm("post", "?action=cadastrarEmpresa");

		// Dados da empresa
		form.addInput("Nome/Razão", "nomeRazao", "text");
		form.addInput("CNPJ", "cpfCnpj", "text");
		form.addInput("Cidade", "cidade", "text");
		form.addInput("Estado", "estado", "text");

		// Dados do usuário administrador
		form.addInput("Email do Administrador", "email", "email");
		form.addInput("Senha do Administrador", "senha", "password");

		form.addButton("Cadastrar Empresa e Administrador");

		page.addToBody(form.render());

		response.getWriter().println(page.render());
	}

	public void cadastrarEmpresa(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Coleta dados da empresa
		String nomeRazao = request.getParameter("nomeRazao");
		String cpfCnpj = request.getParameter("cpfCnpj");
		String cidade = request.getParameter("cidade");
		String estado = request.getParameter("estado");

		// Coleta dados do admin
		String emailAdmin = request.getParameter("email");
		String senhaAdmin = request.getParameter("senha");

		Empresa empresa = new Empresa();
		empresa.setId(UUID.randomUUID().toString());
		empresa.setNomeRazao(nomeRazao);
		empresa.setCpfCnpj(cpfCnpj);
		empresa.setCidade(cidade);
		empresa.setEstado(estado);
		empresa.setAtivo(1); // 1 = ativo

		Usuario admin = new Usuario();
		admin.setEmail(emailAdmin);
		admin.setSenha(HashUtil.sha256(senhaAdmin));

		try (Connection conn = DatabaseConnection.getConnection()) {
			EmpresaDAO empresaDAO = new EmpresaDAO(conn);
			empresaDAO.cadastrar(empresa);

			admin.setIdEmpresa(empresa.getId());
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			usuarioDAO.cadastrar(admin);

			response.sendRedirect("?action=empresaCadastrada");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Erro ao cadastrar empresa e usuário admin.");
		}
	}

	public void empresaCadastrada(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getContextPath();
		HtmlPage page = new HtmlPage("Cadastro Realizado", contextPath);

		page.addToBody("<h1>Cadastro realizado com sucesso!</h1>");
		page.addToBody("<p>Você já pode fazer login com seu usuário administrador.</p>");
		page.addToBody("<a href='?action=login'>Ir para Login</a>");

		response.getWriter().println(page.render());
	}

	public void listarEmpresas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getContextPath();
		HtmlPage page = new HtmlPage("Empresas Cadastradas", contextPath);

		try (Connection conn = DatabaseConnection.getConnection()) {
			EmpresaDAO empresaDAO = new EmpresaDAO(conn);
			List<Empresa> empresas = empresaDAO.listar();

			page.addToBody("<h1>Empresas Cadastradas</h1>");

			HtmlTable table = new HtmlTable();
			table.setHeaders("ID", "Nome/Razão", "CNPJ", "Cidade", "Estado");

			for (Empresa e : empresas) {
				table.addRow(
					e.getId(),
					e.getNomeRazao(),
					e.getCpfCnpj(),
					e.getCidade(),
					e.getEstado()
				);
			}

			page.addToBody(table.render());

		} catch (Exception e) {
			e.printStackTrace();
			page.addToBody("<p>Erro ao listar empresas.</p>");
		}

		response.getWriter().println(page.render());
	}
}
