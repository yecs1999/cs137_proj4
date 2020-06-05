/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zhen
 */
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

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbcon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars?useSSL=false", "root", "1234");
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
           
            //Adds the order to the database
            //forms the query and executes it
             String query = "INSERT INTO orders(model, fullname, phone, email, method, country, address, card, cvv)" + 
                     "VALUES (?,?,?,?,?,?,?,?,?)";
             PreparedStatement stmt = dbcon.prepareStatement(query);
             stmt.setString(1, modelString);
             stmt.setString(2, fullname);
             stmt.setString(3, phone);
             stmt.setString(4, email);
             stmt.setString(5, method);
             stmt.setString(6, country);
             stmt.setString(7, fullAddress);
             stmt.setString(8, card);
             stmt.setString(9, cvv);
             stmt.execute();
             
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
                    "<form action=\"index.html\">"+
                        "<input type=\"submit\" value=\"Go back to index\" />"+
                    "</form>"+
                "</body>"+
            "</html>";
             
             out.write(innerHtml);
             stmt.close();
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

}
