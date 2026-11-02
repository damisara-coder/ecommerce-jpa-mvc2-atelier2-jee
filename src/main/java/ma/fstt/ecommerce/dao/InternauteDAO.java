package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.model.Internaute;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class InternauteDAO extends GenericDAO<Internaute> {

    public InternauteDAO() { super(Internaute.class); }

    public Internaute findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Internaute> r = em.createQuery(
                            "SELECT i FROM Internaute i WHERE i.email = :email", Internaute.class)
                    .setParameter("email", email)
                    .getResultList();
            return r.isEmpty() ? null : r.get(0);
        } finally {
            em.close();
        }
    }
}