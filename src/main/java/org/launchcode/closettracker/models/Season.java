package org.launchcode.closettracker.models;

public enum Season {
    SELECT ("Select a season"),
    SPRING ("Spring"),
    SUMMER ("Summer"),
    FALL ("Fall"),
    WINTER ("Winter");

    private final String displayName;

    Season(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
