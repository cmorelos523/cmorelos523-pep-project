package Controller;

import org.h2.engine.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.*;
import Model.*;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /* 
     * Create controller fields
     */
    AccountService accountService;
    MessageService messageService;

    /* 
     * No args constructor -> New account and message service
     */
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        
        Javalin app = Javalin.create();
        // POST -> Create a new account (localhost:8080/register)
        app.post("/register", this::postNewUserHandler);
        // POST -> Login into account (localhost:8080/login)
        app.post("/login", this::postUserLoginHandler);
        // POST -> Create a new message (localhost:8080/messages)
        app.post("/messages", this::postNewMessageHandler);
        // GET -> Get a list of all messages (localhost:8080/messages)
        app.get("/messages", this::getAllMessagesHandler);
        // GET -> Get message by ID (localhost:8080/messages/{message_id})
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        // DELETE -> Delete a message by its ID (localhost:8080/messages/{message_id})
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        // PATCH -> Update a message by its ID (localhost:8080/messages/{message_id})
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        // GET -> Get all the messages sent by a user (localhost:8080/accounts/{account_id}/messages)
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserIdHandler);

        return app;
    }

    /*
     * Handler to create a new User
     */
    private void postNewUserHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        
        if (addedAccount != null) {
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        } else {
            context.status(400);
        }
    }

    /*
     * Handler to process account log in
     */
    private void postUserLoginHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedInAccount = accountService.logIn(account);
        
        if (loggedInAccount != null) {
            context.json(mapper.writeValueAsString(loggedInAccount));
        } else {
            context.status(401);
        }
    }

    /*
     * Handler to create a new message
     */
    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            context.json(mapper.writeValueAsString(addedMessage));
        } else {
            context.status(400);
        }
    }

    /*
     * Handler to get all messages
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /*
     * Handler to retrieve a message by its ID
     */
    private void getMessageByIdHandler(Context context) {
        try {
            context.json(messageService.getMessageByID(context.pathParam("message_id")));
        } catch (NullPointerException e) {
            context.status(200);
        }
        
    }

    /*
     * Handler to delete and retrieve a message by its ID
     */
    private void deleteMessageByIdHandler(Context context) {
        try {
            context.json(messageService.getMessageByID(context.pathParam("message_id")));
            int deletedRows = messageService.deleteMessageByID(context.pathParam("message_id"));
        } catch (NullPointerException e) {
            context.status(200);
        }
    }

    /*
     * Handler to update a message by its ID
     */
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        message.setMessage_id(message_id);
        Message updatedMessage = messageService.updateMessageByID(message);
        
        if (updatedMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(updatedMessage));
        }

    }
    
    /*
     * Handler to get all the messages sent by a user
     */
    private void getAllMessagesFromUserIdHandler(Context context) {
        try {
            List<Message> messagesFromAccountID = messageService.getMessagesFromAccountID(context.pathParam("account_id"));
            context.json(messagesFromAccountID);
        } catch (NullPointerException e) {
            context.status(200);
        }
    }
}