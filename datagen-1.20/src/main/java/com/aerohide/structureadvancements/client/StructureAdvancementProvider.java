package com.aerohide.structureadvancements.client;

import com.aerohide.structureadvancements.common.MCVersion;
import com.aerohide.structureadvancements.common.CommonFrameType;
import com.aerohide.structureadvancements.common.CommonStrings;
import com.aerohide.structureadvancements.common.IconBindings;
import com.aerohide.structureadvancements.common.SmartStringBuilder;
import com.aerohide.structureadvancements.common.AdvancementHierarchy;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;

import java.util.function.Consumer;

public class StructureAdvancementProvider extends FabricAdvancementProvider {

    public StructureAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    private FrameType mapFrameType(CommonFrameType commonFrameType) {
        switch (commonFrameType) {
            case CHALLENGE: return FrameType.CHALLENGE;
            case GOAL: return FrameType.GOAL;
            case TASK:
            default: return FrameType.TASK;
        }
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Map<String, Advancement> builtAdvancements = new HashMap<>();

        String rootIconId = IconBindings.getIcon(MCVersion.V1_20, "root");
        Item rootIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(rootIconId));
        DisplayInfo rootDisplay = new DisplayInfo(
            new ItemStack(rootIcon),
            Component.literal(CommonStrings.getRootTitle()),
            Component.literal(CommonStrings.getRootDescription()),
            new ResourceLocation("minecraft", "block/amethyst_block"),
            mapFrameType(AdvancementHierarchy.getFrameType("root")),
            true,
            true,
            false
        );
        rootDisplay.setLocation(0.0f, 0.0f);

        Advancement rootPlaceholder = Advancement.Builder.advancement()
            .display(rootDisplay)
            .addCriterion("dummy", PlayerTrigger.TriggerInstance.tick())
            .build(new ResourceLocation("structureadvancements", "structures/root"));
        builtAdvancements.put("root", rootPlaceholder);

        Advancement.Builder rootBuilder = Advancement.Builder.advancement()
            .display(rootDisplay);

