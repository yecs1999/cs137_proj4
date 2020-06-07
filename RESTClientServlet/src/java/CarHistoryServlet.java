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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
//import java.util.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chris
 */
@WebServlet(urlPatterns = {"/CarHistoryServlet"})
public class CarHistoryServlet extends HttpServlet {

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
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            /* TODO output your page here. You may use following sample code. */
            ArrayList<String> recent = (ArrayList<String>)session.getAttribute("recent_cars");
            String pid = request.getParameter("pid");
            String innerHtml = "";
            if (recent == null){
                //Set recent with one if not set
                ArrayList<String> new_recent = new ArrayList<String>();
                session.setAttribute("recent_cars", new_recent);
                recent = (ArrayList<String>) session.getAttribute("recent_cars");
                innerHtml += "<h2>You don't have any history yet</h2><br>";
            }
            else{
                innerHtml += "<h2>Current History:</h2><br>";
            }
            if (recent.size() == 5 && pid != null){
                recent.remove(0);
                recent.add(pid);
            }
            else if (recent.size() < 5 && pid != null){
                recent.add(pid);
            }

            for (int i = recent.size() - 1; i >= 0; i--){
                String curr_pid = recent.get(i);
                String jsonResponse = target.path("v1").path("api").path("cars/"+curr_pid).
                        request().
                        accept(MediaType.APPLICATION_JSON).
                        get(String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                Car car = objectMapper.readValue(jsonResponse, Car.class);
                String myPid = car.getPid();
                String make = car.getMake();
                String model = car.getModel();
                String year = car.getYear();
                innerHtml += "<b>PID " + myPid + ":</b> <a href=ProductDetailsServlet?pid=" + myPid
                        + " onclick='addCarToHistory(" + myPid + ");'>" + make + " " + model + " " + year + "</a><br>";
                
            }
            
            innerHtml += "</div>";
            out.write(innerHtml);
            response.setStatus(200);
        }
        catch(Exception e){
            response.setStatus(500);
        }
       
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
