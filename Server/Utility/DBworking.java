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
    private static final String url = "jdbc:postgresql://localhost:5432/studs";
    private static final String user = "postgres";
    private static final String password = "123456";
    private static Connection connection;
    private static Statement stmt;
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
            stmt = connection.createStatement();
            rs = stmt.executeQuery(("select *  from users d where exists( select * from users where d.login ='" + user + "'and d.password='" + password + "')"));
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {

        }
        return false;
    }

    public Boolean addNewUser(String user, String password) {
        try {
            stmt = connection.createStatement();
            stmt.execute(("insert into users values ('"+user+"','"+password+"')"));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public void uploadAllTickets(){
        TicketCollection ticketCollection = new TicketCollection();
        try{
        TreeMap<Long,Ticket> ticketTreeMap = ticketCollection.getTickets();
            stmt = connection.createStatement();
            stmt.execute("TRUNCATE tickets");
            ticketTreeMap.entrySet().stream().forEach(x-> {
                try {
                    stmt.execute("INSERT into tickets values(" +
                            x.getValue().getMapKey()+
                            ",'"+x.getValue().getName()+
                            "',"+x.getValue().getCoordinates().getX()+
                            ","+x.getValue().getCoordinates().getY()+
                            ","+x.getValue().getPrice()+
                            ",'"+(x.getValue().getComment())+
                            "','"+x.getValue().getType()+
                            "','"+x.getValue().getPerson().getHairColor()+
                            "','"+x.getValue().getPerson().getNationality()+
                            "',"+x.getValue().getPerson().getLocation().getX()+
                            ","+x.getValue().getPerson().getLocation().getY()+
                            ",'"+x.getValue().getPerson().getLocation().getName()+
                            "','"+x.getValue().getCreationDate()+
                            "','"+x.getValue().getUser()+"');");
                } catch (SQLException e) {
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
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from tickets");
            String csvTicket ="";
            while (rs.next()){
               csvTicket+=" ,"+rs.getString(2)+","+rs.getLong(3)+","+rs.getLong(4)+","+rs.getDouble(5)+","+rs.getString(6)+","+rs.getString(7)+","+rs.getString(8)+","+rs.getString(9)+","+rs.getLong(10)+","+rs.getDouble(11)+","+rs.getString(12)+"\n";
                Ticket ticket = Decoder.decodeIntoCollection(csvTicket).firstEntry().getValue();
                long mapkey = (long) rs.getInt(1);
                ticket.setMapKey(mapkey);
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
}
