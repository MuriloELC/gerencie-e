package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import connection.DatabaseConnection;
import dao.EmpresaDAO;
import dao.UsuarioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import model.Usuario;

public class EmpresaController {

	public void cadastrarForm(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("  <head>");
		out.println("    <title>Cadastro de Empresa</title>");
		out.println("    <link rel='stylesheet' href='css/style.css'>");
		out.println("  </head>");
		out.println("  <body>");
		out.println("    <h1>Cadastro de Empresa</h1>");

		out.println("    <form method='post' action='?action=cadastrarEmpresa'>");

		out.println("      <fieldset>");
		out.println("        <legend>Dados da Empresa</legend>");
		out.println("        <label>Nome/Razão:</label><br>");
		out.println("        <input type='text' name='nomeRazao' required><br><br>");

		out.println("        <label>CNPJ:</label><br>");
		out.println("        <input type='text' name='cpfCnpj' required><br><br>");

		out.println("        <label>Cidade:</label><br>");
		out.println("        <input type='text' name='cidade' required><br><br>");

		out.println("        <label>Estado:</label><br>");
		out.println("        <input type='text' name='estado' required><br><br>");
		out.println("      </fieldset><br>");

		out.println("      <fieldset>");
		out.println("        <legend>Usuário Administrador</legend>");
		out.println("        <label>Email:</label><br>");
		out.println("        <input type='email' name='email' required><br><br>");

		out.println("        <label>Senha:</label><br>");
		out.println("        <input type='password' name='senha' required><br><br>");
		out.println("      </fieldset><br>");

		out.println("      <input type='submit' value='Cadastrar Empresa + Admin'>");

		out.println("    </form>");

		out.println("  </body>");
		out.println("</html>");
	}

	public void cadastrarEmpresa(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String nomeRazao = request.getParameter("nomeRazao");
		String cpfCnpj = request.getParameter("cpfCnpj");
		String cidade = request.getParameter("cidade");
		String estado = request.getParameter("estado");
		String emailAdmin = request.getParameter("email");
		String senhaAdmin = request.getParameter("senha");

		Empresa empresa = new Empresa();
		empresa.setNomeRazao(nomeRazao);
		empresa.setCpfCnpj(cpfCnpj);
		empresa.setCidade(cidade);
		empresa.setEstado(estado);

		Usuario admin = new Usuario();
		admin.setEmail(emailAdmin);
		admin.setSenha(senhaAdmin);
//		admin.setAtivo(1);

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

	public void empresaCadastrada(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Sucesso</title></head><body>");
		out.println("<h1>Empresa e Usuário administrador cadastrados com sucesso!</h1>");
		out.println("<a href='?action=login'>Ir para Login</a>");
		out.println("</body></html>");
	}
}