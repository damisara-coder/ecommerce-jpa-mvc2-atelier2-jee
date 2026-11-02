package ma.fstt.ecommerce.controller;

import ma.fstt.ecommerce.dao.InternauteDAO;
import ma.fstt.ecommerce.model.Internaute;
import ma.fstt.ecommerce.model.Panier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private InternauteDAO internauteDAO;

    @Override
    public void init() throws ServletException {
        internauteDAO = new InternauteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "connexion";

        if ("connecter".equals(action)) {                       // action 1
            Internaute i = internauteDAO.findByEmail(request.getParameter("email"));
            if (i != null && i.getMotDePasse().equals(request.getParameter("motDePasse"))) {
                request.getSession().setAttribute("internaute", i);
                response.sendRedirect(request.getContextPath() + "/vitrine");
            } else {
                request.setAttribute("erreur", "Email ou mot de passe incorrect.");
                afficher(request, response);
            }

        } else if ("inscrire".equals(action)) {                 // action 2
            String email = request.getParameter("email");
            if (internauteDAO.findByEmail(email) != null) {
                request.setAttribute("erreur", "Cet email est déjà utilisé.");
                afficher(request, response);
            } else {
                Internaute i = new Internaute(
                        request.getParameter("nom"),
                        request.getParameter("prenom"),
                        email,
                        request.getParameter("motDePasse"));
                i.setPanier(new Panier());          // un internaute = un seul panier, créé d'emblée
                internauteDAO.save(i);              // cascade : le panier est enregistré aussi
                request.getSession().setAttribute("internaute", i);
                response.sendRedirect(request.getContextPath() + "/vitrine");
            }

        } else if ("deconnecter".equals(action)) {              // action 3
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/vitrine");

        } else {                                                // par défaut : formulaires
            afficher(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void afficher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vues/connexion.jsp").forward(request, response);
    }
}