package services;

import models.Sponsor;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSponsor implements IService<Sponsor>{

    private Connection connection;

    public ServiceSponsor() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void add(Sponsor sponsor) throws SQLException {
        String sql = "insert into sponsor (nom_sponsor,donnation,image) VALUES (?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, sponsor.getNomSponsor());
        pst.setDouble(2, sponsor.getDonnation());
        pst.setString(3, sponsor.getImage());

        pst.executeUpdate();
        System.out.println("sponsor added");

    }

    @Override
    public void update(Sponsor sponsor) throws SQLException {
        String sql = "update  sponsor set  nom_sponsor = ? ,donnation = ? ,image = ? where id = ? ";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, sponsor.getNomSponsor());
        pst.setDouble(2, sponsor.getDonnation());
        pst.setString(3, sponsor.getImage());
        pst.setInt(4, sponsor.getId());

        pst.executeUpdate();
        System.out.println("sponsor updated");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from sponsor where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Sponsor with id = " + id + " deleted");

    }

    @Override
    public List<Sponsor> getAll() throws SQLException {
        String sql = "select * from sponsor";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Sponsor> sponsors = new ArrayList<>();
        while (rs.next()) {
            Sponsor s = new Sponsor();
            s.setId(rs.getInt("id"));
            s.setNomSponsor(rs.getString("nom_sponsor"));
            s.setDonnation(rs.getDouble("donnation"));
            s.setImage(rs.getString("image"));
            sponsors.add(s);
        }
        return sponsors;
    }

    @Override
    public Sponsor getById(int id) throws SQLException {
        String sql = "SELECT * FROM sponsor WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Sponsor s = new Sponsor();
        if (rs.next()) {
            s.setId(rs.getInt("id"));
            s.setNomSponsor(rs.getString("nom_sponsor"));
            s.setDonnation(rs.getDouble("donnation"));
            s.setImage(rs.getString("image"));
            System.out.println("sponsor with id = " + id + " found");
            return s;

        }
        System.out.println("sponsor with id = " + id + " not found ! ");
        return null;
    }
}
