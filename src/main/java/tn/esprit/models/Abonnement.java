package tn.esprit.models;

import java.sql.Date;

public class Abonnement {
    private int id,prix_a,categorie_abonnement;
    private String nom_a,description_a;
private Date date_debut_a,date_fin_a;
    public static Abonnement abonnement;
    public Abonnement() {}
    public Abonnement(int ID, String NOM, String DESCRIPTION, int PRIX, Date DATEDEBUT, Date DATEFIN, int CATEGORIEABONNEMENT) {
        this.id = ID;
        this.nom_a = NOM;
        this.description_a = DESCRIPTION;
        this.prix_a = PRIX;
        this.date_debut_a = DATEDEBUT;
        this.date_fin_a = DATEFIN;
        this.categorie_abonnement = CATEGORIEABONNEMENT;
    }
    public Abonnement(String NOM, String DESCRIPTION, int PRIX, Date DATEDEBUT, Date DATEFIN, int CATEGORIEABONNEMENT) {

        this.nom_a = NOM;
        this.description_a = DESCRIPTION;
        this.prix_a = PRIX;
        this.date_debut_a = DATEDEBUT;
        this.date_fin_a = DATEFIN;
        this.categorie_abonnement = CATEGORIEABONNEMENT;
    }
    public Abonnement(String NOM, String DESCRIPTION, int PRIX, Date DATEDEBUT, Date DATEFIN) {

        this.nom_a = NOM;
        this.description_a = DESCRIPTION;
        this.prix_a = PRIX;
        this.date_debut_a = DATEDEBUT;
        this.date_fin_a = DATEFIN;

    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNom_a() {return nom_a;}
    public void setNom_a(String nom_a) {this.nom_a = nom_a;}
    public String getDescription_a() {return description_a;}
    public void setDescription_a(String description_a) {this.description_a = description_a;}

    public int getPrix_a() {return prix_a;}
    public void setPrix_a(int prix_a) {this.prix_a = prix_a;}
    public Date getDate_debut_a() {return date_debut_a;}
    public void setDate_debut_a(Date date_debut_a) {this.date_debut_a = date_debut_a;}
    public Date getDate_fin_a() {return date_fin_a;}
    public void setDate_fin_a(Date date_fin_a) {this.date_fin_a = date_fin_a;}
    public int getCategorie_abonnement() {return categorie_abonnement;}
    public void setCategorie_abonnement(int categorie_abonnement) {this.categorie_abonnement = categorie_abonnement;}
    public static Abonnement getAbonnement() {return abonnement;}
    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", nom_a='" + nom_a + '\'' +
                ", description_a='" + description_a + '\'' +
                ", prix_a='" + prix_a + '\'' +
                ", date_debut_a='" + date_debut_a + '\'' +
                ", date_fin_a='" + date_fin_a + '\'' +
                ", categorie_abonnement='" + categorie_abonnement + '\'' +
                '}';
    }
}
