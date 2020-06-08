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

GET:
    Request URL:
        v1/api/cars/{pid}
        v1/api/cars/category={category}&row={row}
    Sample Response:
    {"category":"sports",
    "make":"Lotus",
    "model":"Esprit",
    "trim":"V8",
    "color":"Silver",
    "year":"2003",
    "odo":"50000",
    "gearbox":"Manual",
    "engine":"3.5 8cyl",
    "price":"40000",
    "location":"Scottsdale, AZ",
    "description":"Family owned for 17 years, mostly highway miles, always maintained at
    lotus dealership. Very rare twin turbo v8 version, this is one of the last affordable
    exotic cars.",
    "subImg":"./img/sports/csp11/2.jpg",
    "mainImg":"./img/sports/csp11/1.jpg",
    "intImg":"./img/sports/csp11/3.jpg"}

POST:
    Request URL:
        v1/api/cars/send
    Sample Request:
    {"model":"2016 Chevrolet Corvette Z06",
    "phone":"(949)468-8303",
    "email":"me@gmail.com",
    "method":"standard",
    "country":"United States",
    "fullAddress":"1111 Somewhere Ln Irvine CA 92604",
    "card":"123456789011",
    "cvv":"132",
    "fullName":"Luke Falcone"}
