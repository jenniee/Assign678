/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgpackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author c0637089
 */
@ApplicationScoped
public class MessageController {
    
    private List<Message> messages = new ArrayList<>();
    public Message instance = new Message();
    
    public List<Message> getAllMessages(){
        DateFormat df = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
        Date msgDate = instance.getSenttime();
        String stringDate = df.format(msgDate);
        return messages;
    }
    
    public Message getMessageById(int id){
        for(Message selectedMessage : messages){
            if(selectedMessage.getId() == id){
                return selectedMessage;
            }
        }
        return null;
    }
    
   public List<Message> getMessageByDate(Date from, Date to){
        for(Message msg : messages){
            if(msg.getSenttime().after(from) && msg.getSenttime().before(to)){
                return messages;
            }
        }
        return null;
    }
   
   public void add(Message msg){
        messages.add(msg);
    }
   
   public void edit(Message msg){
       
   }
    
    public void remove(int id){
        
    }

    
    
    
}
