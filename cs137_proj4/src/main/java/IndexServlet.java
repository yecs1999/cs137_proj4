/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Chris
 */
public class IndexServlet extends HttpServlet {

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
            Statement stmt = dbcon.createStatement();
            String cat = "select * from carcategories group by category order by category";
            ResultSet cat_len = stmt.executeQuery(cat);
            ArrayList<String> categories = new ArrayList<String>();
            while (cat_len.next()){
                categories.add(cat_len.getString("category"));
            }
            String htmlText = "<div class='categories'>"
                    + "<table id='car_table'>"
                    + "<thead><tr><th>Hatch</th><th>Minivan</th><th>Sedan</th><th>Sports</th><th>SUV</th></tr></thead>"
                    + "<tbody>";
            String endHtml = "</tbody></table>";
            for (int i =0; i < 2; i++){
                htmlText += "<tr>";
                for (int j = 0; j < categories.size();j++){
                    String carRowQuery = "Select cardata.*, carimages.main_img from"
                            + " cardata left join carimages on carimages.pid = cardata.pid "
                            + "where category = ? limit 1 offset ?";
                    PreparedStatement cstmt = dbcon.prepareStatement(carRowQuery);
                    cstmt.setString(1, categories.get(j));
                    cstmt.setInt(2, i);
                    ResultSet crow_rs = cstmt.executeQuery();
                    if (crow_rs.next()){
                        Integer pid = crow_rs.getInt("pid");
                        String main_img = crow_rs.getString("main_img");
                        String make = crow_rs.getString("make");
                        String model = crow_rs.getString("model");
                        Integer year = crow_rs.getInt("year");
                        String price = crow_rs.getString("price");
                        htmlText += "<td><a href=car_info.html?pid=" + pid.toString() + " onclick='addCarToHistory(" + pid.toString() + ");'>";
                        htmlText += "<img src=" + main_img + " width=250 height=250></a>";
                        htmlText += "<b>" + make + " " + model + " " + year + "<br>$ ";
                        htmlText += price + "</b></td>";
                    }
                    cstmt.close();
                    crow_rs.close();
                }
                htmlText += "</tr>";
            }
            htmlText += endHtml;
            stmt.close();
            cat_len.close();
            out.write(htmlText);
            RequestDispatcher dis = request.getRequestDispatcher("CarHistoryServlet");          
            dis.include(request, response);     

            
            response.setStatus(200);
            dbcon.close();
            
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
