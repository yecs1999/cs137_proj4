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
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Luke
 */
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
        try {
            /* TODO output your page here. You may use following sample code. */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbcon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars?useSSL=false", "root", "1234");
            
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
                String query = "Select * from cardata where pid = ?";
                PreparedStatement stmt = dbcon.prepareStatement(query);
                stmt.setString(1, pid);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    Integer act_pid = rs.getInt("pid");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    Integer year = rs.getInt("year");
                    String price = rs.getString("price");
                    totalPrice += Double.parseDouble(price);
                    innerHtml += "<b>PID " + act_pid.toString() + ": </b> <a href=car_info.html?pid=" + act_pid.toString()
                            + " onclick='addCarToHistory(" + act_pid.toString() + ");'>" + make + " " + model + " " + year.toString() + "</a><br/>";
                }
                stmt.close();
                rs.close();
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

}
