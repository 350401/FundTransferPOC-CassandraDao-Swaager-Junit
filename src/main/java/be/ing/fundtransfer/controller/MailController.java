package be.ing.fundtransfer.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.service.MailService;
import be.ing.fundtransfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Controller
@Path("/app/send")
@Api(value = "Mail resource", produces = "application/json")
public  class MailController {
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mail")
    public String triggerEmail(TransactionData transactionData) throws Exception{
        UserData userData= userService.getByUserName(transactionData.getToAccount());
        System.out.println("Mail User:::::"+userData);
        String mailStatus = mailService.triggerEmail(userData,transactionData);
        return mailStatus;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/transactionJson")
    public Transaction getJson(){
        Transaction transaction= new Transaction();
        return transaction;
    }

}
