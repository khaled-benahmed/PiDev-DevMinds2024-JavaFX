package models;

import java.util.Objects;

public class Sponsor {

    int id;
    String nomSponsor;
    Double donnation;
    String image;

    public Sponsor() {
    }

    public Sponsor(int id, String nomSponsor, Double donnation, String image) {
        this.id = id;
        this.nomSponsor = nomSponsor;
        this.donnation = donnation;
        this.image = image;
    }

    public Sponsor(String nomSponsor, Double donnation, String image) {
        this.nomSponsor = nomSponsor;
        this.donnation = donnation;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomSponsor() {
        return nomSponsor;
    }

    public void setNomSponsor(String nomSponsor) {
        this.nomSponsor = nomSponsor;
    }

    public Double getDonnation() {
        return donnation;
    }

    public void setDonnation(Double donnation) {
        this.donnation = donnation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", nomSponsor='" + nomSponsor + '\'' +
                ", donnation=" + donnation +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sponsor sponsor = (Sponsor) o;
        return Objects.equals(nomSponsor, sponsor.nomSponsor) && Objects.equals(donnation, sponsor.donnation) && Objects.equals(image, sponsor.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomSponsor, donnation, image);
    }
}
