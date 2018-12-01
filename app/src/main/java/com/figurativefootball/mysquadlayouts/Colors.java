package com.figurativefootball.mysquadlayouts;

import android.graphics.Color;

public class Colors {

    private String name;
    private int id;

    public static Colors[] colorList =
            { new Colors("None Selected", Color.GREEN),
                    new Colors("Regular Ass Red", Color.RED),
            new Colors("Young Ass Yellow", Color.YELLOW),
            new Colors("Bitch Ass Blue", Color.BLUE)};

    private Colors(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String toString() {
        return name;
    }
}


