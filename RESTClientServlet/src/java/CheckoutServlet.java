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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
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
 * @author Luke
 */
@WebServlet(urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

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
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        try {
            /* TODO output your page here. You may use following sample code. */
            
            //Depending on if the parameters are posted to this servlet or not. Temp using history below
            //ArrayList<String> cart = (ArrayList<String>)session.getAttribute("cart");
            //ArrayList<String> cart = request.getParameterValues("pid");
            ArrayList<String> cart = (ArrayList<String>)session.getAttribute("shopping_cart");
            String innerHtml = "";
            if (cart == null || cart.size() < 1) {
                innerHtml += "<h2>No items are in your cart</h2>";
            }
            else {
                innerHtml += "<h2 onload='getTotalPrice()'>Checkout for Order of:</h2>";
            }
            
            double totalPrice = 0.0;
            for (int i = 0; i < cart.size(); i++) {
                String pid = cart.get(i);
                String jsonResponse = target.path("v1").path("api").path("cars").
                    path(pid).
                    request().
                    accept(MediaType.APPLICATION_JSON).
                    get(String.class);
                 ObjectMapper objectMapper = new ObjectMapper();
                Car car = objectMapper.readValue(jsonResponse, Car.class);
                String act_pid = car.getPid();
                String make = car.getMake();
                String model = car.getModel();
                String year = car.getYear();
                String price = car.getPrice();
                totalPrice += Double.parseDouble(price);
                innerHtml += "<b>PID " + act_pid.toString() + ": </b> <a href=ProductDetailsServlet?pid=" + act_pid.toString()
                        + " onclick='addCarToHistory(" + act_pid.toString() + ");'>" + make + " " + model + " " + year.toString() + "</a><br/>";
                
            }
            
            Locale locale = new Locale("en", "US");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
            innerHtml += "<br/><b>TOTAL PRICE: </b>" + formatter.format(totalPrice) + "<br/>";
            
            innerHtml += "<br/>";
            out.write(innerHtml);
            response.setStatus(200);
        }
        catch (Exception e){
            response.setStatus(500);
        }
        out.close();
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
