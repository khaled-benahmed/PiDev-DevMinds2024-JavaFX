/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author khaled
 */
public class Planning {
    
    private int id;

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    private String Titre;
    private int activite_id;
    private Date date_planning;
    private String jour_planning;
    private int heure_planning;
    private int heure_fin;


    public Planning() {
    }

    public Planning(int id, int activite_id, String Titre,Date date_planning,String jour_planning, int heure_planning,int heure_fin) {
        this.id = id;
        this.activite_id = activite_id;
        this.date_planning = date_planning;
        this.jour_planning = jour_planning;
        this.heure_planning = heure_planning;
        this.heure_fin = heure_fin;
        this.Titre = Titre;
    }

    public Planning(int activite_id, Date date_planning,String jour_planning, int heure_planning,int heure_fin,String Titre) {
        this.activite_id = activite_id;
        this.date_planning = date_planning;
        this.jour_planning = jour_planning;
        this.heure_planning = heure_planning;
        this.heure_fin = heure_fin;
        this.Titre = Titre;
    }

    public Planning(Date date_planning, String jour_planning, int heure_planning,int heure_fin) {
        this.date_planning = date_planning;
        this.jour_planning = jour_planning;
        this.heure_planning = heure_planning;
        this.heure_fin = heure_fin;
    }


    public Planning(int activite_id, int heure_planning) {
        this.activite_id = activite_id;
        this.heure_planning = heure_planning;
    }

    public Planning(int activite_id) {
        this.activite_id = activite_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_planning() {
        return date_planning;
    }

    public void setDate_planning(Date date_planning) {
        this.date_planning = date_planning;
    }

    public String getJour_planning() {
        return jour_planning;
    }

    public void setJour_planning(String jour_planning) {
        this.jour_planning = jour_planning;
    }

    public int getHeure_planning() {
        return heure_planning;
    }

    public void setHeure_planning(int heure_planning) {
        this.heure_planning = heure_planning;
    }

    public int getActivite_id() {
        return activite_id;
    }

    public void setActivite_id(int activite_id) {
        this.activite_id = activite_id;
    }

    public int getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(int heure_fin) {
        this.heure_fin = heure_fin;
    }


    @Override
    public String toString() {
        return "Planning{" + "id=" + id + 
                ", activite_id=" + activite_id +
                ", date_planning=" + date_planning +
                ", jour_planning=" + jour_planning + 
                ", heure_planning=" + heure_planning +
                ", heure_fin=" + heure_fin +'}';
    }


}
