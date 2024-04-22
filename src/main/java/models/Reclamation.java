package models;

import java.time.LocalDate;
import java.util.Objects;

public class Reclamation {

    int id ;
    String nomUser ;
    String textReclamation;

    LocalDate dateReclamation;

    int idUser ;

    public Reclamation() {
    }

    public Reclamation(int id, String nomUser, String textReclamation, LocalDate dateReclamation, int idUser) {
        this.id = id;
        this.nomUser = nomUser;
        this.textReclamation = textReclamation;
        this.dateReclamation = dateReclamation;
        this.idUser = idUser;
    }

    public Reclamation(String nomUser, String textReclamation, LocalDate dateReclamation, int idUser) {
        this.nomUser = nomUser;
        this.textReclamation = textReclamation;
        this.dateReclamation = dateReclamation;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getTextReclamation() {
        return textReclamation;
    }

    public void setTextReclamation(String textReclamation) {
        this.textReclamation = textReclamation;
    }

    public LocalDate getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(LocalDate dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", nomUser='" + nomUser + '\'' +
                ", textReclamation='" + textReclamation + '\'' +
                ", dateReclamation=" + dateReclamation +
                ", idUser=" + idUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return idUser == that.idUser && Objects.equals(nomUser, that.nomUser) && Objects.equals(textReclamation, that.textReclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomUser, textReclamation, idUser);
    }
}
