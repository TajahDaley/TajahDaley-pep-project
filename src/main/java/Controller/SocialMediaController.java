package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

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
        app.post("/register", this::postRegisterNewHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postCreateMessageHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterNewHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAcount = accountService.addAccount(account);
        if (account.username != "" && account.password.length() > 4 && addedAcount != null) { 
            ctx.json(mapper.writeValueAsString(addedAcount));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
        
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.login(account);
        if (loginAccount != null) {
            ctx.json(mapper.writeValueAsString(loginAccount));
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    private void postCreateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = messageService.addMessage(message);
        if (message.message_text != "" && message.message_text.length() < 255 && createMessage != null) {
            ctx.json(mapper.writeValueAsString(createMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessageHandler(Context ctx) throws JsonProcessingException{
        List<Message> message = messageService.getAllMessages();
        ctx.json(message);
        ctx.status(200);

    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        //ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message getMessage = messageService.getMessageById(message_id);
        if (getMessage != null) {
            ctx.json(getMessage);
            ctx.status(200);
        } else {
            ctx.status(404);
        }

    }

    private void deleteMessageByIdHandler (Context ctx) throws JsonProcessingException{
        //ObjectMapper mapper = new ObjectMapper();
        int deleteMessage = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(deleteMessage);
        if (deletedMessage != null){
            ctx.json(deletedMessage);
            //ctx.status(200);
        } else {
            ctx.status(200);
        }

    }

    private void patchMessageByIdHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updateMessage = messageService.updateMessageById(id, message);
        if (updateMessage != null) {
            ctx.json(updateMessage);
        }else {
            ctx.status(400);
        }

    }

    private void getMessageByAccountIdHandler (Context ctx) throws JsonProcessingException {
        String accountId = ctx.pathParam("account_id");
        List<Message> messages = messageService.getMessageGivenAccountId(Integer.parseInt(accountId));
        ctx.json(messages);
        
    }


}