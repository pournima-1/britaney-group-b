package org.launchcode.closettracker.models;

public enum Color {
    SELECT ("Select a color"),
    RED ("Red"),
    ORANGE ("Orange"),
    YELLOW ("Yellow"),
    GREEN ("Green"),
    BLUE ("Blue"),
    PURPLE ("Purple"),
    BLACK ("Black"),
    WHITE ("White"),
    TAN ("Tan"),
    GRAY ("Gray"),
    OTHER ("Other");

    private final String displayName;

    Color(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
