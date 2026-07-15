package com.aerohide.structureadvancements.common;
public class IconBindings {
    public static String getIcon(MCVersion version, String key) {
        return switch (key.toLowerCase()) {
            case "root" -> "minecraft:compass";
            case "overworld" -> "minecraft:grass_block";
            case "nether" -> "minecraft:netherrack";
            case "the_end", "end" -> "minecraft:end_stone";
            default -> getStructureIcon(version, key);
        };
    }
    private static String getStructureIcon(MCVersion version, String structure) {
        boolean isNewFormat = (version == MCVersion.V1_21 || version == MCVersion.V26_2);
        return switch (structure.toLowerCase()) {
            case "mineshaft" -> "minecraft:cobweb";
            case "village" -> "minecraft:emerald";
            case "stronghold" -> "minecraft:end_portal_frame";
            case "desert_pyramid" -> "minecraft:tnt";
            case "jungle_pyramid" -> "minecraft:dispenser";
            case "igloo" -> "minecraft:snow_block";
            case "ocean_ruin" -> isNewFormat ? "minecraft:mossy_stone_bricks" : "minecraft:suspicious_gravel";
            case "shipwreck" -> "minecraft:oak_boat";
            case "monument" -> "minecraft:sea_lantern";
            case "mansion" -> "minecraft:totem_of_undying";
            case "buried_treasure" -> "minecraft:chest";
            case "pillager_outpost" -> "minecraft:dark_oak_log";
            case "swamp_hut" -> "minecraft:cauldron";
            case "ancient_city" -> "minecraft:sculk_shrieker";
            case "trail_ruins" -> "minecraft:brush";
            case "trial_chambers" -> "minecraft:trial_key";
            case "fortress" -> "minecraft:nether_brick";
            case "bastion_remnant" -> "minecraft:gilded_blackstone";
            case "nether_fossil" -> "minecraft:bone_block";
            case "ruined_portal" -> "minecraft:crying_obsidian";
            case "end_city" -> "minecraft:purpur_pillar";
            default -> throw new IllegalArgumentException("Unknown structure: " + structure);
        };
    }
}
