package com.codebee.shopkart.Model;

public class Product {
    private String Id;
    private String Name;
    private String Description;
    private double Price;
    private String Vendor;
    private int RemainingCount;
    private String Images;
    private String Model;
    private String Size;
    private String Brand;
    private String Category;
    private String Colour;
    private long ReleasedTime;
    private long UpdatedTime;
    private boolean Shippable;
    private double Discount;
    private String FeaturedImage;
    private int SoldCount;

    public Product() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public int getRemainingCount() {
        return RemainingCount;
    }

    public void setRemainingCount(int remainingCount) {
        RemainingCount = remainingCount;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }

    public long getReleasedTime() {
        return ReleasedTime;
    }

    public void setReleasedTime(long releasedTime) {
        ReleasedTime = releasedTime;
    }

    public long getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        UpdatedTime = updatedTime;
    }

    public boolean isShippable() {
        return Shippable;
    }

    public void setShippable(boolean shippable) {
        Shippable = shippable;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public String getFeaturedImage() {
        return FeaturedImage;
    }

    public void setFeaturedImage(String featuredImage) {
        FeaturedImage = featuredImage;
    }

    public int getSoldCount() {
        return SoldCount;
    }

    public void setSoldCount(int soldCount) {
        SoldCount = soldCount;
    }
}
