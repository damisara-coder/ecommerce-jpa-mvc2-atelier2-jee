package ma.fstt.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String designation;

    @Column(length = 500)
    private String description;

    private double prix;
    private int stock;

    public Produit() { }   // obligatoire pour JPA

    public Produit(String designation, String description, double prix, int stock) {
        this.designation = designation;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    public boolean estDisponible() { return stock > 0; }

    public void decrementerStock(int q) { this.stock -= q; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}