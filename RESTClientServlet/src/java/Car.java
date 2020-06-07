/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class Car {
    private String pid;
    private String mainImg;
    private String subImg;
    private String intImg;
    private String category;
    private String make;
    private String model;
    private String trim;
    private String color;
    private String year;
    private String odo;
    private String gearbox;
    private String engine;
    private String price;
    private String location;
    private String description;
    
    /*public Car(String main_img,String sub_img, String int_img,String category, String make, 
             String model, String trim, String color, String year, String odo, String gearbox,
             String engine, String price, String location, String description){
        this.main_img = main_img;
        this.sub_img = sub_img;
        this.int_img = int_img;
        this.category = category;
        this.make = make;
        this.model = model;
        this.trim = trim;
        this.color = color;
        this.year = year;
        this.odo = odo;
        this.gearbox = gearbox;
        this.engine = engine;
        this.price = price;
        this.location = location;
        this.description = description;
    }*/
    public String getPid(){
        return pid;
    }
    public String getMainImg(){
        return mainImg;
    }
    public String getSubImg(){
        return subImg;
    }
    public String getIntImg(){
        return intImg;
    }
    public String getCategory(){
        return category;
    }
    public String getMake(){
        return make;
    }
    public String getModel(){
        return model;
    }
    public String getTrim(){
        return trim;
    }
    public String getColor(){
        return color;
    }
    public String getYear(){
        return year;
    }
    public String getOdo(){
        return odo;
    }
    public String getGearbox(){
        return gearbox;
    }
    public String getEngine(){
        return engine;
    }
    public String getPrice(){
        return price;
    }
    public String getLocation(){
        return location;
    }
    public String getDescription(){
        return description;
    }
    
}