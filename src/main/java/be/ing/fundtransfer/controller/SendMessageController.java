package be.ing.fundtransfer.controller;

import be.ing.fundtransfer.bean.Message;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.service.SendMessageService;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Controller
@Path("/authentication")
@Api(value = " SendMessage resource", produces = "application/json")
public class SendMessageController {

    @Autowired
    SendMessageService sendMessageService;
    Transaction transaction;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sendMessage")
    public  String sendMessage(Message message){
        int otp = sendMessageService.generateOTP();
        String status =sendMessageService.sendOTP(message.getMessage(),message.getMobile(),otp);
        System.out.println("OTP Status in controller"+status);
        return  status;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/generateJson")
    public Message getJson(){
        Message message = new Message();
        return message;
    }


}
