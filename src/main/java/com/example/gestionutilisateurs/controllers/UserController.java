package com.example.gestionutilisateurs.controllers;


import com.example.gestionutilisateurs.App;
import com.example.gestionutilisateurs.entities.User;
import com.example.gestionutilisateurs.tools.MyConnection;
import javafx.application.HostServices;
import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class UserController implements Initializable {
    Connection con =null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tFName;

    @FXML
    private TextField tLastName;

    @FXML
    private TextField tRole;
    @FXML
    private TextField tTel;

    @FXML
    private TextField tUsername;

    @FXML
    private TextField tImage;
    @FXML
    private ImageView pdp;
    //
    @FXML
    private TextField tBlockDuration;




    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, String> colfName;

    @FXML
    private TableColumn<User, Integer> colid;

    @FXML
    private TableColumn<User, String> collName;

    @FXML
    private TableColumn<User, String> colEmail;


    @FXML
    private TableColumn<User, Integer> colTel;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colImage;

    //
    @FXML
    private Button btnUnblock;

    @FXML
    private Button btnBlock;
    @FXML
    private TableColumn<User, Boolean> colBlocked;
    @FXML
    private TableColumn<User, String> colBlockReason;

    @FXML
    private ImageView goBackBtn;
    @FXML
    private TableColumn<User, Date> colBlockedUntil;
    @FXML
    private Button btnExportCSV;

    @FXML
    private TableView<User> table;
    int id =0;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> filterChoice;
    @FXML
    private Button searchButton;
    @FXML
    private Button sortButton;


    private List<User> filteredUsers;
    private Comparator<User> userComparator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
        initializeFilterChoice();
        initializeUserComparator();

    }
    //observableList
    public ObservableList<User> getUsers(){
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "select * from user";
        con = MyConnection.getInstance().getCnx();
        try {
            st= con.prepareStatement(query);
            rs= st.executeQuery();
            while (rs.next()){
                User st =new User();
                st.setId(rs.getInt("id"));
                st.setFirstname(rs.getString("FirstName"));
                st.setLastname(rs.getString("Lastname"));
                st.setRole(rs.getString("Roles"));
                st.setUsername(rs.getString("Username"));
                st.setEmail(rs.getString("Email"));
                st.setTel(rs.getInt("Tel"));
                st.setImage(rs.getString("Image"));
                st.setIs_blocked(rs.getBoolean("is_blocked"));
                st.setIs_blocked_until(rs.getObject("is_blocked_until", Date.class));
                st.setBlock_reason(rs.getString("block_reason"));

                users.add(st);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public void showUsers() {
        ObservableList<User> list = getUsers();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colfName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        collName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colBlocked.setCellValueFactory(new PropertyValueFactory<>("is_blocked"));
        colBlockedUntil.setCellValueFactory(new PropertyValueFactory<>("is_blocked_until"));
        colBlockReason.setCellValueFactory(new PropertyValueFactory<>("block_reason"));

        // Appliquer le style de ligne
        table.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldUser, newUser) -> {
                if (newUser != null) {
                    setRowStyle(row, newUser);
                }
            });
            return row;
        });
    }


    @FXML
    void clearField(ActionEvent event) {
        clear();

    }

    @FXML
    void creatUser(ActionEvent event) {
        String insert = "INSERT INTO user (FirstName,LastName,Roles,Username,Tel,Image) VALUES(?,?,?,?,?,?)";
        con=MyConnection.getInstance().getCnx();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tFName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tRole.getText());
            st.setString(4, tUsername.getText());
            st.setInt(5, Integer.parseInt(tTel.getText()));
            st.setString(6, tImage.getText());

            st.executeUpdate();
            clear();
            showUsers();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    @FXML
    void getData(MouseEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        id= user.getId();
        tFName.setText(user.getFirstname());
        tLastName.setText(user.getLastname());
        tRole.setText(user.getRole());
        tUsername.setText(user.getUsername());
        tTel.setText(String.valueOf(user.getTel()));
        tImage.setText(user.getImage());
        //
        File imageFile = new File(user.getImage());
        Image image = new Image(imageFile.toURI().toString());
        pdp.setImage(image);
        btnSave.setDisable(true);

    }
    void clear (){
        tLastName.setText(null);
        tFName.setText(null);
        tRole.setText(null);
        tImage.setText(null);
        tTel.setText(null);
        tUsername.setText(null);
        btnSave.setDisable(false);
    }

    @FXML
    void deleteUser(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        // Vérifier si un utilisateur est sélectionné
        if (user != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Êtes-vous sûr de supprimer cet utilisateur : " + user.getUsername() + " ?");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'utilisateur de la base de données
                String deleteQuery = "DELETE FROM user WHERE id = ?";
                con = MyConnection.getInstance().getCnx();
                try {
                    st = con.prepareStatement(deleteQuery);
                    st.setInt(1, user.getId());
                    st.executeUpdate();

                    // Actualiser la liste des utilisateurs affichée dans la table
                    showUsers();
                    // Effacer les champs de saisie après la suppression
                    clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            // Afficher un message d'erreur si aucun utilisateur n'est sélectionné
            System.out.println("Veuillez sélectionner un utilisateur à supprimer.");
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        String update = "UPDATE user SET FirstName=?, LastName=?, Roles=?, Username=?, Tel=?, Image=? WHERE id = ?";
        con = MyConnection.getInstance().getCnx();
        try {
            // Validation du numéro de téléphone
            String tel = tTel.getText();
            if (!tel.matches("\\d{8}")) { // Vérifie si le numéro de téléphone contient exactement 8 chiffres
                // Affiche un message d'erreur si le numéro de téléphone est invalide
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le numéro de téléphone doit contenir exactement 8 chiffres.");
                alert.showAndWait();
                return; // Arrête l'exécution de la méthode
            }

            // Validation du rôle
            String role = tRole.getText();
            if (!role.equals(" simple utilisateur") && !role.equals("Coach") && !role.equals("Nutritionniste")) {
                // Affiche un message d'erreur si le rôle est invalide
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le rôle doit être 'simple utilisateur', 'Coach' ou 'Nutritionniste'.");
                alert.showAndWait();
                return; // Arrête l'exécution de la méthode
            }

            // Préparation de la requête
            st = con.prepareStatement(update);
            st.setString(1, tFName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, role);
            st.setString(4, tUsername.getText());
            st.setString(5, tel);
            st.setString(6, tImage.getText());
            st.setInt(7, id);

            // Exécution de la requête
            st.executeUpdate();

            // Mise à jour de l'affichage et réinitialisation des champs
            showUsers();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void blockUser(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            // Afficher une boîte de dialogue pour choisir la durée de blocage et la raison
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Bloquer l'utilisateur");
            dialog.setHeaderText(null);
            dialog.setContentText("Entrez la durée de blocage (en heures) :");

            Optional<String> durationResult = dialog.showAndWait();
            if (durationResult.isPresent()) {
                try {
                    // Récupérer la durée de blocage saisie par l'administrateur
                    int durationInHours = Integer.parseInt(durationResult.get());

                    // Afficher une nouvelle boîte de dialogue pour saisir la raison du blocage
                    TextInputDialog reasonDialog = new TextInputDialog();
                    reasonDialog.setTitle("Bloquer l'utilisateur");
                    reasonDialog.setHeaderText(null);
                    reasonDialog.setContentText("Entrez la raison du blocage :");

                    Optional<String> reasonResult = reasonDialog.showAndWait();
                    if (reasonResult.isPresent()) {
                        String reason = reasonResult.get();

                        // Calculer la date de fin de blocage
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.HOUR_OF_DAY, durationInHours);
                        Date blockedUntil = calendar.getTime();

                        // Mettre à jour l'utilisateur dans la base de données
                        String updateQuery = "UPDATE user SET is_blocked = true, is_blocked_until = ?, block_reason = ? WHERE id = ?";
                        con = MyConnection.getInstance().getCnx();
                        st = con.prepareStatement(updateQuery);
                        st.setTimestamp(1, new java.sql.Timestamp(blockedUntil.getTime()));
                        st.setString(2, reason);
                        st.setInt(3, user.getId());
                        st.executeUpdate();

                        // Mettre à jour l'objet User
                        user.setIs_blocked(true);
                        user.setIs_blocked_until(blockedUntil);
                        user.setBlock_reason(reason);

                        // Actualiser l'affichage de la table
                        showUsers();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir une durée de blocage valide (en heures).");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun utilisateur sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur à bloquer.");
            alert.showAndWait();
        }
    }


    @FXML
    void unblockUser(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null && user.isIs_blocked()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de déblocage");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Êtes-vous sûr de vouloir débloquer l'utilisateur " + user.getUsername() + " ?");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    String updateQuery = "UPDATE user SET is_blocked = false, is_blocked_until = NULL, block_reason = NULL WHERE id = ?";
                    con = MyConnection.getInstance().getCnx();
                    st = con.prepareStatement(updateQuery);
                    st.setInt(1, user.getId());
                    st.executeUpdate();

                    user.setIs_blocked(false);
                    user.setIs_blocked_until(null);
                    user.setBlock_reason(null);
                    showUsers();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun utilisateur sélectionné ou non bloqué");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur bloqué à débloquer.");
            alert.showAndWait();
        }
    }




    @FXML
    private void goBackHandler(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/MainCotainer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setRowStyle(TableRow<User> row, User user) {
        if (user.isIs_blocked()) {
            row.setStyle("-fx-background-color: #ffcdd2;"); // Rouge clair pour les utilisateurs bloqués
        } else {
            row.setStyle("-fx-background-color: grey;"); // Blanc pour les utilisateurs non bloqués
        }
    }


    /////

    private void initializeFilterChoice() {
        filterChoice.getItems().addAll("Tous", "Bloqués", "Non bloqués");
        filterChoice.getSelectionModel().selectFirst();
        filterChoice.setOnAction(event -> handleSearch());
    }

    private void initializeUserComparator() {
        userComparator = Comparator.comparing(User::getFirstname)
                .thenComparing(User::getLastname)
                .thenComparing(User::getUsername);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        String filterValue = filterChoice.getSelectionModel().getSelectedItem();

        filteredUsers = getUsers().stream()
                .filter(user -> {
                    boolean matchesSearch = user.getFirstname().toLowerCase().contains(searchText)
                            || user.getLastname().toLowerCase().contains(searchText)
                            || user.getUsername().toLowerCase().contains(searchText)
                            || user.getEmail().toLowerCase().contains(searchText);
                    boolean matchesFilter = filterValue.equals("Tous") || (filterValue.equals("Bloqués") && user.isIs_blocked())
                            || (filterValue.equals("Non bloqués") && !user.isIs_blocked());
                    return matchesSearch && matchesFilter;
                })
                .collect(Collectors.toList());

        updateTableView();
    }

    @FXML
    private void handleSort() {
        filteredUsers.sort(userComparator);
        updateTableView();
    }

    private void updateTableView() {
        table.setItems(FXCollections.observableArrayList(filteredUsers));
    }
    @FXML
    private void exportToCSV(ActionEvent event) {
        try {
            // Récupérer les données des utilisateurs
            ObservableList<User> users = getUsers();
            System.out.println("Nombre d'utilisateurs : " + users.size());

            // Construire le contenu du fichier CSV
            StringBuilder csvContent = new StringBuilder();
            csvContent.append("ID,Prénom,Nom,Rôle,Nom d'utilisateur,E-mail,Téléphone,Image,Bloqué,Bloqué jusqu'au,Raison du blocage\n");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (User user : users) {
                csvContent.append(String.format("%d,%s,%s,%s,%s,%s,%d,%s,%b,%s,%s\n",
                        user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getRole(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getTel(),
                        user.getImage(),
                        user.isIs_blocked(),
                        user.getIs_blocked_until() != null ? dateFormat.format(user.getIs_blocked_until()) : "",
                        user.getBlock_reason() != null ? user.getBlock_reason() : ""));
            }

            // Récupérer le nom de fichier
            String fileName = "utilisateurs.csv";

            // Créer le dossier "liste_des_utilisateurs" sur le bureau
            File userDirectory = new File(System.getProperty("user.home"), "Desktop/liste_des_utilisateurs");
            if (!userDirectory.exists()) {
                userDirectory.mkdirs();
            }

            // Créer le fichier CSV dans le dossier "liste_des_utilisateurs"
            File csvFile = new File(userDirectory, fileName);
            System.out.println("Fichier CSV créé : " + csvFile.getAbsolutePath());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write(csvContent.toString());
                System.out.println("Contenu du fichier CSV écrit avec succès.");
            }

            // Ouvrir le fichier dans le navigateur
            System.out.println("Ouverture du fichier dans le navigateur...");
            getHostServices().showDocument(csvFile.toURI().toString());
            System.out.println("Fichier ouvert dans le navigateur.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportation CSV");
            alert.setHeaderText(null);
            alert.setContentText("Les données des utilisateurs ont été exportées avec succès.");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'exportation");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'exportation des données : " + e.getMessage());
            alert.showAndWait();
        }
    }

    private HostServices getHostServices() {
        try {
            return App.class.getConstructor().newInstance().getHostServices();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}

