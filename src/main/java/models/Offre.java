package models;

import java.util.Objects;

public class Offre {

    int id;
    String desciption;
    String tags;
    Double prix;

    public Offre() {
    }

    public Offre(int id, String desciption, String tags, Double prix) {
        this.id = id;
        this.desciption = desciption;
        this.tags = tags;
        this.prix = prix;
    }

    public Offre(String desciption, String tags, Double prix) {
        this.desciption = desciption;
        this.tags = tags;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", desciption='" + desciption + '\'' + ", tags='" + tags + '\'' + ", prix=" + prix + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offre offre = (Offre) o;
        return Objects.equals(desciption, offre.desciption) && Objects.equals(tags, offre.tags) && Objects.equals(prix, offre.prix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(desciption, tags, prix);
    }
}
