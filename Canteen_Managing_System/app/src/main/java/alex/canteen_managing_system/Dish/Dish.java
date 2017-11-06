package alex.canteen_managing_system.Dish;

/**
 * Created by MACHENIKE on 2017/11/6.
 */

public class Dish {
    private String name;
    private String type;
    private float price;

    public Dish(String n, String t, float p){
        name = n;
        type = t;
        price = p;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setType(String type){
        this.type=type;
    }

    public void setPrice(float price){
        this.price=price;
    }

    public String Output(){
        return name+","+type+","+price;
    }
}