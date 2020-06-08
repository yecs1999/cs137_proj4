/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chris
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderWrapper {
    private Order order;
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    } 
}
