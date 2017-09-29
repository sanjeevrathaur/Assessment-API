package com.test.rest.service.controller;

import com.test.data.source.FileDataSource;
import com.test.model.Account;
import com.test.service.AccountSvc;
import com.test.service.AccountSvcImpl;
import com.test.utils.Constant;
import com.test.utils.PATCH;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/account")
public class AccountRestController {

    //  TODO : Need to inject dependency via Spring
    FileDataSource fileDataSource = new FileDataSource();

    @Autowired
    AccountSvc accountSvc;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustommerAccounts() {

        String message = "No Record Found !";
        accountSvc = accountSvc == null ? accountSvc = new AccountSvcImpl() : accountSvc;
        try{
            org.json.JSONArray allCustomerAccounts = accountSvc.getAll();
            if(allCustomerAccounts == null || allCustomerAccounts.length() == 0){
                return Response.status(200).entity(message).build();
            }
            return Response.status(200).entity(allCustomerAccounts).build();

        }catch (Exception e){
            e.printStackTrace();
        }
        // we can change status http status accordingly.
        return Response.status(200).entity(message).build();
    }

    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustommerAccount(@PathParam("accountId")int accountId) {

        return Response.status(200).entity(fileDataSource.checkRecord(accountId)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRecord(String accountData) {

        accountSvc = accountSvc == null ? accountSvc = new AccountSvcImpl() : accountSvc;
        return Response.status(200).entity(accountSvc.create(accountData)).build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response partialUpdateCustommerAccount(String dataForUpdate) {

        return Response.status(200).entity(accountSvc.partialUpdate(dataForUpdate)).build();
    }

}