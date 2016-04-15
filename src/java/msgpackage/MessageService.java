/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msgpackage;

import java.io.StringReader;
import java.util.Date;
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
    @Path("messages")
    public JsonArray getAll() {
        JsonArrayBuilder json = Json.createArrayBuilder();
            for(Message message : newMsgCtrl.getAllMessages()){
                json.add(Json.createObjectBuilder()
                        .add("id", message.getId())
                        .add("title", message.getTitle())
                        .add("contents", message.getContents())
                        .add("author", message.getAuthor())
                        .add("senttime", message.getDateString()));
            }
        return json.build();
    }
    
    @GET
    @Produces("application/json")
    @Path("{id}")
    public Message getById(int id) {
        return newMsgCtrl.getMessageById(id);
    }
    
    @GET
    @Produces("application/json")
    @Path("{startDate}/{endDate}")
    public Message getDateByRange(Date from, Date to) {
        return newMsgCtrl.getMessageByDate(from, to);
    }

    /**
     * PUT method for updating or creating an instance of MessageService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public String putJson(@PathParam("id") int id) {
        return "";
    }
    
    @POST
    @Consumes("application/json")
    public JsonArray postJson(JsonArray json){
        return json;
    }
    
    @DELETE
    @Path("{id}")
    public String deleteJson(@PathParam("id") int id){
        return "200 OK";
    }
}
