/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chris
 */
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
    public Order(String model,String fullname, String phone,String email, String method, 
             String country, String fullAddress, String card, String cvv){
        this.model = model;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.method = method;
        this.country = country;
        this.fullAddress = fullAddress;
        this.card = card;
        this.cvv = cvv;

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
        
}
