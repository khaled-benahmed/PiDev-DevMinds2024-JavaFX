package services;

import models.Offre;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffre implements IService<Offre>{
    private Connection connection;

    public ServiceOffre() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Offre offre) throws SQLException {
        String sql = "insert into offres (description,tags,prix) VALUES (?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, offre.getDesciption());
        pst.setString(2, offre.getTags());
        pst.setDouble(3, offre.getPrix());

        pst.executeUpdate();
        System.out.println("Offre added");
    }

    @Override
    public void update(Offre offre) throws SQLException {
        String sql = "Update  offres set description = ? ,tags = ? ,prix =  ? where id = ? ";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, offre.getDesciption());
        pst.setString(2, offre.getTags());
        pst.setDouble(3, offre.getPrix());
        pst.setInt(4, offre.getId());

        pst.executeUpdate();
        System.out.println("Offre updated");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from offres where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("offre with id = " + id + " deleted");

    }

    @Override
    public List<Offre> getAll() throws SQLException {
        String sql = "select * from offres";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Offre> offres = new ArrayList<>();
        while (rs.next()) {
            Offre o = new Offre();
            o.setId(rs.getInt("id"));
            o.setDesciption(rs.getString("description"));
            o.setTags(rs.getString("tags"));
            o.setPrix(rs.getDouble("prix"));
            offres.add(o);
        }
        return offres;
    }

    @Override
    public Offre getById(int id) throws SQLException {
        String sql = "SELECT * FROM offres WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Offre o = new Offre();
        if (rs.next()) {
            o.setId(rs.getInt("id"));
            o.setDesciption(rs.getString("description"));
            o.setTags(rs.getString("tags"));
            o.setPrix(rs.getDouble("prix"));
            System.out.println("offre with id = " + id + " found");
            return o;

        }
        System.out.println("offre with id = " + id + " not found ! ");
        return null;
    }
}
