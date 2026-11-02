package ma.fstt.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "internaute")
public class Internaute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    private String motDePasse;

    // côté "Un" : la clé étrangère est dans la table panier
    @OneToOne(mappedBy = "internaute", cascade = CascadeType.ALL)
    private Panier panier;

    public Internaute() { }

    public Internaute(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public Panier getPanier() { return panier; }

    // garde les deux côtés de la relation cohérents
    public void setPanier(Panier panier) {
        this.panier = panier;
        if (panier != null) panier.setInternaute(this);
    }
}