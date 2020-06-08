/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import org.glassfish.jersey.client.ClientConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientResponse;
/**
 *
 * @author Zhen
 */
@WebServlet(urlPatterns = {"/OrderDetailsServlet"})
public class OrderDetailsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        HttpSession session = request.getSession(); 
        PrintWriter out = response.getWriter();
        String innerHtml = "";
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        
            /* TODO output your page here. You may use following sample code. */
     
        String first = request.getParameter("firstname");
        String last = request.getParameter("lastname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String method = request.getParameter("method");
        String country = request.getParameter("country");
        String address = request.getParameter("address");
        String zip = request.getParameter("zip");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String card = request.getParameter("card");
        String cvv = request.getParameter("cvv");

        String fullAddress = address + " " + city + " " + state + " " + zip;
        String fullname = first + " " + last;
        String modelString = (String)request.getAttribute("modelNames");            
        Order order = new Order();
        order.setModel(modelString);
        order.setFullName(fullname);
        order.setPhone(phone);
        order.setEmail(email);
        order.setMethod(method);
        order.setCountry(country);
        order.setFullAddress(fullAddress);
        order.setCard(card);
        order.setCVV(cvv);
        System.out.println("THis is the CVV in Order obj:" + order.getCVV());
        //Adds the order to the database
        //forms the query and executes it
        OrderWrapper orderw = new OrderWrapper();
        orderw.setOrder(order);
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream jsonObj = new ByteArrayOutputStream();
        try{
            objectMapper.writeValue(jsonObj, order);
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
        String OrderAsStr = jsonObj.toString();
        
        InputStream orderStream = new ByteArrayInputStream(OrderAsStr.getBytes());
        System.out.println("THis is the jsonstring: " + Entity.json(orderStream));
        final OrderWrapper resp = target.path("v1").path("api").path("cars").path("send")
        .request(MediaType.APPLICATION_JSON_TYPE)
        .post(Entity.entity(orderw, MediaType.APPLICATION_JSON_TYPE),OrderWrapper.class);
        System.out.println("Hello are we here yet");
        System.out.println(resp);
           
        
        /*
        if (resp.getOrder() != 200){
            System.out.println("Did not post correctly");
        }
        */
         String addressString = !method.equals("pickup") ? "Country: "+ country +"<br>"+
                    "Shipping Address: "+ fullAddress +"<br>" : "";
         
         
         
         innerHtml += "<!DOCTYPE html>" +
        "<html>" + 
            "<head>" + 
                "<title>Your order has been processed</title>" +
            "</head>" +
            "<body>" + 
                "<h1>Your Order Summary</h1>" + 
                "<div>" +
                    "Car Models: "  + modelString +"<br>"+
                    "Name: "+ fullname + "<br>"+
                    "Phone: "+ phone +"<br>"+
                    "Email: "+ email +"<br>"+
                    "Shipping Method: "+ method +"<br>"+
                     addressString +
                    "Card Number: " + card + " CVV: "+ cvv +
                "</div>"+
                "<form action=\"index.jsp\">"+
                    "<input type=\"submit\" value=\"Go back to index\" />"+
                "</form>"+
            "</body>"+
        "</html>";

         out.write(innerHtml);
         response.setStatus(200);
        


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private static URI getBaseURI() {

        //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/CarRestService").build();
    }
}
