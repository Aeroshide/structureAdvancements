package com.aerohide.structureadvancements.client;

import com.aerohide.structureadvancements.common.MCVersion;
import com.aerohide.structureadvancements.common.CommonFrameType;
import com.aerohide.structureadvancements.common.CommonStrings;
import com.aerohide.structureadvancements.common.IconBindings;
import com.aerohide.structureadvancements.common.SmartStringBuilder;
import com.aerohide.structureadvancements.common.AdvancementHierarchy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import com.google.gson.JsonObject;

public class StructureAdvancementProvider extends FabricAdvancementProvider {

    private final MCVersion version;
    private final String namespace;

    private static final Map<String, String> LEGACY_FEATURE_NAMES = new HashMap<>();
    static {
        LEGACY_FEATURE_NAMES.put("mineshaft", "Mineshaft");
        LEGACY_FEATURE_NAMES.put("village", "Village");
        LEGACY_FEATURE_NAMES.put("stronghold", "Stronghold");
        LEGACY_FEATURE_NAMES.put("fortress", "Nether_Fortress");
        LEGACY_FEATURE_NAMES.put("desert_pyramid", "Desert_Pyramid");
        LEGACY_FEATURE_NAMES.put("jungle_pyramid", "Jungle_Pyramid");
        LEGACY_FEATURE_NAMES.put("igloo", "Igloo");
        LEGACY_FEATURE_NAMES.put("swamp_hut", "Swamp_Hut");
        LEGACY_FEATURE_NAMES.put("ocean_ruin", "Ocean_Ruin");
        LEGACY_FEATURE_NAMES.put("shipwreck", "Shipwreck");
        LEGACY_FEATURE_NAMES.put("monument", "Monument");
        LEGACY_FEATURE_NAMES.put("mansion", "Mansion");
        LEGACY_FEATURE_NAMES.put("buried_treasure", "Buried_Treasure");
        LEGACY_FEATURE_NAMES.put("pillager_outpost", "Pillager_Outpost");
        LEGACY_FEATURE_NAMES.put("end_city", "EndCity");
        LEGACY_FEATURE_NAMES.put("ruined_portal", "Ruined_Portal");
        LEGACY_FEATURE_NAMES.put("bastion_remnant", "Bastion_Remnant");
        LEGACY_FEATURE_NAMES.put("nether_fossil", "Nether_Fossil");
    }

    public StructureAdvancementProvider(FabricDataOutput output, MCVersion version, String namespace) {
        super(output);
        this.version = version;
        this.namespace = namespace;
    }

    @Override
    public String getName() {
        return "Advancements_" + namespace;
    }

    private FrameType mapFrameType(CommonFrameType commonFrameType) {
        return switch (commonFrameType) {
            case CHALLENGE -> FrameType.CHALLENGE;
            case GOAL -> FrameType.GOAL;
            case TASK -> FrameType.TASK;
        };
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Map<String, Advancement> built = new HashMap<>();

        Item rootIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(IconBindings.getIcon(version, "root")));
        DisplayInfo rootDisplay = new DisplayInfo(
            new ItemStack(rootIcon),
            Component.literal(CommonStrings.getRootTitle()),
            Component.literal(CommonStrings.getRootDescription()),
            new ResourceLocation("textures/block/purpur_block.png"),
            mapFrameType(AdvancementHierarchy.getFrameType("root")),
            true, true, false
        );
        rootDisplay.setLocation(0.0f, 0.0f);

        Advancement rootPlaceholder = Advancement.Builder.advancement()
            .display(rootDisplay)
            .addCriterion("dummy", PlayerTrigger.TriggerInstance.tick())
            .build(new ResourceLocation(namespace, "structures/root"));
        built.put("root", rootPlaceholder);

        Advancement.Builder rootBuilder = Advancement.Builder.advancement()
            .display(rootDisplay);

        buildDimension(consumer, built, rootPlaceholder, rootBuilder, "overworld", -1.0f, 0.0f);
        buildDimension(consumer, built, rootPlaceholder, rootBuilder, "nether", -1.0f, 3.0f);
        buildDimension(consumer, built, rootPlaceholder, rootBuilder, "end", -1.0f, -3.0f);

        createStructureAdvancement(consumer, built, "ocean_ruin", -8.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "shipwreck", -2.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "monument", -3.0f, 0.0f, rootBuilder);

