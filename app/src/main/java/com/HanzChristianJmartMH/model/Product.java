package com.HanzChristianJmartMH.model;

public class Product extends Serializable{
    public String name;
    public int weight;
    public boolean conditionUsed;
    public double price;
    public ProductCategory category;
    public int accountId;
    public double discount;
    public byte shipmentPlans;

    public String toString()
    {
        String string = "Name : " + this.name + "\nWeight : " + this.weight +
                "\nconditionUsed : " + this.conditionUsed + "\nprice : " +
                this.price + "\ncategory : " + this.category + "\ndiscount : " +
                this.discount + "\naccountId : " + this.accountId;
        return string;
    }
}
