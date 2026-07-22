package com.aerohide.structureadvancements.common;
public class CommonStrings {
    public static String getRootTitle() { return "Structure Explorer"; }
    public static String getRootDescription() { return "Find every structure in Minecraft"; }
    public static String getDimensionTitle(MCVersion version, String dimension) {
        return switch (dimension.toLowerCase()) {
            case "overworld" -> "Overworld";
            case "nether" -> "The Nether";
            case "the_end", "end" -> "The End";
            default -> throw new IllegalArgumentException("Unknown dimension: " + dimension);
        };
    }
    public static String getDimensionDescription(MCVersion version, String dimension) {
        return switch (dimension.toLowerCase()) {
            case "overworld" -> "Overworld structures";
            case "nether" -> "Nether structures";
            case "the_end", "end" -> "End structures";
            default -> throw new IllegalArgumentException("Unknown dimension: " + dimension);
        };
    }
}
