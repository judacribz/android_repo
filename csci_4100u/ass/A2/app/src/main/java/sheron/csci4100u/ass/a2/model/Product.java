package sheron.csci4100u.ass.a2.model;


public class Product {
    private long productId;
    private String name;
    private String description;
    private float price;


    public Product(String name,
                   String description,
                   float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public void setProductId(long productId) {
        this.productId = productId;
    }


    public long getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public float getPrice() {
        return price;
    }
}
