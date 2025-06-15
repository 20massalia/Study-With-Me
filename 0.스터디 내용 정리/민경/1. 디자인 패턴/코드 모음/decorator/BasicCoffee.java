package org.example.decorator;

public class BasicCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Basic Coffee";
    }

    @Override
    public int getCost() {
        return 3000;
    }
}
