package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ClientsDao {
    ObservableList<Clients> oblist = FXCollections.observableArrayList();
    private Statement statement;


    public ObservableList<Clients> getClientsList(){
        return oblist;
    }

    public ObservableList<Clients> fillClients()throws Exception{
        statement = Db.connector().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from clientinfo");
        while(resultSet.next()){
            oblist.add(new Clients(resultSet.getString(1),resultSet.getString(2),
            resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));
        }
        return oblist;

    }

    public void insert(Clients clients) throws Exception{
        String firstname = clients.getFirstname();
        String lastname = clients.getLastname();
        String dateofBirth = clients.getDateofbirth().toString();
        String email = clients.getEmail();
        System.out.println(firstname+"   " +lastname);

        statement = Db.connector().createStatement();
        String query = "insert into clientinfo values(null,'"+firstname+"','"+lastname+"','"+dateofBirth+"','"+email+"')";
        System.out.println(query);
        statement.executeUpdate(query);
        statement.close();


    }

    public void edit(Clients clients)throws Exception{
        String firstname = clients.getFirstname();
        String lastname = clients.getLastname();
        Date dateofBirth = clients.getDateofbirth();
        String email = clients.getEmail();

        String query = "update clientinfo set firstname =  '"+firstname+"',lastname = '"+lastname+"', email ='"+email+"' where id = '"+clients.getId()+"'   ";
        statement.executeUpdate(query);
        System.out.println(query);


    }
    public void delete(Clients clients) throws SQLException {
        String firstname = clients.getFirstname();
        String lastname = clients.getLastname();
        Date dateofBirth = clients.getDateofbirth();
        String email = clients.getEmail();

        String query = "delete from clientinfo WHERE id = '"+clients.getId()+"' ";
        statement.executeUpdate(query);
        System.out.println(query);

    }
}
