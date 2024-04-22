package test;

import models.Reclamation;
import models.Reponse;
import services.ServiceReclamation;
import services.ServiceReponse;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) {
        //Reclamation reclamation = new Reclamation(7 , "user7", "text7", java.time.LocalDate.of(2224 , 7 , 12), 4);
        // ServiceReclamation serviceReclamation = new ServiceReclamation();
        Reponse reponse = new Reponse(5 ,9, "reponseedit", java.time.LocalDate.of(2028, 8, 14));
        Reponse reponse1 = new Reponse(8, "reponse2", java.time.LocalDate.of(2021, 7, 12));
        Reponse reponse3 = new Reponse(9, "reponse3", java.time.LocalDate.of(2021, 7, 12));

        ServiceReponse serviceReponse = new ServiceReponse();
        try {
            System.out.println(serviceReponse.getReponsesByIdRec(9));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