        String overworldIconId = IconBindings.getIcon(MCVersion.V1_20, "overworld");
        Item overworldIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(overworldIconId));
        DisplayInfo overworldDisplay = new DisplayInfo(
            new ItemStack(overworldIcon),
            Component.literal(CommonStrings.getDimensionTitle(MCVersion.V1_20, "overworld")),
            Component.literal(CommonStrings.getDimensionDescription(MCVersion.V1_20, "overworld")),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType("overworld")),
            true,
            false,
            false
        );
        overworldDisplay.setLocation(-1.0f, 0.0f);

        Advancement overworld = Advancement.Builder.advancement()
            .parent(rootPlaceholder)
            .display(overworldDisplay)
            .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
            .save(consumer, "structureadvancements:structures/overworld");
        builtAdvancements.put("overworld", overworld);

        String netherIconId = IconBindings.getIcon(MCVersion.V1_20, "nether");
        Item netherIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(netherIconId));
        DisplayInfo netherDisplay = new DisplayInfo(
            new ItemStack(netherIcon),
            Component.literal(CommonStrings.getDimensionTitle(MCVersion.V1_20, "nether")),
            Component.literal(CommonStrings.getDimensionDescription(MCVersion.V1_20, "nether")),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType("nether")),
            true,
            false,
            false
        );
        netherDisplay.setLocation(-1.0f, 3.0f);

        Advancement nether = Advancement.Builder.advancement()
            .parent(rootPlaceholder)
            .display(netherDisplay)
            .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
            .save(consumer, "structureadvancements:structures/nether");
        builtAdvancements.put("nether", nether);

        String endIconId = IconBindings.getIcon(MCVersion.V1_20, "end");
        Item endIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(endIconId));
        DisplayInfo endDisplay = new DisplayInfo(
            new ItemStack(endIcon),
            Component.literal(CommonStrings.getDimensionTitle(MCVersion.V1_20, "end")),
            Component.literal(CommonStrings.getDimensionDescription(MCVersion.V1_20, "end")),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType("the_end")),
            true,
            false,
            false
        );
        endDisplay.setLocation(-1.0f, -3.0f);

        Advancement end = Advancement.Builder.advancement()
            .parent(rootPlaceholder)
            .display(endDisplay)
            .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
            .save(consumer, "structureadvancements:structures/the_end");
        builtAdvancements.put("the_end", end);

        createStructureTagAdvancement(consumer, builtAdvancements, "ocean_ruin", StructureTags.OCEAN_RUIN, -8.0f, -1.0f, rootBuilder);
        createStructureTagAdvancement(consumer, builtAdvancements, "shipwreck", StructureTags.SHIPWRECK, -2.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "monument", BuiltinStructures.OCEAN_MONUMENT, -3.0f, 0.0f, rootBuilder);

        createStructureTagAdvancement(consumer, builtAdvancements, "village", StructureTags.VILLAGE, -3.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "desert_pyramid", BuiltinStructures.DESERT_PYRAMID, -5.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "jungle_pyramid", BuiltinStructures.JUNGLE_TEMPLE, -6.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "igloo", BuiltinStructures.IGLOO, -7.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "swamp_hut", BuiltinStructures.SWAMP_HUT, -7.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "mansion", BuiltinStructures.WOODLAND_MANSION, -4.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "pillager_outpost", BuiltinStructures.PILLAGER_OUTPOST, -6.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "trail_ruins", BuiltinStructures.TRAIL_RUINS, -2.0f, 1.0f, rootBuilder);

        createStructureAdvancement(consumer, builtAdvancements, "buried_treasure", BuiltinStructures.BURIED_TREASURE, -5.0f, 0.0f, rootBuilder);
        createStructureTagAdvancement(consumer, builtAdvancements, "mineshaft", StructureTags.MINESHAFT, -2.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "stronghold", BuiltinStructures.STRONGHOLD, -4.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "ancient_city", BuiltinStructures.ANCIENT_CITY, -8.0f, 0.0f, rootBuilder);

        createStructureAdvancement(consumer, builtAdvancements, "fortress", BuiltinStructures.FORTRESS, -2.0f, 3.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "bastion_remnant", BuiltinStructures.BASTION_REMNANT, -3.0f, 3.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "nether_fossil", BuiltinStructures.NETHER_FOSSIL, -4.0f, 3.0f, rootBuilder);
        createStructureTagAdvancement(consumer, builtAdvancements, "ruined_portal", StructureTags.RUINED_PORTAL, -5.0f, 3.0f, rootBuilder);

        createStructureAdvancement(consumer, builtAdvancements, "end_city", BuiltinStructures.END_CITY, -2.0f, -3.0f, rootBuilder);

        rootBuilder.save(consumer, "structureadvancements:structures/root");
    }

    private void createStructureAdvancement(
        Consumer<Advancement> consumer,
        Map<String, Advancement> builtAdvancements,
        String name,
        ResourceKey<Structure> structureKey,
        float x,
        float y,
        Advancement.Builder rootBuilder
    ) {
        String iconId = IconBindings.getIcon(MCVersion.V1_20, name);
        Item icon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(iconId));
        DisplayInfo displayInfo = new DisplayInfo(
            new ItemStack(icon),
            Component.literal(SmartStringBuilder.capitalize(name)),
            Component.literal(SmartStringBuilder.buildDescription("Explore", name.replace("_", " "))),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType(name)),
            true,
            true,
            false
        );
        displayInfo.setLocation(x, y);

        TagLocationTriggerConditions locationTrigger = new TagLocationTriggerConditions(new ResourceLocation("minecraft", "location"), structureKey.location().toString());

        Advancement advancement = Advancement.Builder.advancement()
            .parent(builtAdvancements.get(AdvancementHierarchy.getParent(name)))
            .display(displayInfo)
            .addCriterion("location", locationTrigger)
            .save(consumer, "structureadvancements:structures/" + name);
        builtAdvancements.put(name, advancement);

        rootBuilder.addCriterion(name, locationTrigger);
    }

    private void createStructureTagAdvancement(
        Consumer<Advancement> consumer,
        Map<String, Advancement> builtAdvancements,
        String name,
        TagKey<Structure> structureTag,
        float x,
        float y,
        Advancement.Builder rootBuilder
    ) {
        String iconId = IconBindings.getIcon(MCVersion.V1_20, name);
        Item icon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(iconId));
        DisplayInfo displayInfo = new DisplayInfo(
            new ItemStack(icon),
            Component.literal(SmartStringBuilder.capitalize(name)),
            Component.literal(SmartStringBuilder.buildDescription("Explore", name.replace("_", " "))),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType(name)),
            true,
            true,
            false
        );
        displayInfo.setLocation(x, y);

        TagLocationTriggerConditions locationTrigger = new TagLocationTriggerConditions(new ResourceLocation("minecraft", "location"), "#" + structureTag.location().toString());

        Advancement advancement = Advancement.Builder.advancement()
            .parent(builtAdvancements.get(AdvancementHierarchy.getParent(name)))
            .display(displayInfo)
            .addCriterion("location", locationTrigger)
            .save(consumer, "structureadvancements:structures/" + name);
        builtAdvancements.put(name, advancement);

        rootBuilder.addCriterion(name, locationTrigger);
    }

    public static class TagLocationTriggerConditions implements net.minecraft.advancements.CriterionTriggerInstance {
        private final ResourceLocation triggerId;
        private final String structureId;

        public TagLocationTriggerConditions(ResourceLocation triggerId, String structureId) {
            this.triggerId = triggerId;
            this.structureId = structureId;
        }

        @Override
        public ResourceLocation getCriterion() {
            return this.triggerId;
        }

        @Override
        public com.google.gson.JsonObject serializeToJson(net.minecraft.advancements.critereon.SerializationContext context) {
            com.google.gson.JsonObject json = new com.google.gson.JsonObject();
            com.google.gson.JsonObject location = new com.google.gson.JsonObject();
            location.addProperty("structure", this.structureId);
            json.add("location", location);
            return json;
        }
    }
}
