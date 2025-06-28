package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import connection.DatabaseConnection;
import dao.ProdutoDAO;
import dao.ProdutoMovimentacaoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Produto;
import model.ProdutoMovimentacao;
import model.Usuario;
import view.HtmlForm;
import view.HtmlPage;
import view.HtmlTable;

public class ProdutoController {

    public void cadastrarForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        HtmlPage page = new HtmlPage("Cadastro de Produto", contextPath);
        page.addToBody("<h1>Cadastro de Produto</h1>");

        HtmlForm form = new HtmlForm("post", "?action=cadastrarProduto");
        form.addInput("Descrição", "descricao", "text");
        form.addInput("Quantidade", "quantidade", "number");
        form.addInput("Valor de Custo", "valorCusto", "number");
        form.addInput("Valor de Venda", "valorVenda", "number");
        form.addInput("Quantidade em Estoque", "quantidadeEstoque", "number");
        form.addButton("Cadastrar Produto");

        page.addToBody(form.render());
        response.getWriter().println(page.render());
    }

    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            response.sendRedirect("?action=login");
            return;
        }

        Produto produto = new Produto();
        produto.setDescricao(request.getParameter("descricao"));
        produto.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
        produto.setValorCusto(Double.parseDouble(request.getParameter("valorCusto")));
        produto.setValorVenda(Double.parseDouble(request.getParameter("valorVenda")));
        produto.setQuantidadeEstoque(Integer.parseInt(request.getParameter("quantidadeEstoque")));
        produto.setIdEmpresa(usuario.getIdEmpresa());

        try (Connection conn = DatabaseConnection.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            produtoDAO.cadastrar(produto);
            response.sendRedirect("?action=listarProdutos");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            response.sendRedirect("?action=login");
            return;
        }

        String contextPath = request.getContextPath();
        HtmlPage page = new HtmlPage("Lista de Produtos", contextPath);
        page.addToBody("<h1>Produtos Cadastrados</h1>");

        try (Connection conn = DatabaseConnection.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            List<Produto> produtos = produtoDAO.listarPorEmpresa(usuario.getIdEmpresa());

            HtmlTable table = new HtmlTable();
            table.setHeaders("ID", "Descrição", "Qtd", "Valor Custo", "Valor Venda", "Estoque", "Ativo");

            for (Produto p : produtos) {
                table.addRow(
                    p.getId(),
                    p.getDescricao(),
                    String.valueOf(p.getQuantidade()),
                    String.format("R$ %.2f", p.getValorCusto()),
                    String.format("R$ %.2f", p.getValorVenda()),
                    String.valueOf(p.getQuantidadeEstoque()),
                    p.getAtivo() == 1 ? "Sim" : "Não"
                );
            }

            page.addToBody(table.render());
        } catch (Exception e) {
            e.printStackTrace();
            page.addToBody("<p>Erro ao listar produtos.</p>");
        }

        response.getWriter().println(page.render());
    }

    public void movimentarForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        HtmlPage page = new HtmlPage("Movimentar Produto", contextPath);
        page.addToBody("<h1>Movimentar Produto</h1>");

        HtmlForm form = new HtmlForm("post", "?action=movimentarProduto");
        form.addInput("ID do Produto", "idProduto", "text");
        form.addInput("Quantidade Movimentada", "quantidadeMovimentada", "number");
        form.addInput("Valor Unitário", "valorUnitario", "number");
        form.addSelect("Tipo (Entrada/Saída)", "tipo", new String[]{"Entrada", "Saída"});
        form.addInput("Tipo de Movimentação", "tipoMovimentacao", "text");
        form.addButton("Executar Movimentação");

        page.addToBody(form.render());
        response.getWriter().println(page.render());
    }

    public void movimentar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            response.sendRedirect("?action=login");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String tipo = request.getParameter("tipo");
            int quantidade = Integer.parseInt(request.getParameter("quantidadeMovimentada"));

            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            Produto produto = produtoDAO.listarPorId(request.getParameter("idProduto"));

            if (produto == null) {
                response.getWriter().println("Produto não encontrado.");
                return;
            }

            if ("Entrada".equalsIgnoreCase(tipo)) {
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
            } else if ("Saída".equalsIgnoreCase(tipo)) {
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
            } else {
                response.getWriter().println("Tipo de movimentação inválido.");
                return;
            }

            produtoDAO.atualizar(produto);

            ProdutoMovimentacao mov = new ProdutoMovimentacao();
            mov.setIdProduto(produto.getId());
            mov.setIdEmpresa(usuario.getIdEmpresa());
            mov.setQuantidadeMovimentada(quantidade);
            mov.setValorUnitario(Double.parseDouble(request.getParameter("valorUnitario")));
            mov.setTipoMovimentacao(request.getParameter("tipoMovimentacao"));
            mov.setTipo(tipo);

            ProdutoMovimentacaoDAO dao = new ProdutoMovimentacaoDAO(conn);
            dao.cadastrar(mov);

            response.sendRedirect("?action=listarProdutos");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao movimentar produto: " + e.getMessage());
        }
    }

    public void editarForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        HtmlPage page = new HtmlPage("Editar Produto", contextPath);
        page.addToBody("<h1>Editar Produto</h1>");

        HtmlForm form = new HtmlForm("post", "?action=editarProduto");
        form.addInput("ID do Produto", "id", "text");
        form.addInput("Descrição", "descricao", "text");
        form.addInput("Quantidade", "quantidade", "number");
        form.addInput("Valor de Custo", "valorCusto", "number");
        form.addInput("Valor de Venda", "valorVenda", "number");
        form.addInput("Quantidade em Estoque", "quantidadeEstoque", "number");
        form.addButton("Salvar Alterações");

        page.addToBody(form.render());
        response.getWriter().println(page.render());
    }

    public void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            Produto produto = produtoDAO.listarPorId(request.getParameter("id"));

            if (produto != null) {
                produto.setDescricao(request.getParameter("descricao"));
                produto.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                produto.setValorCusto(Double.parseDouble(request.getParameter("valorCusto")));
                produto.setValorVenda(Double.parseDouble(request.getParameter("valorVenda")));
                produto.setQuantidadeEstoque(Integer.parseInt(request.getParameter("quantidadeEstoque")));
                produtoDAO.atualizar(produto);
            }

            response.sendRedirect("?action=listarProdutos");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao editar produto: " + e.getMessage());
        }
    }
}
