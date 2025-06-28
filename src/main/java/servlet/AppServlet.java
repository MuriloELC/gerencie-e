package servlet;

import java.io.IOException;

import controller.DashboardController;
import controller.EmpresaController;
import controller.LoginController;
import controller.ProdutoController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/*")
public class AppServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AppServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String acao = request.getParameter("action");
        EmpresaController empresaController = new EmpresaController();
        LoginController loginController = new LoginController();
        DashboardController dashboardController = new DashboardController();
        ProdutoController produtoController = new ProdutoController();

        switch (acao != null ? acao : "") {
            case "cadastrarEmpresaForm":
                empresaController.cadastrarForm(request, response);
                break;
            case "empresaCadastrada":
                empresaController.empresaCadastrada(request, response);
                break;
            case "listarEmpresas":
                empresaController.listarEmpresas(request, response);
                break;
            case "login":
                loginController.loginForm(request, response);
                break;
            case "dashboard":
                dashboardController.dashboard(request, response);
                break;
            case "cadastrarProdutoForm":
                produtoController.cadastrarForm(request, response);
                break;
            case "listarProdutos":
                produtoController.listarProdutos(request, response);
                break;
            case "movimentarProdutoForm":
                produtoController.movimentarForm(request, response);
                break;
            case "editarProdutoForm":
                produtoController.editarForm(request, response);
                break;
            default:
                response.sendRedirect("?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("action");
        EmpresaController empresaController = new EmpresaController();
        LoginController loginController = new LoginController();
        ProdutoController produtoController = new ProdutoController();

        switch (acao != null ? acao : "") {
            case "cadastrarEmpresa":
                empresaController.cadastrarEmpresa(request, response);
                break;
            case "loginUsuario":
                loginController.loginUsuario(request, response);
                break;
            case "cadastrarProduto":
                produtoController.cadastrar(request, response);
                break;
            case "movimentarProduto":
                produtoController.movimentar(request, response);
                break;
            case "editarProduto":
                produtoController.editar(request, response);
                break;
            default:
                response.getWriter().println("Ação POST não reconhecida.");
        }
    }
}
