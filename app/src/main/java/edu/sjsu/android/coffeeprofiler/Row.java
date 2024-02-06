package edu.sjsu.android.coffeeprofiler;

public class Row {
    private int rating;
    private String name, drink, roast;
    public Row(String n, String d, int r, String ro){
        rating = r;
        name = n;
        drink = d;
        roast = ro;
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

    public String getRoast() {
        return roast;
    }
}
