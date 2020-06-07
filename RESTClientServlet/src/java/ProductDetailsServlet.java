import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Luke
 */
@WebServlet(urlPatterns = {"/ProductDetailsServlet"})
public class ProductDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pid = request.getParameter("pid");
        
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        
        PrintWriter out = response.getWriter();
        
        String htmlText = "<!DOCTYPE html>"
                        + "<html>"
                            + "<head>"
                                + "<title>Vending Cars - Car Buying Made Simple</title>"
                                + "<link rel='stylesheet' href='car_info.css'>"
                            + "</head>"
                            + "<body>"
                                + "<div class='banner'>"
                                    + "<h1>Vending Cars</h1>"
                                    + "<div class='slogan'>"
                                        + "The lowest priced cars anywhere"
                                    + "</div>"
                                    + "<div id ='car_info'>";
                                    /*+ "<div id ='cart_id'>"
                                    + "</div>";*/
        String endHtml = "</div></div></body></html>";
        
        String jsonResponse = target.path("v1").path("api").path("cars").
                path(pid).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(jsonResponse, Car.class);
        String main_img = car.getMainImg();
        String sub_img = car.getSubImg();
        String int_img = car.getIntImg();
        String category = car.getCategory();
        String make = car.getMake();
        String model = car.getModel();
        String trim = car.getTrim();
        String color = car.getColor();
        String year = car.getYear();
        String odo = car.getOdo();
        String gearbox = car.getGearbox();
        String engine = car.getEngine();
        String price = car.getPrice();
        String location = car.getLocation();
        String description = car.getDescription();
        
        htmlText += 
            "<div class=\"images\">" +
                "<img src=" + main_img + " width=250 height=250>" +
                "<img src=" + sub_img + " width=250 height=250>" + 
                "<img src=" + int_img + " width=250 height=250>" +
            "</div>";
        htmlText += "<div class=\"description\">" +
                        "<table id=\"description_table\">" + 
                            "<thead><tr><th colspan=2>Specifications</th></tr></thead>" +
                            "<tbody>" + 
                                "<tr><td>Category</td><td>" + category + "</td></tr>" +
                                "<tr><td>Make    </td><td>"+ make +      "</td></tr>" +
                                "<tr><td>Model</td><td>"+ model +"</td></tr>"+
                                "<tr><td>Trim</td><td>"+ trim + "</td></tr>" + 
                                "<tr><td>Color</td><td>"+ color + "</td></tr>" +
                                "<tr><td>Year </td><td>" + year + "</td></tr>" +
                                "<tr><td>Odo</td><td>" + odo + "</td></tr>" + 
                                "<tr><td>Gearbox</td><td>" + gearbox + "</td></tr>" +
                                "<tr><td>Engine</td><td>" + engine + "</td></tr>" + 
                                "<tr><td>Price</td><td>" + price + "</td></tr>" +
                                "<tr><td>Location</td><td>"+ location + "</td></tr>" +
                                "<tr><td>Description</td><td>" + description + "</td></tr>" +
                            "</tbody>" +
                        "</table>"+
                    "</div>";
        htmlText += 
            "<div class=\"buttonDiv\">" +
                        
                "<button type=\"button\" id=\"orderButton\" onclick= 'saveCar()'>"+
                    "Add to Cart" +
                "</button>"+
                "<br/>"+
                "<button type=\"button\" id=\"orderButton\" onclick= 'location.href=\"index.jsp\";'>"+
                    "Return to Main Page" +
                "</button>"+
                "<br/>"+
                "<button type=\"button\" id=\"orderButton\" onclick= 'location.href=\"checkout.html\";'>"+
                    "Checkout" +
                "</button>"+
            "</div>";
        htmlText += endHtml;
        
        out.write(htmlText);
    }
    
    private static URI getBaseURI() {

        //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/CarRestService").build();
    }
    
}
