package com.uci.carrestservice;

import com.uci.carrestservice.service.CarService;

import com.uci.carrestservice.model.Order;
import com.uci.carrestservice.model.Car;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This class contains the methods that will respond to the various endpoints that you define for your RESTful API Service.
 *
 */
//todos will be the pathsegment that precedes any path segment specified by @Path on a method.
@Path("/cars")
public class CarResource {


    //This method represents an endpoint with the URL /todos/{id} and a GET request ( Note that {id} is a placeholder for a path parameter)
    @Path("{pid}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getCarById(@PathParam("pid") String pid/* The {id} placeholder parameter is resolved */) {
        //invokes the DB method which will fetch a todo_list item object by id
        Car car = CarService.getCarById(pid);
        System.out.println(car);

        //Respond with a 404 if there is no such todo_list item for the id provided
        if(car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a todo_list_item object to return as response
        return Response.ok(car).build();
    }

    //This method represents an endpoint with the URL /todos and a GET request.
    // Since there is no @PathParam mentioned, the /todos as a relative path and a GET request will invoke this method.
    @GET
    @Produces( { MediaType.APPLICATION_JSON })
    public Response getCarByRowAndCat(@QueryParam("category") String category, @QueryParam("row") int row) {
        System.out.println(row+1);
        Car car = CarService.getCarByRowAndCat(category, row);
        System.out.println(car);
        if(car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(car).build();
    }
    //IMPORTANT COMMENTS ON HOW TO COMMUNICATE:
    //THe json Format for the car that gets returned is:
    /*{"category":"sports",
    "make":"Lotus",
    "model":"Esprit",
    "trim":"V8",
    "color":"Silver",
    "year":"2003",
    "odo":"50000",
    "gearbox":"Manual",
    "engine":"3.5 8cyl",
    "price":"40000",
    "location":"Scottsdale, AZ",
    "description":"Family owned for 17 years, mostly highway miles, always maintained at
    lotus dealership. Very rare twin turbo v8 version, this is one of the last affordable 
    exotic cars.",
    "subImg":"./img/sports/csp11/2.jpg",
    "mainImg":"./img/sports/csp11/1.jpg",
    "intImg":"./img/sports/csp11/3.jpg"}
    */
    
    //In your servlet convert the Order Object into a json 

    
    
    //https://examples.javacodegeeks.com/enterprise-java/rest/jersey/json-example-with-jersey-jackson/
    // good resource above for how to POST a json
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON}) //This method accepts form parameters.
    //If you were to send a POST through a form submit, this method would be called.
    public Response addOrder(Order order) {
     
        System.out.println(order);

        if(CarService.AddOrder(order)) {
            return Response.ok().entity("ORDER Added Successfully").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }


}
