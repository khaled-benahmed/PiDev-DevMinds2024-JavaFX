package com.example.gestionutilisateurs.services;

import com.example.gestionutilisateurs.entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.gestionutilisateurs.interfaces.IService;
import com.example.gestionutilisateurs.tools.MyConnection;

public class UserService implements IService<User> {

    static Connection cnx;
    private static int id;
    private static String lastname;
    private static String username;
    private static String email;
    private static String firstname;
    private static String password;
    private static String image;
    private static int tel;


    public UserService() {
        cnx = MyConnection.getInstance().getCnx();
    }




    @Override
    public void ajouter(User t) throws SQLException {


        String req = "INSERT INTO user (firstname,lastname,username,email,password,tel,image,roles) VALUES(?,?,?,?,?,?,?,?)";
        //String req = "INSERT INTO yyy (lastname,firstname) VALUES(?,?)";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, t.getFirstname());
        stmt.setString(2, t.getLastname());
        stmt.setString(3, t.getUsername());
        stmt.setString(4, t.getEmail());
        stmt.setString(5, t.getPassword());
        stmt.setInt(6, t.getTel());
        stmt.setString(7, t.getImage());
        stmt.setString(8, t.getRole());
        ;
        int result = stmt.executeUpdate();

        System.out.println(result + " enregistrement ajouté.");


    }

    public boolean existemail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            exist = true;
        }
        return exist;
    }

    public boolean existUsername(String username) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE username = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            exist = true;
        }
        return exist;
    }

    @Override
    public void modifier(User t) throws SQLException {
        String req = "UPDATE user SET lastname=?, firstname=?, email=? , tel=?, image=?, roles=?, username=? WHERE id=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, t.getLastname());
        stmt.setString(2, t.getFirstname());
        stmt.setString(3, t.getEmail());
        stmt.setInt(4, t.getTel());
        stmt.setString(5, t.getImage());
        stmt.setString(6, t.getRole());
        stmt.setString(7, t.getUsername());
        stmt.setInt(8, t.getId());

        stmt.executeUpdate();

        System.out.println("Modification effectuée avec succès!");
    }


    @Override
    public void supprimer(User t) throws SQLException {
        String req = "Delete from user where id=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setInt(1, t.getId());
        stmt.executeUpdate();
        System.out.println(" suppression etablie!");


    }



    /*public List<User> rechercherParNom(String nom) throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user WHERE (roles='Coach' OR roles='Nutritionist' OR roles='simple utilisateur') AND (nom LIKE '%" + nom + "%' OR email LIKE '%" + nom + "%' OR prenom LIKE '%" + nom + "%')";
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(req);

        while(rs.next()){
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setTel(rs.getInt("tel"));
            p.setEmail(rs.getString("email"));
            p.setUsername(rs.getString("username"));
            p.setLastname(rs.getString("nom"));
            p.setFirstname(rs.getString("prenom"));
            p.setRole(rs.getString("role"));

            p.setImage(rs.getString("image"));
            p.setPassword(rs.getString("mdp"));

            users.add(p);


        }
        return users;
    }
 */
    @Override
    public List<User> recuperer() throws SQLException  {
        List<User> users = new ArrayList<>();
        String req="select * from user";
        Statement st = cnx.createStatement();
        ResultSet rs =  st.executeQuery(req);
        while(rs.next()){
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setTel(rs.getInt("tel"));
            p.setEmail(rs.getString("email"));
            p.setLastname(rs.getString("nom"));
            p.setFirstname(rs.getString("prenom"));
            p.setUsername(rs.getString("username"));
            p.setRole(rs.getString("role"));
            // p.setImage(rs.getString("image"));


            p.setImage(rs.getString("image"));


            p.setPassword(rs.getString("mdp"));

            users.add(p);


        }

        return users;
    }

    public String getFirstname(int id) throws SQLException {
        String req = "SELECT nom FROM user WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        String firstname = "";
        if (rs.next()) {
            firstname = rs.getString("nom");
        }
        return firstname;
    }




    @Override
    public List<User> getAllData () throws SQLException {
        List<User> data = new ArrayList<>();
        String requete = "Select * From user";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            User p = new User();
            p.setId(rs.getInt(1));
            p.setLastname(rs.getString("nom"));
            p.setFirstname(rs.getString("prenom"));
            p.setEmail(rs.getString("email"));
            p.setPassword(rs.getString("mdp"));
            p.setTel(rs.getInt(6));
            p.setImage(rs.getString("image"));
            p.setRole(rs.getString("role"));
            data.add(p);


        }
        return data;
    }
    public void ModifMDP(String email, String password) throws SQLException{

        String req="Update user set password=? where email=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, password);
        stmt.setString(2, email);
        stmt.executeUpdate();
    }

}





