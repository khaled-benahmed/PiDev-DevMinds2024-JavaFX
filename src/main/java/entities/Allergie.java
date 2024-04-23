package entities;

public class Allergie {
    private int id;
    private Plat plat;

    private String nom ;

    private String description ;

    public Allergie() {
    }

    public Allergie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Allergie(int id, Plat plat, String nom, String description) {
        this.id = id;
        this.plat = plat;
        this.nom = nom;
        this.description = description;
    }
    public Allergie(int id_plat, String nom, String description) {
        this.plat = new Plat();
        this.plat.setId_p(id_plat);
        this.nom = nom;
        this.description = description;
    }
    public Allergie(Plat plat, String nom, String description) {
        this.plat = plat;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plat getPlat() {
        return plat;
    }
    public int getIdPlat() {
        if (plat != null) {
            return plat.getId_p();
        } else {
            return -1; // ou une valeur qui indique l'absence de catégorie, selon vos besoins
        }
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Allergie{" +
                "id=" + id +
                ", plat=" + plat +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
