package com.hurst.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public enum StatusOption {
    NOT_STARTED(Color.RED, "Not Started"),
    IN_PROGRESS(Color.BLUE, "In Progress"),
    PAUSED(Color.ORANGE, "Paused"),
    COMPLETED(Color.GREEN, "Completed");

    final Color colour;
    final SimpleStringProperty value;

    StatusOption (Color colour, String value) {
        this.colour = colour;
        this.value = new SimpleStringProperty(value);
    }
}
