package services;

import models.Offre;
import models.Promotion;
import models.Sponsor;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePromotion implements IService<Promotion> {

    private Connection connection;

    public ServicePromotion() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Promotion promotion) throws SQLException {
        String sql = "insert into promotion (sponsor_id,code_promotion,reduction_promotion,date_expiration) VALUES (?,?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, promotion.getSponsorId());
        pst.setString(2, promotion.getCodePromotion());
        pst.setDouble(3, promotion.getReductionPromotion());
        pst.setDate(4, Date.valueOf(promotion.getDateExpiration()));
        pst.executeUpdate();
        System.out.println("promotion added");

    }

    @Override
    public void update(Promotion promotion) throws SQLException {
        String sql = "update  promotion set  sponsor_id = ? ,code_promotion = ? ,reduction_promotion = ? ,date_expiration = ?  where id = ? ";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, promotion.getSponsorId());
        pst.setString(2, promotion.getCodePromotion());
        pst.setDouble(3, promotion.getReductionPromotion());
        pst.setDate(4, Date.valueOf(promotion.getDateExpiration()));
        pst.setInt(5, promotion.getId());
        pst.executeUpdate();
        System.out.println("promotion updated");

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from promotion where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("promotion with id = " + id + " deleted");

    }

    @Override
    public List<Promotion> getAll() throws SQLException {
        String sql = "select * from promotion";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Promotion> promotions = new ArrayList<>();
        while (rs.next()) {
            Promotion p = new Promotion();
            p.setId(rs.getInt("id"));
            p.setCodePromotion(rs.getString("code_promotion"));
            p.setReductionPromotion(rs.getDouble("reduction_promotion"));
            p.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
            p.setSponsorId(rs.getInt("sponsor_id"));
            promotions.add(p);
        }
        return promotions;
    }

    @Override
    public Promotion getById(int id) throws SQLException {
        String sql = "SELECT * FROM promotion WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Promotion p = new Promotion();
        if (rs.next()) {
            p.setId(rs.getInt("id"));
            p.setCodePromotion(rs.getString("code_promotion"));
            p.setReductionPromotion(rs.getDouble("reduction_promotion"));
            p.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
            p.setSponsorId(rs.getInt("sponsor_id"));
            System.out.println("promo with id = " + id + " found");
            return p;

        }
        System.out.println("promo with id = " + id + " not found ! ");
        return null;
    }

    public List<Promotion> getByIdSponsor(int id) throws SQLException {
        String sql = "SELECT * FROM promotion WHERE `sponsor_id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        List<Promotion> promotions = new ArrayList<>();
        while (rs.next()) {
            Promotion p = new Promotion();
            p.setId(rs.getInt("id"));
            p.setCodePromotion(rs.getString("code_promotion"));
            p.setReductionPromotion(rs.getDouble("reduction_promotion"));
            p.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
            p.setSponsorId(rs.getInt("sponsor_id"));
            promotions.add(p);
        }
        return promotions;
    }
}
