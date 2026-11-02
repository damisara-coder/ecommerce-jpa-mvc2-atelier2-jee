package ma.fstt.ecommerce.test;

import ma.fstt.ecommerce.dao.ProduitDAO;
import ma.fstt.ecommerce.model.*;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;

public class TestJPA {

    public static void main(String[] args) {

        // Test 1 : un produit simple via le DAO
        ProduitDAO dao = new ProduitDAO();
        Produit clavier = dao.save(new Produit("Clavier", "Clavier mécanique", 350.0, 20));
        System.out.println("Produits en base : " + dao.findAll().size());

        // Test 2 : les relations, avec un seul EntityManager
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Produit ecran = new Produit("Écran", "Écran 24 pouces", 1500.0, 10);
            em.persist(ecran);

            Internaute client = new Internaute("Alami", "Youssef", "youssef@mail.com", "1234");
            Panier panier = new Panier();
            client.setPanier(panier);

            panier.ajouterProduit(em.find(Produit.class, clavier.getId()), 2);
            panier.ajouterProduit(ecran, 1);

            em.persist(client);     // cascade : panier puis lignes
            em.getTransaction().commit();

            System.out.println("Total du panier : " + panier.calculerTotal());   // 2200.0
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.close();
        }
    }
}