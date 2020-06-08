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

@XmlRootElement(name = "order")
public class Order {
    private String model;
    private String fullname;
    private String phone;
    private String email;
    private String method;
    private String country;
    private String fullAddress;
    private String card;
    private String cvv;
    public void setModel(String model){
        this.model = model;
    }
    public void setFullName(String fullname){
        this.fullname= fullname;
    }
    public void setPhone(String phone){
        this.phone =  phone;
    }
    public void setEmail(String email){
        this.email= email;
    }
    public void setMethod(String method){
        this.method= method;
    }
    public void setCountry(String country){
        this.country= country;
    }
    public void setFullAddress(String fullAddress){
        this.fullAddress= fullAddress;
    }
    public void setCard(String card){
        this.card= card;
    }
    public void setCVV(String cvv){
        this.cvv= cvv;
    }    
    public String getModel(){
        return model;
    }
    public String getFullName(){
        return fullname;
    }
    public String getPhone(){
        return phone;
    }
    public String getEmail(){
        return email;
    }
    public String getMethod(){
        return method;
    }
    public String getCountry(){
        return country;
    }
    public String getFullAddress(){
        return fullAddress;
    }
    public String getCard(){
        return card;
    }
    public String getCVV(){
        return cvv;
    }
    @Override
    public String toString(){
        return "THIS IS TEST Order model:" + model;
    }
        
}
