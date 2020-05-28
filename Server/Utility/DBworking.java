package Utility;

import Common.Decoder;
import Common.Ticket;
import Common.TicketCollection;
import org.postgresql.core.SqlCommand;

import java.sql.*;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DBworking {
//    private static final String url = "jdbc:postgresql://pg:5432/studs";
//    private static final String user = "s285706";
//    private static final String password = "boi902";
    private static final String url = "jdbc:postgresql://localhost:5432/studs";
    private static final String user = "postgres";
    private static final String password = "123456";
    private static Connection connection;
    private static Statement stmt;
    private static  PreparedStatement preparedStatement;
    private static ResultSet rs;

    public  Boolean ConnectionToDB() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Boolean userExist(String user, String password) {

        try {
           preparedStatement = connection.prepareStatement("select *  from users d where exists( select * from users where d.login = ? and d.password= ?)");
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {

        }
        return false;
    }
    public Boolean ticketExist(Long id){
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select *  from tickets d where exists( select * from users where d.id ="+id+")");
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {

        }
        return false;
    }

    public Boolean addNewUser(String user, String password) {
        try {
            preparedStatement = connection.prepareStatement("insert into users values (?,?)");
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,password);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {

            return false;
        }
    }
    public void uploadAllTickets(){
        TicketCollection ticketCollection = new TicketCollection();
        try{
            stmt = connection.createStatement();
            stmt.execute("TRUNCATE tickets");
        TreeMap<Long,Ticket> ticketTreeMap = ticketCollection.getTickets();
            ticketTreeMap.entrySet().stream().forEach(x-> {
                try {
                    preparedStatement = connection.prepareStatement("INSERT into tickets values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setLong(1, Math.toIntExact(x.getValue().getMapKey()));
                    preparedStatement.setString(2, x.getValue().getName());
                    preparedStatement.setLong(3, x.getValue().getCoordinates().getX());
                    preparedStatement.setLong(4, x.getValue().getCoordinates().getY());
                    preparedStatement.setDouble(5, x.getValue().getPrice());
                    preparedStatement.setString(6, x.getValue().getComment());
                    preparedStatement.setString(7, x.getValue().getType().toString());
                    preparedStatement.setString(8, x.getValue().getPerson().getHairColor().toString());
                    preparedStatement.setString(9, x.getValue().getPerson().getNationality().toString());
                    preparedStatement.setLong(10, x.getValue().getPerson().getLocation().getX());
                    preparedStatement.setDouble(11, x.getValue().getPerson().getLocation().getY());
                    preparedStatement.setString(12, x.getValue().getPerson().getLocation().getName());
                    preparedStatement.setTimestamp(13, x.getValue().getCreationDate());
                    preparedStatement.setString(14, x.getValue().getUser());
                    preparedStatement.setLong(15, Long.parseLong(x.getValue().getPerson().getPassportID()));
                    preparedStatement.execute();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void loadAllTickets(){

        try {
            TicketCollection.getLock().writeLock().lock();
            TicketCollection ticketCollection = new TicketCollection();
            ticketCollection.getTickets().clear();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from tickets");
            while (rs.next()){
                String[]ticketarray = new String[11];
                ticketarray[0]=rs.getString(2);
                ticketarray[1]= String.valueOf(rs.getLong(3));
                ticketarray[2]= String.valueOf(rs.getLong(4));
                ticketarray[3]= String.valueOf(rs.getDouble(5));
                ticketarray[4]=rs.getString(6);
                ticketarray[5]=rs.getString(7);
                ticketarray[6]=rs.getString(8);
                ticketarray[7]=rs.getString(9);
                ticketarray[8]= String.valueOf(rs.getLong(10));
                ticketarray[9]= String.valueOf(rs.getDouble(11));
                ticketarray[10]=rs.getString(12);
               Ticket ticket = Decoder.decodeIntoCollection(ticketarray).firstEntry().getValue();
                long mapkey = (long) rs.getInt(1);
                ticket.setMapKey(mapkey);
                ticket.getPerson().setPassportID(rs.getLong(15));
                ticket.setCreationDate(rs.getTimestamp(13));
                ticket.setUser(rs.getString(14));
                ticketCollection.putTicket(mapkey,ticket);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            TicketCollection.getLock().writeLock().unlock();
        }

    }
    public Long getNewPassportID(){
        try {
            stmt = connection.createStatement();
            //rs =stmt.executeQuery("SELECT nextval(\'tickets_passportid_seq\')");
            rs =stmt.executeQuery("SELECT nextval('passport')");
            if (rs.next()) {
                return rs.getLong(1);
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
