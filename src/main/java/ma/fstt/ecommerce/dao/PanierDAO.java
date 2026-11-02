package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.model.LignePanier;
import ma.fstt.ecommerce.model.Panier;
import ma.fstt.ecommerce.model.Produit;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class PanierDAO extends GenericDAO<Panier> {

    public PanierDAO() { super(Panier.class); }

    // Lignes du panier d'un internaute (le produit est chargé avec chaque ligne)
    public List<LignePanier> findLignes(Long idInternaute) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT l FROM LignePanier l WHERE l.panier.internaute.id = :id ORDER BY l.id",
                            LignePanier.class)
                    .setParameter("id", idInternaute)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Si le produit est déjà dans le panier : on incrémente la quantité, sinon on crée une ligne
    public void ajouterProduit(Long idInternaute, Long idProduit, int quantite) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Panier panier = em.createQuery(
                            "SELECT p FROM Panier p WHERE p.internaute.id = :id", Panier.class)
                    .setParameter("id", idInternaute)
                    .getSingleResult();
            Produit produit = em.find(Produit.class, idProduit);

            List<LignePanier> existantes = em.createQuery(
                            "SELECT l FROM LignePanier l WHERE l.panier = :panier AND l.produit = :produit",
                            LignePanier.class)
                    .setParameter("panier", panier)
                    .setParameter("produit", produit)
                    .getResultList();

            if (existantes.isEmpty()) {
                em.persist(new LignePanier(panier, produit, quantite));
            } else {
                LignePanier l = existantes.get(0);
                l.setQuantite(l.getQuantite() + quantite);   // objet géré : sauvegardé au commit
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // On vérifie que la ligne appartient bien au panier de l'internaute connecté
    public void supprimerLigne(Long idLigne, Long idInternaute) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            LignePanier l = em.find(LignePanier.class, idLigne);
            if (l != null && l.getPanier().getInternaute().getId().equals(idInternaute)) {
                em.remove(l);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}