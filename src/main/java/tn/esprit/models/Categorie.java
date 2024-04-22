package tn.esprit.models;

public class Categorie {
    private int id;
    private String nom_categorie,description_c;
    public static Categorie categorie;
    public Categorie() {}
    public Categorie(int ID, String NOMCATEGORIE, String DESCRIPTION) {
        this.id = ID;
        this.nom_categorie = NOMCATEGORIE;
        this.description_c = DESCRIPTION;
    }
    public Categorie(String NOMCATEGORIE, String DESCRIPTION) {
        this.nom_categorie = NOMCATEGORIE;
        this.description_c = DESCRIPTION;
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNom_categorie() {return nom_categorie;}
    public void setNom_categorie(String nom_categorie) {this.nom_categorie = nom_categorie;}

    public String getdescription_c() {return description_c;}
    public void setdescription_c(String description_c) {this.description_c = description_c;}

    public static Categorie getCategorie() {return categorie;}
    public static void setCategorie(Categorie categorie) {Categorie.categorie = categorie;}
    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom_categorie='" + nom_categorie + '\'' +
                ", description_c='" + description_c + '\'' +
                '}';
    }


}
