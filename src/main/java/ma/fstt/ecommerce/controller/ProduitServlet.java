package ma.fstt.ecommerce.controller;

import ma.fstt.ecommerce.dao.ProduitDAO;
import ma.fstt.ecommerce.model.Produit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vitrine")
public class ProduitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProduitDAO produitDAO;

    @Override
    public void init() throws ServletException {
        produitDAO = new ProduitDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "lister";

        if ("ajouter".equals(action)) {                         // action 1
            Produit p = new Produit(
                    request.getParameter("designation"),
                    request.getParameter("description"),
                    Double.parseDouble(request.getParameter("prix")),
                    Integer.parseInt(request.getParameter("stock")));
            produitDAO.save(p);
            response.sendRedirect(request.getContextPath() + "/vitrine?action=lister");

        } else if ("supprimer".equals(action)) {                // action 2
            try {
                produitDAO.delete(Long.valueOf(request.getParameter("id")));
            } catch (RuntimeException e) {
                request.setAttribute("erreur",
                        "Suppression impossible : ce produit est présent dans un panier.");
                lister(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/vitrine?action=lister");

        } else {                                                // action par défaut : lister
            lister(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void lister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("produits", produitDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/vues/vitrine.jsp").forward(request, response);
    }
}