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
@WebServlet(urlPatterns = {"/IndexServlet"})
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        
        PrintWriter out = response.getWriter();
        
        String[] categories = {"hatch", "minivan", "sedan", "sports", "suv"};
        String htmlText = "<div class='categories'>"
                        + "<table id='car_table'>"
                        + "<thead><tr><th>Hatch</th><th>Minivan</th><th>Sedan</th><th>Sports</th><th>SUV</th></tr></thead>"
                        + "<tbody>";
        String endHtml = "</tbody></table>";
        for (int i =0; i < 2; i++){
            htmlText += "<tr>";
            for (int j = 0; j < 5; j++){
                String jsonResponse = target.path("v1").path("api").path("cars").
                        queryParam("category", categories[j]).queryParam("row", i).
                        request().
                        accept(MediaType.APPLICATION_JSON).
                        get(String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                Car car = objectMapper.readValue(jsonResponse, Car.class);
                String pid = car.getPid();
                String main_img = car.getMainImg();
                String make = car.getMake();
                String model = car.getModel();
                String year = car.getYear();
                String price = car.getPrice();
                htmlText += "<td><a href=ProductDetailsServlet?pid=" + pid + " onclick='addCarToHistory(" + pid.toString() + ");'>";
                htmlText += "<img src=" + main_img + " width=250 height=250></a>";
                htmlText += "<b>" + make + " " + model + " " + year + "<br>$ ";
                htmlText += price + "</b></td>";
            }
            htmlText += "</tr>";
        }
        htmlText += endHtml;
        
        out.write(htmlText);
    }
    
    private static URI getBaseURI() {

        //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/CarRestService").build();
    }
    
}
