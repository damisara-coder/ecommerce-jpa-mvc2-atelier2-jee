package ma.fstt.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "ligne_panier")
public class LignePanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantite;

    @ManyToOne
    @JoinColumn(name = "id_panier")
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    public LignePanier() { }

    public LignePanier(Panier panier, Produit produit, int quantite) {
        this.panier = panier;
        this.produit = produit;
        this.quantite = quantite;
    }

    public double getSousTotal() { return quantite * produit.getPrix(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
}