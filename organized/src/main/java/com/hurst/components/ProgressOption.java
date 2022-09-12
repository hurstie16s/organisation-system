package com.hurst.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public enum ProgressOption {
    BEHIND_SCHEDULE(Color.RED, "Behind Schedule"),
    ON_SCHEDULE(Color.ORANGE, "On Schedule"),
    AHEAD_SCHEDULE(Color.GREEN, "Ahead of Schedule");

    final Color colour;
    final SimpleStringProperty value;

    ProgressOption (Color colour, String value) {
        this.colour = colour;
        this.value = new SimpleStringProperty(value);
    }
}
