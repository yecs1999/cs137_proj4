/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import org.glassfish.jersey.client.ClientConfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
/**
 *
 * @author Zhen
 */
@WebServlet(urlPatterns = {"/CheckoutSubmissionServlet"})
public class CheckoutSubmissionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        try {
            String modelNames = "";
            ArrayList<String> cart = (ArrayList<String>) session.getAttribute("shopping_cart");
            for (int i = 0; i < cart.size(); i++) {
                String pid = cart.get(i);
               String jsonResponse = target.path("v1").path("api").path("cars").
                    path(pid).
                    request().
                    accept(MediaType.APPLICATION_JSON).
                    get(String.class);
                 ObjectMapper objectMapper = new ObjectMapper();
                Car car = objectMapper.readValue(jsonResponse, Car.class);
                String make = car.getMake();
                String model = car.getModel();
                String year = car.getYear();
                modelNames += year + " " + make + " " + model + ", ";
               
            }
            modelNames = modelNames.substring(0, modelNames.length() - 2);

            request.setAttribute("modelNames", modelNames);
            RequestDispatcher rd = request.getRequestDispatcher("OrderDetailsServlet");
            rd.forward(request, response);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
