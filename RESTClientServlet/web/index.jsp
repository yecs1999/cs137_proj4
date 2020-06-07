<%-- 
    Document   : index
    Created on : Jun 6, 2020, 6:46:06 PM
    Author     : Luke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Cars - Car Buying Made Simple</title>
        <link rel="stylesheet" href="index.css">
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
        <div id ="info">
            <jsp:include page="/IndexServlet" />
        </div>
    </body>

</html>