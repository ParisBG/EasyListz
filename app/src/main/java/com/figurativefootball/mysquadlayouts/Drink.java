package com.figurativefootball.mysquadlayouts;

public class Drink {

    private String name;
    private String description;
    private int imageResourceId;

    public static final Drink[] drinks = {
            new Drink(
                    "Latte",
                    "A couple of espresso shots with steamed milk",
                    R.drawable.latte),

            new Drink(
                    "Cappuccino",
                    "Espresso, Hot milk, and a steamed milk foam",
                    R.drawable.cappuccino),

            new Drink(
                    "Frappuccino",
                    "Espresso, Cold milk, chilled on ice",
                    R.drawable.frappuccino)
    };

    private Drink(String name, String desciption, int imageResourceId) {
        this.name = name;
        this.description = desciption;
        this.imageResourceId = imageResourceId;
    }
        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }

        public String toString() {
            return this.name;
        }
    }

