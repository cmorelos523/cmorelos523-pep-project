package Controller;

import org.h2.engine.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Service.*;
import Model.*;

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
    //MessageService messageService;

    /* 
     * No args constructor -> New account and message service
     */
    public SocialMediaController() {
        this.accountService = new AccountService();
        //this.messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
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

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /*
     * Handler to create a new User
     * Body:
     * Create new object mapper
     * Use object mapper to get JSON data from body and transform it into a user
     * Use the service class to add the user
     * If the value is not null, perform the operation
     * Else, return status code 400 (Client side error)
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
     * Handler to process User log in
     */
    private void postUserLoginHandler(Context context) {
        // TODO: Fill this out        
    }

    /*
     * Handler to create a new message
     */
    private void postNewMessageHandler(Context context) {
        // TODO: Fill this out
    }

    /*
     * Handler to get all messages
     */
    private void getAllMessagesHandler(Context context) {
        // TODO: Fill this out
        context.result("in getAllMessagesHandler...");
    }

    /*
     * Handler to retrieve a message by its ID
     */
    private String getMessageByIdHandler(Context context) {
        // TODO: Fill this out
        return "";
    }

    /*
     * Handler to delete and possibly retrieve a message by its ID
     */
    private String deleteMessageByIdHandler(Context context) {
        // TODO: Fill this out
        return "";
    }

    /*
     * Handler to update a message by its ID
     */
    private String updateMessageByIdHandler(Context context) {
        // TODO: Fill this out
        return "";
    }
    
    /*
     * Handler to get all the messages sent by a user
     */
    private void getAllMessagesFromUserIdHandler(Context context) {
        // TODO: Fill this out
    }
}