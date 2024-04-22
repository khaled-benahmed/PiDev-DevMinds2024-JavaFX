package models;

import java.time.LocalDate;

public class Reponse {

    int id ;
    int idReclamation ;
    String contenuResponse ;
    LocalDate dateReponse;

    public Reponse() {
    }

    public Reponse(int id, int idReclamation, String contenuResponse, LocalDate dateReponse) {
        this.id = id;
        this.idReclamation = idReclamation;
        this.contenuResponse = contenuResponse;
        this.dateReponse = dateReponse;
    }

    public Reponse(int idReclamation, String contenuResponse, LocalDate dateReponse) {
        this.idReclamation = idReclamation;
        this.contenuResponse = contenuResponse;
        this.dateReponse = dateReponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getContenuResponse() {
        return contenuResponse;
    }

    public void setContenuResponse(String contenuResponse) {
        this.contenuResponse = contenuResponse;
    }

    public LocalDate getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", idReclamation=" + idReclamation +
                ", contenuResponse='" + contenuResponse + '\'' +
                ", dateReponse=" + dateReponse +
                '}';
    }
}
