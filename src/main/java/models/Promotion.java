package models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Promotion {

    int id ;
    int sponsorId ;
    String codePromotion ;
    Double reductionPromotion ;
    LocalDate dateExpiration ;

    public Promotion() {
    }

    public Promotion(int id, int sponsorId, String codePromotion, Double reductionPromotion, LocalDate dateExpiration) {
        this.id = id;
        this.sponsorId = sponsorId;
        this.codePromotion = codePromotion;
        this.reductionPromotion = reductionPromotion;
        this.dateExpiration = dateExpiration;
    }

    public Promotion(int sponsorId, String codePromotion, Double reductionPromotion, LocalDate dateExpiration) {
        this.sponsorId = sponsorId;
        this.codePromotion = codePromotion;
        this.reductionPromotion = reductionPromotion;
        this.dateExpiration = dateExpiration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getCodePromotion() {
        return codePromotion;
    }

    public void setCodePromotion(String codePromotion) {
        this.codePromotion = codePromotion;
    }

    public Double getReductionPromotion() {
        return reductionPromotion;
    }

    public void setReductionPromotion(Double reductionPromotion) {
        this.reductionPromotion = reductionPromotion;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", sponsorId=" + sponsorId +
                ", codePromotion='" + codePromotion + '\'' +
                ", reductionPromotion=" + reductionPromotion +
                ", dateExpiration=" + dateExpiration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return sponsorId == promotion.sponsorId && Objects.equals(codePromotion, promotion.codePromotion) && Objects.equals(reductionPromotion, promotion.reductionPromotion) && Objects.equals(dateExpiration, promotion.dateExpiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sponsorId, codePromotion, reductionPromotion, dateExpiration);
    }
}
