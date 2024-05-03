package services;

import models.Reclamation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IService<Reclamation> {

    private Connection connection;

    public ServiceReclamation() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Reclamation reclamation) throws SQLException {
        String sql = "insert into reclamation (nom_user_reclamation,text_reclamation,etat ,date_reclamation,id_user) VALUES (?,?,?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, reclamation.getNomUser());
        pst.setString(2, reclamation.getTextReclamation());
        pst.setString(3, "En attente");
        pst.setDate(4, Date.valueOf(reclamation.getDateReclamation()));
        pst.setInt(5, reclamation.getIdUser());
        pst.executeUpdate();
        System.out.println("Reclamation added");
    }

    @Override
    public void update(Reclamation reclamation) throws SQLException {

        String sql = "update reclamation set nom_user_reclamation = ?,  text_reclamation = ? , etat = ? , date_reclamation = ? , id_user = ?  where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, reclamation.getNomUser());
        preparedStatement.setString(2, reclamation.getTextReclamation());
        preparedStatement.setString(3, reclamation.getEtat());
        preparedStatement.setDate(4, Date.valueOf(reclamation.getDateReclamation()));
        preparedStatement.setInt(5, reclamation.getIdUser());
        preparedStatement.setInt(6, reclamation.getId());
        preparedStatement.executeUpdate();
        System.out.println("Reclamation with id = " + reclamation.getId() + " updated");

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from reclamation where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Reclamation with id = " + id + " deleted");
    }

    @Override
    public List<Reclamation> getAll() throws SQLException {
        String sql = "select * from reclamation";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Reclamation> reclamations = new ArrayList<>();
        while (rs.next()) {
            Reclamation rec = new Reclamation();
            rec.setId(rs.getInt("id"));
            rec.setDateReclamation(rs.getDate("date_reclamation").toLocalDate());
            rec.setEtat(rs.getString("etat"));
            rec.setNomUser(rs.getString("nom_user_reclamation"));
            rec.setIdUser(rs.getInt("id_user"));
            rec.setTextReclamation(rs.getString("text_reclamation"));

            reclamations.add(rec);
        }
        return reclamations;
    }

    @Override
    public Reclamation getById(int id) throws SQLException {
        String sql = "SELECT * FROM reclamation WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Reclamation rec = new Reclamation();
        if (rs.next()) {
            rec.setId(rs.getInt("id"));
            rec.setDateReclamation(rs.getDate("date_reclamation").toLocalDate());
            rec.setNomUser(rs.getString("nom_user_reclamation"));
            rec.setEtat(rs.getString("etat"));
            rec.setIdUser(rs.getInt("id_user"));
            rec.setTextReclamation(rs.getString("text_reclamation"));
            System.out.println("Reclamation with id = " + id + " found");
            return rec;

        }
        System.out.println("Reclamation with id = " + id + " not found ! ");
        return null;
    }

    public List<Reclamation> getReclamationsByIdUser(int id_user) throws SQLException {
        String sql = "select * from reclamation where id_user = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_user);
        ResultSet rs = statement.executeQuery();
        List<Reclamation> reclamations = new ArrayList<>();
        while (rs.next()) {
            Reclamation rec = new Reclamation();
            rec.setId(rs.getInt("id"));
            rec.setDateReclamation(rs.getDate("date_reclamation").toLocalDate());
            rec.setNomUser(rs.getString("nom_user_reclamation"));
            rec.setEtat(rs.getString("etat"));
            rec.setIdUser(rs.getInt("id_user"));
            rec.setTextReclamation(rs.getString("text_reclamation"));

            reclamations.add(rec);
        }
        return reclamations;
    }
}
