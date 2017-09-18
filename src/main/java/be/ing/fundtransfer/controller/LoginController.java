package be.ing.fundtransfer.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.service.UserService;
import be.ing.fundtransfer.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Component
@Api(value = "User resource", produces = "application/json")
@Path("/user")
public class LoginController {
	@Autowired
	UserService userService;


	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@PathParam("id") String id)  {
		try{
			UserData userData =userService.getByUserName(id);
			return Response.status(200).entity(userData).build();
		}catch (DataRetrievalException exp){
			return Response.status(400).build();
		}
	}

	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(){
		try{
			List<UserData> userData =userService.findAllUsers();
			return Response.status(200).entity(userData).build();
		}catch (DataRetrievalException exp){
			return Response.status(400).build();
		}
	}    

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create")
	@ApiOperation(value = "Creates User resource. Accepts : UserData as input ", response = UserData.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User resource created", responseHeaders = {
					@ResponseHeader(name = "Location", description = "The URL to retrieve created resource", response = String.class)
			}),
			@ApiResponse(code = 404, message = "Failed to create User resource ")
	})	
	public Response registerUser(UserData userData) {
		try{
			String Status = userService.createUser(userData);
			if(StringUtils.equals(Status,Constants.SUCCESS)){
				return Response.status(200).entity(userData).build();
			}else{
				return Response.status(404).build();
			}
		}catch (DataInsertionException exp){
			return Response.status(400).build();
		}
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	@ApiOperation(value = "User Login operation. Accepts : UserData as input ", response = UserData.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User resource validated"),
			@ApiResponse(code = 404, message = "Given user not found ")
	})	
	public Response validateLogin(UserData userData) {
		try{
			UserData userDataFetched = userService.getByUserName(userData.getUsername());
			if(userDataFetched.getPassword().equals(userData.getPassword())) {
				//return Response.status(200).entity(userDataFetched).build();
				return Response.status(200).entity(userData).header("Access-Control-Allow-Origin","*").build();//to work two different ports
			}else{
				return Response.status(404).build();
			}
		}catch (DataRetrievalException exp){
			return Response.status(400).build();
		}
	}
	@XmlRootElement
	@ApiModel(description = "To filters the list")
	class ContactsRequest {
		@XmlElement
		@ApiModelProperty(value = "name beginning with")
		public String name;
	}
}