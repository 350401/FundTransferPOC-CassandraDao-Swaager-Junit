package be.ing.fundtransfer.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.datastax.driver.core.utils.UUIDs;

import be.ing.fundtransfer.bean.EmailMessage;
import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.service.MailService;
import be.ing.fundtransfer.service.SendMessageService;
import be.ing.fundtransfer.service.TransactionService;
import be.ing.fundtransfer.service.UserService;
import be.ing.fundtransfer.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Path("/transaction")
@Api(value = "Transaction resource", produces = "application/json")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    MailService mailService;
    @Autowired
    SendMessageService sendMessageService;
    @Autowired
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
	  @ApiOperation(value = "Creates Transaction resource. Accepts : TransactionData as input ", response = TransactionData.class)
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "TransactionData resource created"),
	    @ApiResponse(code = 400, message = "Failed to create Transaction resource ")
	  })    
    public Response createTransaction(TransactionData transactionData) throws DataInsertionException {
        UserData userDataTo =null;
        UserData userDataFrom = null;
        transactionData.setId(UUIDs.timeBased().toString());
        try {
            userDataTo= userService.getByUserName(transactionData.getToAccount());//status 1
            System.out.println(userDataTo);
            userDataFrom = userService.getByUserName(transactionData.getFromAccount());//status 2
            System.out.println(userDataFrom);
            transactionData.setStatus(1);

            if(userDataTo!=null && userDataFrom!=null){
                String message="OTP Password Generated ";
                int toOtp = sendMessageService.generateOTP();
                int fromOtp = sendMessageService.generateOTP();
                String toOtpStatus = sendMessageService.sendOTP(message,userDataTo.getMobileNumber(),toOtp);
                String fromOtpStatus = sendMessageService.sendOTP(message,userDataFrom.getMobileNumber(),fromOtp);
                if(toOtpStatus.equals(Constants.SUCCESS) && fromOtpStatus.equals(Constants.SUCCESS)){
                    transactionData.setStatus(3);//status 3;
                    transactionData.setToOtp(toOtp);
                    transactionData.setFromOtp(fromOtp);
                    EmailMessage emailMesage= new EmailMessage();
                    emailMesage.setEmail(userDataTo.getEmail());
                    emailMesage.setUserName(userDataFrom.getUsername());
                    emailMesage.setTransactionId(transactionData.getId());
                    String status = mailService.sendEmail(emailMesage);
                    if(status.contains("OK")){
                        transactionData.setStatus(4);//status 4;
                    }
                    //ibanNum need to update along with to otp and from otp screen //validate screen need to implement
                    //update status 5;
                }
            }
           }catch(Exception ex){
        }

        System.out.println(":::::::::::::::::::::::::::::::::::::");
        System.out.println(transactionData);
        System.out.println(":::::::::::::::::::::::::::::::::::::");
        String status = transactionService.createTransaction(transactionData);
        System.out.println("::::::::::::status "+status);
        if(StringUtils.equals(status, Constants.SUCCESS)){
            return Response.status(200).entity(transactionData).build();
        }else{
            return Response.status(400).build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/retrieve")
    @ApiOperation(value = "Gets a Transaction resource. Accepts : TransactionData as input ", response = TransactionData.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Transaction resource found"),
    		@ApiResponse(code = 404, message = "Unable to find Transaction with given input")
    })    
    public Response getTransaction(TransactionData transactionData)throws DataRetrievalException {
        TransactionData transactionDataFetched = transactionService.getTransaction(transactionData);
        if(transactionDataFetched !=null && transactionDataFetched.getFromAccount()!=null){
            return Response.status(200).entity(transactionData).build();
        }else{
            return Response.status(404).build();
        }
    }
    @POST
    @Path("/update")
    public Response updateTransacton(TransactionData transactionData)throws DataInsertionException {
       try {
           if (transactionData.getStatus() == 4) {
               TransactionData fetchedTransactionData = transactionService.getTransaction(transactionData);
               if (fetchedTransactionData!=null && (transactionData.getToOtp() == fetchedTransactionData.getToOtp())
                       && (transactionData.getFromOtp() == fetchedTransactionData.getFromOtp())
                       ) {
                   //Update data in the transaction
                   //get the user details
                   UserData userDataFrom = userService.getByUserName(transactionData.getFromAccount());
                   if (userDataFrom != null) {
                       double currentAmout = userDataFrom.getCurAmount();
                       double availableAmount = userDataFrom.getAvalAmount();
                       availableAmount = availableAmount - transactionData.getTrnsfAmount();
                       userDataFrom.setAvalAmount(availableAmount);
                       String fromUserStatus = userService.createUser(userDataFrom);
                       if (StringUtils.equals(fromUserStatus, Constants.SUCCESS)) {
                           UserData fromDataTo = userService.getByUserName(transactionData.getToAccount());
                           if (fromDataTo != null) {
                               double toCurrentAmout = fromDataTo.getCurAmount();
                               double toAvailableAmount = fromDataTo.getAvalAmount();
                               toAvailableAmount = toAvailableAmount - transactionData.getTrnsfAmount();
                               fromDataTo.setAvalAmount(toAvailableAmount);
                               String toUserStatus = userService.createUser(userDataFrom);
                               if (StringUtils.equals(toUserStatus, Constants.SUCCESS)) {
                                   transactionData.setStatus(5);
                               }
                           }
                       }
                   }
               }
           }
       }catch(Exception ex){
       }
        String Status = transactionService.updateTransaction(transactionData);
        if(StringUtils.equals(Status, Constants.SUCCESS)){
            return Response.status(200).entity(transactionData).build();
        }else{
            return Response.status(400).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate")
    public Response validateTransaction(TransactionData transactionData) throws DataInsertionException {
       if(transactionData.getStatus()==4){
           String Status = transactionService.createTransaction(transactionData);
           if(StringUtils.equals(Status, Constants.SUCCESS)){
               return Response.status(200).entity(transactionData).build();
           }else{
               return Response.status(400).build();
           }
        }else{
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/tranJson")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionData getTransactionJson()throws DataInsertionException {
        TransactionData trn = new TransactionData();
        return trn;
    }
}