package ma.fstt.ecommerce.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "panier")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    // côté propriétaire : crée la colonne id_internaute (unique = un seul panier par client)
    @OneToOne
    @JoinColumn(name = "id_internaute", unique = true, nullable = false)
    private Internaute internaute;

    // composition : les lignes vivent et meurent avec le panier
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignes = new ArrayList<>();

    public Panier() {
        this.dateCreation = new Date();
    }

    public void ajouterProduit(Produit p, int quantite) {
        for (LignePanier l : lignes) {
            Produit lp = l.getProduit();
            boolean memeProduit = (lp == p)
                    || (lp.getId() != null && lp.getId().equals(p.getId()));
            if (memeProduit) {
                l.setQuantite(l.getQuantite() + quantite);
                return;
            }
        }
        lignes.add(new LignePanier(this, p, quantite));
    }

    public void retirerLigne(LignePanier l) {
        lignes.remove(l);
        l.setPanier(null);
    }

    public double calculerTotal() {
        double total = 0;
        for (LignePanier l : lignes) total += l.getSousTotal();
        return total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    public Internaute getInternaute() { return internaute; }
    public void setInternaute(Internaute internaute) { this.internaute = internaute; }
    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) { this.lignes = lignes; }
}