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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
//import java.util.Queue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chris
 */
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            /* TODO output your page here. You may use following sample code. */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbcon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars?useSSL=false", "root", "1234");
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
                String query = "select * from cardata where pid = ?";
                PreparedStatement stmt = dbcon.prepareStatement(query);
                stmt.setString(1, curr_pid);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    Integer act_pid = rs.getInt("pid");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    Integer year = rs.getInt("year");
                    innerHtml += "<b>PID " + act_pid.toString() + ":</b> <a href=car_info.html?pid=" + act_pid.toString()
                            + " onclick='addCarToHistory(" + act_pid.toString() + ");'>" + make + " " + model + " " + year.toString() + "</a><br>";
                }
                
                stmt.close();
                rs.close();
                
            }
            
            innerHtml += "</div>";
            out.write(innerHtml);
            response.setStatus(200);
        }
        catch(Exception e){
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
