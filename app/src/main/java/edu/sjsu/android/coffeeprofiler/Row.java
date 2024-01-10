package edu.sjsu.android.coffeeprofiler;

public class Row {
    private int rating;
    private String name, drink;
    public Row(String n, String d, int r){
        rating = r;
        name = n;
        drink = d;
    }

    public int getRating() {
        return rating;
    }

    public String getDrink() {
        return drink;
    }

    public String getName() {
        return name;
    }
}
