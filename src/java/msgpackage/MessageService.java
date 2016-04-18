/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgpackage;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author c0637089
 */
@ApplicationScoped
@Path("messages")
public class MessageService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MessageService
     */
    public MessageService() {
    }

    private MessageController newMsgCtrl = new MessageController();
    /**
     * Retrieves representation of an instance of msgpackage.MessageService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getAll() {
        return newMsgCtrl.getAllMessages().toString();
    }
    
    @GET
    @Produces("application/json")
    @Path("{id}")
    public String getById(@PathParam("id")int id) {
        Message msg = newMsgCtrl.getMessageById(id);
        JsonArrayBuilder json = Json.createArrayBuilder();
                json.add(Json.createObjectBuilder()
                        .add("id", msg.getId())
                        .add("title", msg.getTitle())
                        .add("contents", msg.getContents())
                        .add("author", msg.getAuthor())
                        .add("senttime", msg.getDateString()));
                return json.build().toString();
    }
    
    @GET
    @Produces("application/json")
    @Path("{startDate}/{endDate}")
    public String getDateByRange(@PathParam("from") Date from, @PathParam("to") Date to) {
        return newMsgCtrl.getMessageByDate(from, to).toString();
    }

    /**
     * PUT method for updating or creating an instance of MessageService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public String putJson(@PathParam("id") int id, JsonObject json) {
        Message msg = new Message();
        msg.setTitle(json.getString("title"));
        msg.setAuthor(json.getString("author"));
        msg.setContents(json.getString("contents"));
        msg.setSenttime(new Date());
        msg.setId(id);
        
        newMsgCtrl.edit(msg);
        return "200 OK";
    }
    
    @POST
    @Consumes("application/json")
    public String postJson(JsonObject json){
        Message msg = new Message();
        msg.setTitle(json.getString("title"));
        msg.setAuthor(json.getString("author"));
        msg.setContents(json.getString("contents"));
        
        java.util.Date dt = new java.util.Date();
        msg.setSenttime(dt);
        
        newMsgCtrl.add(msg);
        
        return "200 OK";
    }
    
    @DELETE
    @Path("{id}")
    public String deleteJson(@PathParam("id") int id){
        newMsgCtrl.remove(id);
        return "200 OK";
    }
}
