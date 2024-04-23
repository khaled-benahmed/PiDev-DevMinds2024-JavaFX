package entities;

public class Plat {

    private int id_p;
    private String nom;
    private String calories;
    private String prix;
    private String image;
    private String allergie; // Stocke le nom de l'allergie sous forme de texte

private Allergie allergies;
    public Plat() {
    }

    public Plat(int id_p, String nom, String calories, String prix, String image, String allergie) {
        this.id_p = id_p;
        this.nom = nom;
        this.calories = calories;
        this.prix = prix;
        this.image = image;
        this.allergie = allergie;
    }
    public Plat(String nom, String calories, String prix, String image, Allergie allergies) {
        this.nom = nom;
        this.calories = calories;
        this.prix = prix;
        this.image = image;
        this.allergies = allergies;
    }

    public Plat(String nom, String calories, String prix, String image, String allergie) {
        this.nom = nom;
        this.calories = calories;
        this.prix = prix;
        this.image = image;
        this.allergie = allergie;
    }

    public Plat(int id_p) {
        this.id_p = id_p;
    }
    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAllergie() {
        return allergie;
    }

    public void setAllergie(String allergie) {
        this.allergie = allergie;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id_p=" + id_p +
                ", nom='" + nom + '\'' +
                ", calories='" + calories + '\'' +
                ", prix='" + prix + '\'' +
                ", image='" + image + '\'' +
                ", allergie='" + allergie + '\'' +
                '}';
    }
}


