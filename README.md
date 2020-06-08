# cs137_proj4

GROUP MEMBERS:
Christopher Ye: Student# 93031221
Zhen Li: Student# 84257555
Emerson Chow: Student# 29527073
Luke Falcone: Student# 26133003

From the first page, cars will be queried and loaded into a table, and the user may click on a car picture to view more information regarding it. (Req 1: JSP Implementation, Req 2: Uses GET to receive car information, Req 3: GET is implemented through REST calls)

Also on the first page, car names for the last 5 visited product details pages are shown.

In the product details page, the user may proceed to checkout or press back to view the other cars once more. (Req 2: Uses GET to receive car information, Req 3: GET is implemented through REST calls)

If the user clicks add to cart then a car will be added to their cart using Session.

If they click checkout they will be presented with a checkout page, where the contents of their shopping cart are located and where they may fill their information in.

Clicking Checkout then shows the user a confirmation screen as well as stores the user's information in the database with validation. (Req 2: Uses POST to generate order information, Req 3: POST is implemented through REST calls)

RESTful Service Methods:
Base URL: http://localhost:8080/CarRestService

<b>GET:</b> <br>
    <b>Request URL:</b> <br>
        v1/api/cars/{pid} <br>
        v1/api/cars/category={category}&row={row} <br>
    <b>Sample Response:</b> <br>
    {"category":"sports", <br>
    "make":"Lotus",<br>
    "model":"Esprit",<br>
    "trim":"V8",<br>
    "color":"Silver",<br>
    "year":"2003",<br>
    "odo":"50000",<br>
    "gearbox":"Manual",<br>
    "engine":"3.5 8cyl",<br>
    "price":"40000",<br>
    "location":"Scottsdale, AZ",<br>
    "description":"Family owned for 17 years, mostly highway miles, always maintained at<br>
    lotus dealership. Very rare twin turbo v8 version, this is one of the last affordable<br>
    exotic cars.",<br>
    "subImg":"./img/sports/csp11/2.jpg",<br>
    "mainImg":"./img/sports/csp11/1.jpg",<br>
    "intImg":"./img/sports/csp11/3.jpg"}<br>

<b>POST:</b><br>
    <b>Request URL:</b><br>
        v1/api/cars/send<br>
   <b> Sample Request:</b><br>
    {"model":"2016 Chevrolet Corvette Z06",<br>
    "phone":"(949)468-8303",<br>
    "email":"me@gmail.com",<br>
    "method":"standard",<br>
    "country":"United States",<br>
    "fullAddress":"1111 Somewhere Ln Irvine CA 92604",<br>
    "card":"123456789011",<br>
    "cvv":"132",<br>
    "fullName":"Luke Falcone"}<br>
