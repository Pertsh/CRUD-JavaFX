package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // *** Table view and columns ***
    @FXML
    private TableView<Clients> tableView;
    @FXML
    private TableColumn<Clients, String> idColumn;
    @FXML
    private TableColumn<Clients, String> firstnameColumn;
    @FXML
    private TableColumn<Clients, String> lastnameColumn;
    @FXML
    private TableColumn<Clients, Date> dateOfBirthColumn;
    @FXML
    private TableColumn<Clients, String> emailColumn;



    // *** First window components ***
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextField firstNameTxtField;
    @FXML
    private TextField lastNameTxtField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private DatePicker datePicker ;
    @FXML
    private Button createButton;
    @FXML
    private Button xButton;
    @FXML
    private Button deleteButton;


    // *** local variables ***
    private String id;
    private String firstname;
    private String lastname;
    private String date ;
    private String email;
    private ClientsDao dao = new ClientsDao();

    // *** Getters and setters ***
    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}
    public String getDate() { return date;}
    public String getEmail() { return email; }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setDate(String date) { this.date = date; }
    public void setEmail(String email) {
        this.email = email;
    }


    // *** X button action ***
    @FXML
    public void actionX(){
        System.exit(0);
    }


    // *** CREATE button action ***
    @FXML
    public void create() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("fields cannot be null");
        try {
            firstname = firstNameTxtField.getText();
            lastname = lastNameTxtField.getText();
            date = datePicker.getValue().toString();
            email = emailTxtField.getText();
            dao.insert(new Clients(id, firstname, lastname, date, email));
            playSound("create");

            // *** clean fields after creating user ***
            firstNameTxtField.clear();
            lastNameTxtField.clear();
            datePicker.getEditor().clear();
            datePicker.setValue(null);
            emailTxtField.clear();
            // *** update tableView data ***


        } catch (Exception e) {
            playSound("alert");
            alert.showAndWait();
        }
        updateData();
    }

    public void initialize(URL location, ResourceBundle resources) {


        //Adding the shadow when the mouse cursor is on
        DropShadow shadow = new DropShadow();
        deleteButton.addEventHandler(MouseEvent.MOUSE_ENTERED,e -> deleteButton.setEffect(shadow));
        deleteButton.addEventHandler(MouseEvent.MOUSE_EXITED,e -> deleteButton.setEffect(null));
        createButton.addEventHandler(MouseEvent.MOUSE_ENTERED,e -> createButton.setEffect(shadow));
        createButton.addEventHandler(MouseEvent.MOUSE_EXITED,e -> createButton.setEffect(null));
        firstNameTxtField.addEventHandler(MouseEvent.MOUSE_ENTERED,e->{ firstNameTxtField.setEffect(shadow);
            playSound("fields"); });
        firstNameTxtField.addEventHandler(MouseEvent.MOUSE_EXITED,e->firstNameTxtField.setEffect(null));
        lastNameTxtField.addEventHandler(MouseEvent.MOUSE_ENTERED,e->{ lastNameTxtField.setEffect(shadow);
            playSound("fields"); });
        lastNameTxtField.addEventHandler(MouseEvent.MOUSE_EXITED,e->lastNameTxtField.setEffect(null));
        datePicker.addEventHandler(MouseEvent.MOUSE_ENTERED,e->{ datePicker.setEffect(shadow);
            playSound("fields"); });
        datePicker.addEventHandler(MouseEvent.MOUSE_EXITED,e->datePicker.setEffect(null));
        emailTxtField.addEventHandler(MouseEvent.MOUSE_ENTERED,e->{ emailTxtField.setEffect(shadow);
            playSound("fields"); });
        emailTxtField.addEventHandler(MouseEvent.MOUSE_EXITED,e->emailTxtField.setEffect(null));
        xButton.addEventHandler(MouseEvent.MOUSE_ENTERED,e->xButton.setEffect(shadow));
        xButton.addEventHandler(MouseEvent.MOUSE_EXITED,e->xButton.setEffect(null));

        // *** to  allow edit tableView
        tableView.setEditable(true);
        firstnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
           dao.fillClients();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Toolkit.getDefaultToolkit().beep();

            alert.setHeaderText("no connection to DB");
            alert.showAndWait();
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateofbirth"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setItems(dao.getClientsList());
    }

    public void updateData() throws Exception {
        dao.getClientsList().clear();
        dao.fillClients();
    }

    public void onEditFirstName(TableColumn.CellEditEvent<Clients, String> clientsStringCellEditEvent) throws Exception{
        Clients tmp = tableView.getSelectionModel().getSelectedItem();
        tmp.setFirstname(clientsStringCellEditEvent.getNewValue());
        System.out.println(tmp.getFirstname());
        dao.edit(tmp);
        playSound("create");

    }
    public void onEditLastName(TableColumn.CellEditEvent<Clients, String> clientsStringCellEditEvent) throws Exception{
        Clients tmp = tableView.getSelectionModel().getSelectedItem();
        tmp.setLastname(clientsStringCellEditEvent.getNewValue());
        System.out.println(tmp.getLastname());
        dao.edit(tmp);
        playSound("create");

    }
    public void onEditEmail(TableColumn.CellEditEvent<Clients, String> clientsStringCellEditEvent) throws Exception{
        Clients tmp = tableView.getSelectionModel().getSelectedItem();
        tmp.setEmail(clientsStringCellEditEvent.getNewValue());
        System.out.println(tmp.getEmail());
        dao.edit(tmp);
        playSound("create");

    }

    public void PanelAboutButton(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("CRUD-JavaFX program made by Pertsh Galstyan");
        alert.setContentText("contact info: pertsh.galstyan@gmail.com");
        playSound("eventually");
        alert.showAndWait();

    }

    public void onDeleteRow() throws Exception{
        Clients tmp = tableView.getSelectionModel().getSelectedItem();
        try{
        dao.delete(tmp);
        playSound("delete");
        updateData();
        }catch (Exception e){
            playSound("alert");
        }
    }

    public void playSound(String which){
        switch(which){

            case "fields":
                String fields = ("src/resourses/select.mp3");
                Media sound1 = new Media(new File(fields).toURI().toString());
                MediaPlayer mediaPlayer1 = new MediaPlayer(sound1);
                mediaPlayer1.play();
                break;
            case "alert":
                String alert = ("src/resourses/alert.mp3");
                Media sound2 = new Media(new File(alert).toURI().toString());
                MediaPlayer mediaPlayer2 = new MediaPlayer(sound2);
                mediaPlayer2.play();
                break;
            case "create":
                String create = ("src/resourses/create.mp3");
                Media sound3 = new Media(new File(create).toURI().toString());
                MediaPlayer mediaPlayer3 = new MediaPlayer(sound3);
                mediaPlayer3.play();
                break;
            case "eventually":
                String eventually = ("src/resourses/eventually.mp3");
                Media sound4 = new Media(new File(eventually).toURI().toString());
                MediaPlayer mediaPlayer4 = new MediaPlayer(sound4);
                mediaPlayer4.play();
                break;
            case "delete":
                String delete = ("src/resourses/delete.mp3");
                Media sound5 = new Media(new File(delete).toURI().toString());
                MediaPlayer mediaPlayer5 = new MediaPlayer(sound5);
                mediaPlayer5.play();
                break;
        }
    }
}







