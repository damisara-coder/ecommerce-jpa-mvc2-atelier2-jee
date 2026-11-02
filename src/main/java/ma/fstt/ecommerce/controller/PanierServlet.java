package ma.fstt.ecommerce.controller;

import ma.fstt.ecommerce.dao.PanierDAO;
import ma.fstt.ecommerce.model.Internaute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PanierDAO panierDAO;

    @Override
    public void init() throws ServletException {
        panierDAO = new PanierDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Il faut être connecté pour avoir un panier
        Internaute client = (Internaute) request.getSession().getAttribute("internaute");
        if (client == null) {
            response.sendRedirect(request.getContextPath() + "/client");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "afficher";

        if ("ajouter".equals(action)) {                         // action 1
            Long idProduit = Long.valueOf(request.getParameter("idProduit"));
            panierDAO.ajouterProduit(client.getId(), idProduit, 1);
            response.sendRedirect(request.getContextPath() + "/vitrine?action=lister");

        } else if ("supprimer".equals(action)) {                // action 2
            Long idLigne = Long.valueOf(request.getParameter("idLigne"));
            panierDAO.supprimerLigne(idLigne, client.getId());
            response.sendRedirect(request.getContextPath() + "/panier?action=afficher");

        } else {                                                // par défaut : afficher
            request.setAttribute("lignes", panierDAO.findLignes(client.getId()));
            request.getRequestDispatcher("/WEB-INF/vues/panier.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}