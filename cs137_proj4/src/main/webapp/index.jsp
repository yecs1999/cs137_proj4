<%-- 
    Document   : index
    Created on : Jun 5, 2020, 8:02:56 PM
    Author     : Luke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.IOException, java.io.PrintWriter, javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.sql.DriverManager, java.sql.Connection, java.sql.Statement, java.sql.ResultSet, java.sql.PreparedStatement, java.util.ArrayList, javax.servlet.RequestDispatcher" %>
<%! Connection dbcon; Statement stmt; String cat; ResultSet cat_len; ArrayList<String> categories; String htmlText; String endHtml; String carRowQuery;
    PreparedStatement cstmt; ResultSet crow_rs; Integer pid; String main_img; String make; String model; Integer year; String price; RequestDispatcher dis; %>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Cars - Car Buying Made Simple</title>
        <link rel="stylesheet" href="index.css">
        <%--<script type='text/javascript' src='index.js'></script>--%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="banner">
            <h1>Vending Cars</h1>
            <div class="slogan">
                The lowest priced cars anywhere
            </div>
        </div>
        <div class="sidebar"> 
            <div class="info">
                <h2>About</h2>
                <p>Here at Vending Cars we are committed to streamlining your car buying experience by making shopping for one as easy as a single click!
                    No more haggling with third party sellers or shady dealerships, we guarantee the lowest buy it now prices anywhere on the web. All our vehicles are certified pre-owned
                    And come with a 1 Year warranty so you can drive at ease knowing you got the best deal</p>
            </div>
            <div class="links">
                <h2>Links</h2>
                <ul>
                    <li><a href="https://www.cars.com">cars.com</a></li>
                    <li><a href="https://www.cargurus.com">cargurus.com</a></li>
                    <li><a href="https://www.carmax.com">carmax.com</a></li>
                </ul>
            </div>
            <div class="contact">
                <h2>Contact info</h2>
                <p>
                    Donald Bren Hall, 6210, Irvine, CA 92697 <br>
                    Phone#: (949)-824-7427 <br>
                    Christopher Ye: Student# 93031221 <br>
                    Zhen Li: Student# 84257555 <br>
                    Emerson Chow: Student# 29527073 <br>
                    Luke Falcone: Student# 26133003 <br>
                </p>
            </div>
            <div style="text-align: center">
                <form action="checkout.html">
                    <input class="buttonClass" type="submit" value="Checkout" />
                </form>
            </div>
        </div>
        <%--<script src="car_info.js" type="text/javascript"></script>--%>
        <div id ="info">
        <div class="categories">
            <%
                Class.forName("com.mysql.cj.jdbc.Driver");
                dbcon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars?useSSL=false", "root", "1234");
                stmt = dbcon.createStatement();
                cat = "select * from carcategories group by category order by category";
                cat_len = stmt.executeQuery(cat);
                categories = new ArrayList<String>();
                while (cat_len.next()){
                    categories.add(cat_len.getString("category"));
                }
                htmlText = "<div class='categories'>"
                        + "<table id='car_table'>"
                        + "<thead><tr><th>Hatch</th><th>Minivan</th><th>Sedan</th><th>Sports</th><th>SUV</th></tr></thead>"
                        + "<tbody>";
                endHtml = "</tbody></table>";
                for (int i =0; i < 2; i++){
                    htmlText += "<tr>";
                    for (int j = 0; j < categories.size();j++){
                        carRowQuery = "Select cardata.*, carimages.main_img from"
                                + " cardata left join carimages on carimages.pid = cardata.pid "
                                + "where category = ? limit 1 offset ?";
                        cstmt = dbcon.prepareStatement(carRowQuery);
                        cstmt.setString(1, categories.get(j));
                        cstmt.setInt(2, i);
                        crow_rs = cstmt.executeQuery();
                        if (crow_rs.next()){
                            pid = crow_rs.getInt("pid");
                            main_img = crow_rs.getString("main_img");
                            make = crow_rs.getString("make");
                            model = crow_rs.getString("model");
                            year = crow_rs.getInt("year");
                            price = crow_rs.getString("price");
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
                dis = request.getRequestDispatcher("CarHistoryServlet");          
                dis.include(request, response);
            %>
            <!--<table id="car_table">
                <thead><tr><th>Hatch</th><th>Minivan</th><th>Sedan</th><th>Sports</th><th>SUV</th></tr></thead>
                <tbody></tbody>
            </table>-->
        </div>    
        </div>
    </body>

</html>