package services;

import models.Reclamation;
import models.Reponse;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IService<Reponse> {
    private Connection connection;

    public ServiceReponse() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Reponse reponse) throws SQLException {
        String sql = "insert into reponse (idreclamation_id,contenu_reponse,date_reponse) VALUES (?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, reponse.getIdReclamation());
        pst.setString(2, reponse.getContenuResponse());
        pst.setDate(3, Date.valueOf(reponse.getDateReponse()));
        pst.executeUpdate();
        System.out.println("Reponse added");

    }

    @Override
    public void update(Reponse reponse) throws SQLException {
        String sql = "update reponse set idreclamation_id = ?,  contenu_reponse = ? , date_reponse = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, reponse.getIdReclamation());
        preparedStatement.setString(2, reponse.getContenuResponse());
        preparedStatement.setDate(3, Date.valueOf(reponse.getDateReponse()));
        preparedStatement.setInt(4, reponse.getId());
        preparedStatement.executeUpdate();
        System.out.println("Reponse with id = " + reponse.getId() + " updated");

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from reponse where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Reponse with id = " + id + " deleted");

    }

    @Override
    public List<Reponse> getAll() throws SQLException {
        String sql = "select * from reponse";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Reponse> reponses = new ArrayList<>();
        while (rs.next()) {
            Reponse rep = new Reponse();
            rep.setId(rs.getInt("id"));
            rep.setDateReponse(rs.getDate("date_reponse").toLocalDate());
            rep.setContenuResponse(rs.getString("contenu_reponse"));
            rep.setIdReclamation(rs.getInt("idreclamation_id"));

            reponses.add(rep);
        }
        return reponses;
    }

    @Override
    public Reponse getById(int id) throws SQLException {
        String sql = "SELECT * FROM reponse WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Reponse rep = new Reponse();
        if (rs.next()) {
            rep.setId(rs.getInt("id"));
            rep.setDateReponse(rs.getDate("date_reponse").toLocalDate());
            rep.setContenuResponse(rs.getString("contenu_reponse"));
            rep.setIdReclamation(rs.getInt("idreclamation_id"));
            System.out.println("Reponse with id = " + id + " found");
            return rep;

        }
        return null;
    }

    public List<Reponse> getReponsesByIdRec(int id_rec) throws SQLException {
        String sql = "select * from reponse where idreclamation_id = ? ";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, id_rec);
        ResultSet rs = pst.executeQuery();
        List<Reponse> reponses = new ArrayList<>();
        while (rs.next()) {
            Reponse rep = new Reponse();
            rep.setId(rs.getInt("id"));
            rep.setDateReponse(rs.getDate("date_reponse").toLocalDate());
            rep.setContenuResponse(rs.getString("contenu_reponse"));
            rep.setIdReclamation(rs.getInt("idreclamation_id"));

            reponses.add(rep);
        }
        System.out.println(reponses);
        return reponses;
    }
}
