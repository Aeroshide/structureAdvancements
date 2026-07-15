package com.aerohide.structureadvancements.common;
public class CommonStrings {
    public static String getRootTitle() { return "Structure Explorer"; }
    public static String getRootDescription() { return "Find every structure in Minecraft"; }
    public static String getDimensionTitle(MCVersion version, String dimension) {
        return switch (dimension.toLowerCase()) {
            case "overworld" -> "Overworld";
            case "nether" -> (version == MCVersion.V1_19 || version == MCVersion.V1_20) ? "Nether" : "The Nether";
            case "the_end", "end" -> (version == MCVersion.V1_19 || version == MCVersion.V1_20) ? "End" : "The End";
            default -> throw new IllegalArgumentException("Unknown dimension: " + dimension);
        };
    }
    public static String getDimensionDescription(MCVersion version, String dimension) {
        return switch (dimension.toLowerCase()) {
            case "overworld" ->
                    (version == MCVersion.V1_19 || version == MCVersion.V1_20) ? "Find every structure in the Overworld" : "Overworld structures";
            case "nether" ->
                    (version == MCVersion.V1_19 || version == MCVersion.V1_20) ? "Find every structure in the Nether" : "Nether structures";
            case "the_end", "end" ->
                    (version == MCVersion.V1_19 || version == MCVersion.V1_20) ? "Find every structure in the End" : "End structures";
            default -> throw new IllegalArgumentException("Unknown dimension: " + dimension);
        };
    }
}
