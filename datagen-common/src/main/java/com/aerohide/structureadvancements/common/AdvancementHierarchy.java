package com.aerohide.structureadvancements.common;

import java.util.HashMap;
import java.util.Map;

public class AdvancementHierarchy {
    public enum ENTRY {
        ROOT("root"),
        OVERWORLD("overworld"),
        NETHER("nether"),
        THE_END("the_end");

        public final String id;

        ENTRY(String id) {
            this.id = id;
        }
    }

    public enum STRUCTURE {
        MINESHAFT("mineshaft"),
        STRONGHOLD("stronghold"),
        BURIED_TREASURE("buried_treasure"),
        TRIAL_CHAMBERS("trial_chambers"),
        ANCIENT_CITY("ancient_city"),
        OCEAN_RUIN("ocean_ruin"),
        SHIPWRECK("shipwreck"),
        MONUMENT("monument"),
        VILLAGE("village"),
        DESERT_PYRAMID("desert_pyramid"),
        JUNGLE_PYRAMID("jungle_pyramid"),
        IGLOO("igloo"),
        SWAMP_HUT("swamp_hut"),
        MANSION("mansion"),
        PILLAGER_OUTPOST("pillager_outpost"),
        TRAIL_RUINS("trail_ruins"),
        FORTRESS("fortress"),
        BASTION_REMNANT("bastion_remnant"),
        NETHER_FOSSIL("nether_fossil"),
        RUINED_PORTAL("ruined_portal"),
        END_CITY("end_city");

        public final String id;

        STRUCTURE(String id) {
            this.id = id;
        }
    }

    private static final Map<String, String> PARENTS = new HashMap<>();
    private static final Map<String, CommonFrameType> FRAME_TYPES = new HashMap<>();

    static {
        PARENTS.put(ENTRY.OVERWORLD.id, ENTRY.ROOT.id);
        PARENTS.put(ENTRY.NETHER.id, ENTRY.ROOT.id);
        PARENTS.put(ENTRY.THE_END.id, ENTRY.ROOT.id);

        PARENTS.put(STRUCTURE.MINESHAFT.id, STRUCTURE.BURIED_TREASURE.id);
        PARENTS.put(STRUCTURE.STRONGHOLD.id, STRUCTURE.MINESHAFT.id);
        PARENTS.put(STRUCTURE.BURIED_TREASURE.id, ENTRY.OVERWORLD.id);
        PARENTS.put(STRUCTURE.TRIAL_CHAMBERS.id, STRUCTURE.STRONGHOLD.id);
        PARENTS.put(STRUCTURE.ANCIENT_CITY.id, STRUCTURE.TRIAL_CHAMBERS.id);

        PARENTS.put(STRUCTURE.OCEAN_RUIN.id, ENTRY.OVERWORLD.id);
        PARENTS.put(STRUCTURE.SHIPWRECK.id, STRUCTURE.OCEAN_RUIN.id);
        PARENTS.put(STRUCTURE.MONUMENT.id, STRUCTURE.SHIPWRECK.id);

        PARENTS.put(STRUCTURE.VILLAGE.id, ENTRY.OVERWORLD.id);
        PARENTS.put(STRUCTURE.DESERT_PYRAMID.id, ENTRY.OVERWORLD.id);
        PARENTS.put(STRUCTURE.JUNGLE_PYRAMID.id, STRUCTURE.DESERT_PYRAMID.id);
        PARENTS.put(STRUCTURE.IGLOO.id, STRUCTURE.VILLAGE.id);
        PARENTS.put(STRUCTURE.SWAMP_HUT.id, STRUCTURE.VILLAGE.id);
        PARENTS.put(STRUCTURE.MANSION.id, STRUCTURE.VILLAGE.id);
        PARENTS.put(STRUCTURE.PILLAGER_OUTPOST.id, STRUCTURE.VILLAGE.id);
        PARENTS.put(STRUCTURE.TRAIL_RUINS.id, STRUCTURE.DESERT_PYRAMID.id);

        PARENTS.put(STRUCTURE.FORTRESS.id, ENTRY.NETHER.id);
        PARENTS.put(STRUCTURE.BASTION_REMNANT.id, STRUCTURE.FORTRESS.id);
        PARENTS.put(STRUCTURE.NETHER_FOSSIL.id, ENTRY.NETHER.id);
        PARENTS.put(STRUCTURE.RUINED_PORTAL.id, ENTRY.NETHER.id);

        PARENTS.put(STRUCTURE.END_CITY.id, ENTRY.THE_END.id);

        // goals are default per couriway's video
        FRAME_TYPES.put(ENTRY.ROOT.id, CommonFrameType.CHALLENGE);
        FRAME_TYPES.put(ENTRY.OVERWORLD.id, CommonFrameType.TASK);
        FRAME_TYPES.put(ENTRY.NETHER.id, CommonFrameType.TASK);
        FRAME_TYPES.put(ENTRY.THE_END.id, CommonFrameType.TASK);
    }

    public static String getParent(String advancementName) {
        return PARENTS.getOrDefault(advancementName, ENTRY.ROOT.id);
    }

    public static String getParent(STRUCTURE advancement) {
        return getParent(advancement.id);
    }

    public static CommonFrameType getFrameType(String advancementName) {
        return FRAME_TYPES.getOrDefault(advancementName, CommonFrameType.GOAL);
    }
}