        createStructureAdvancement(consumer, built, "village", -3.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "desert_pyramid", -5.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "jungle_pyramid", -6.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "igloo", -7.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "swamp_hut", -7.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "mansion", -4.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "pillager_outpost", -6.0f, 0.0f, rootBuilder);

        createStructureAdvancement(consumer, built, "buried_treasure", -5.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "mineshaft", -2.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, built, "stronghold", -4.0f, -1.0f, rootBuilder);

        createStructureAdvancement(consumer, built, "fortress", -2.0f, 3.0f, rootBuilder);
        if (AdvancementHierarchy.existsInVersion(AdvancementHierarchy.STRUCTURE.BASTION_REMNANT, version)) {
            createStructureAdvancement(consumer, built, "bastion_remnant", -3.0f, 3.0f, rootBuilder);
        }
        if (AdvancementHierarchy.existsInVersion(AdvancementHierarchy.STRUCTURE.NETHER_FOSSIL, version)) {
            createStructureAdvancement(consumer, built, "nether_fossil", -4.0f, 3.0f, rootBuilder);
        }
        if (AdvancementHierarchy.existsInVersion(AdvancementHierarchy.STRUCTURE.RUINED_PORTAL, version)) {
            createStructureAdvancement(consumer, built, "ruined_portal", -5.0f, 3.0f, rootBuilder);
        }

        createStructureAdvancement(consumer, built, "end_city", -2.0f, -3.0f, rootBuilder);

        rootBuilder.save(consumer, namespace + ":structures/root");
    }

    private void buildDimension(Consumer<Advancement> consumer, Map<String, Advancement> built, Advancement root, Advancement.Builder rootBuilder, String dimension, float x, float y) {
        String dimKey = dimension.equals("end") ? "the_end" : dimension;
        Item icon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(IconBindings.getIcon(version, dimension)));
        DisplayInfo display = new DisplayInfo(
            new ItemStack(icon),
            Component.literal(CommonStrings.getDimensionTitle(version, dimension)),
            Component.literal(CommonStrings.getDimensionDescription(version, dimension)),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType(dimKey)),
            true, false, false
        );
        display.setLocation(x, y);

        Advancement adv = Advancement.Builder.advancement()
            .parent(root)
            .display(display)
            .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
            .save(consumer, namespace + ":structures/" + dimKey);
        built.put(dimKey, adv);
    }

    private void createStructureAdvancement(Consumer<Advancement> consumer, Map<String, Advancement> built, String name, float x, float y, Advancement.Builder rootBuilder) {
        Item icon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(IconBindings.getIcon(version, name)));
        DisplayInfo displayInfo = new DisplayInfo(
            new ItemStack(icon),
            Component.literal(SmartStringBuilder.capitalize(name)),
            Component.literal(SmartStringBuilder.buildDescription("Explore", name.replace("_", " "))),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType(name)),
            true, true, false
        );
        displayInfo.setLocation(x, y);

        String featureName = name.equals("end_city") ? "endcity" : name;
        FeatureLocationTrigger locationTrigger = new FeatureLocationTrigger(new ResourceLocation("minecraft", "location"), featureName, version);

        Advancement advancement = Advancement.Builder.advancement()
            .parent(built.get(AdvancementHierarchy.getParent(name)))
            .display(displayInfo)
            .addCriterion("location", locationTrigger)
            .save(consumer, namespace + ":structures/" + name);
        built.put(name, advancement);

        rootBuilder.addCriterion(name, locationTrigger);
    }

    public static class FeatureLocationTrigger implements net.minecraft.advancements.CriterionTriggerInstance {
        private final ResourceLocation triggerId;
        private final String featureName;
        private final MCVersion version;

        public FeatureLocationTrigger(ResourceLocation triggerId, String featureName, MCVersion version) {
            this.triggerId = triggerId;
            this.featureName = featureName;
            this.version = version;
        }

        @Override
        public ResourceLocation getCriterion() {
            return this.triggerId;
        }

        @Override
        public JsonObject serializeToJson(net.minecraft.advancements.critereon.SerializationContext context) {
            JsonObject json = new JsonObject();
            if (this.version == MCVersion.V1_16) {
                JsonObject location = new JsonObject();
                location.addProperty("feature", this.featureName);
                json.add("location", location);
            } else {
                json.addProperty("feature", this.featureName);
            }
            return json;
        }
    }
}
