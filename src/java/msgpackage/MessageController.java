/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgpackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.json.JsonArray;
import static msgpackage.Credentials.getConnection;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import org.jboss.logging.Logger;

/**
 *
 * @author c0637089
 */
@ApplicationScoped
public class MessageController {
     java.text.SimpleDateFormat sdf = 
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private List<Message> messages = new ArrayList<>();
    public Message message = new Message();
    
    public JsonArray getAllMessages(){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM messages");
            
                
            while (rs.next()) {
                jab.add(Json.createObjectBuilder()
                        .add("id", message.getId())
                        .add("title", message.getTitle())
                        .add("contents", message.getContents())
                        .add("author", message.getAuthor())
                        .add("senttime", message.getDateString()))
                        .build();
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("getAllMessages error: " + ex.getMessage());
        }
        return jab.build();
    }
    
    public Message getMessageById(int id){
        Message msg = new Message();
        
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM messages WHERE id = " + id);
            
            rs.next();
            msg.setId(id);
            msg.setTitle(rs.getString("title"));
            msg.setAuthor(rs.getString("author"));
            msg.setContents(rs.getString("author"));
            
            try {
                msg.setSenttime(sdf.parse(rs.getString("senttime")));
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.out.println("getAllMessages error: " + ex.getMessage());
        }
        return msg;
    }
    
   public JsonArray getMessageByDate(Date from, Date to){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            
            String fromTime = sdf.format(from);
            String toTime = sdf.format(to);
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM messages WHERE senttime < " + toTime + " AND senttime > " + fromTime);
            
                
            while (rs.next()) {
                jab.add(Json.createObjectBuilder()
                        .add("id", message.getId())
                        .add("title", message.getTitle())
                        .add("contents", message.getContents())
                        .add("author", message.getAuthor())
                        .add("senttime", message.getDateString()))
                        .build();
            }
            
            conn.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jab.build();
    }
   
   public void add(Message msg){
        String currentTime = sdf.format(msg.getSenttime());
        
        try(Connection conn = getConnection()){
          PreparedStatement pstmt = conn.prepareStatement("INSERT INTO messages (title, contents, author, senttime) VALUES(" 
                  + "'" + msg.getTitle() + "',"
                  + "'" + msg.getContents() + "'," 
                  + "'" + msg.getAuthor() + "',"
                  + "'" + currentTime + "'");
          
          pstmt.executeUpdate();
          conn.close();
       }
        catch (SQLException ex){
           java.util.logging.Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
   
   public void edit(Message msg){
       try(Connection conn = getConnection()){
           PreparedStatement pstmt = conn.prepareStatement("UPDATE messages SET title=?, content=?, author=?, senttime=? WHERE id=?");
           pstmt.setString(1, msg.getTitle());
           pstmt.setString(2, msg.getContents());
           pstmt.setString(3, msg.getAuthor());
           
           String currentTime = sdf.format(msg.getSenttime());
           pstmt.setString(4, currentTime);
           
           pstmt.setInt(5, msg.getId());
           
           pstmt.executeUpdate();
           conn.close();
       }
       catch(SQLException ex){
           java.util.logging.Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
    
    public void remove(int id){
        try(Connection conn = getConnection()){
           PreparedStatement pstmt = conn.prepareStatement("DELETE from messages WHERE id=?");
           pstmt.setInt(1, id);
           
           pstmt.executeUpdate();
           conn.close();
           
           
       }
       catch(SQLException ex){
           java.util.logging.Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    
    
    
}
