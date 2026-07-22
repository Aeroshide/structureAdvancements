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

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.SerializationContext;
import com.google.gson.JsonObject;

public class StructureAdvancementProvider extends FabricAdvancementProvider {

    public StructureAdvancementProvider(FabricDataGenerator generator) {
        super(generator);
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

        String rootIconId = IconBindings.getIcon(MCVersion.V1_18_2, "root");
        Item rootIcon = Registry.ITEM.get(new ResourceLocation(rootIconId));
        DisplayInfo rootDisplay = new DisplayInfo(
            new ItemStack(rootIcon),
            new TextComponent(CommonStrings.getRootTitle()),
            new TextComponent(CommonStrings.getRootDescription()),
            new ResourceLocation("minecraft", "textures/block/amethyst_block.png"),
            mapFrameType(AdvancementHierarchy.getFrameType("root")),
            true,
            true,
            false
        );
        rootDisplay.setLocation(0.0f, 0.0f);

        Advancement rootPlaceholder = Advancement.Builder.advancement()
            .display(rootDisplay)
            .addCriterion("dummy", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY))
            .build(new ResourceLocation("structureadvancements", "structures/root"));
        builtAdvancements.put("root", rootPlaceholder);

        Advancement.Builder rootBuilder = Advancement.Builder.advancement()
            .display(rootDisplay);

        String overworldIconId = IconBindings.getIcon(MCVersion.V1_18_2, "overworld");
        Item overworldIcon = Registry.ITEM.get(new ResourceLocation(overworldIconId));
        DisplayInfo overworldDisplay = new DisplayInfo(
            new ItemStack(overworldIcon),
            new TextComponent(CommonStrings.getDimensionTitle(MCVersion.V1_18_2, "overworld")),
            new TextComponent(CommonStrings.getDimensionDescription(MCVersion.V1_18_2, "overworld")),
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
            .addCriterion("tick", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY))
            .save(consumer, "structureadvancements:structures/overworld");
        builtAdvancements.put("overworld", overworld);

        String netherIconId = IconBindings.getIcon(MCVersion.V1_18_2, "nether");
        Item netherIcon = Registry.ITEM.get(new ResourceLocation(netherIconId));
        DisplayInfo netherDisplay = new DisplayInfo(
            new ItemStack(netherIcon),
            new TextComponent(CommonStrings.getDimensionTitle(MCVersion.V1_18_2, "nether")),
            new TextComponent(CommonStrings.getDimensionDescription(MCVersion.V1_18_2, "nether")),
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
            .addCriterion("tick", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY))
            .save(consumer, "structureadvancements:structures/nether");
        builtAdvancements.put("nether", nether);

        String endIconId = IconBindings.getIcon(MCVersion.V1_18_2, "end");
        Item endIcon = Registry.ITEM.get(new ResourceLocation(endIconId));
        DisplayInfo endDisplay = new DisplayInfo(
            new ItemStack(endIcon),
            new TextComponent(CommonStrings.getDimensionTitle(MCVersion.V1_18_2, "end")),
            new TextComponent(CommonStrings.getDimensionDescription(MCVersion.V1_18_2, "end")),
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
            .addCriterion("tick", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY))
            .save(consumer, "structureadvancements:structures/the_end");
        builtAdvancements.put("the_end", end);

        createStructureAdvancement(consumer, builtAdvancements, "ocean_ruin", -8.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "shipwreck", -2.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "monument", -3.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "village", -3.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "desert_pyramid", -5.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "jungle_pyramid", -6.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "igloo", -7.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "swamp_hut", -7.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "mansion", -4.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "pillager_outpost", -6.0f, 0.0f, rootBuilder);

        createStructureAdvancement(consumer, builtAdvancements, "buried_treasure", -5.0f, 0.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "mineshaft", -2.0f, -1.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "stronghold", -4.0f, -1.0f, rootBuilder);

        createStructureAdvancement(consumer, builtAdvancements, "fortress", -2.0f, 3.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "bastion_remnant", -3.0f, 3.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "nether_fossil", -4.0f, 3.0f, rootBuilder);
        createStructureAdvancement(consumer, builtAdvancements, "ruined_portal", -5.0f, 3.0f, rootBuilder);

        createStructureAdvancement(consumer, builtAdvancements, "end_city", -2.0f, -3.0f, rootBuilder);

        rootBuilder.save(consumer, "structureadvancements:structures/root");
    }

    private void createStructureAdvancement(
        Consumer<Advancement> consumer,
        Map<String, Advancement> builtAdvancements,
        String name,
        float x,
        float y,
        Advancement.Builder rootBuilder
    ) {
        String iconId = IconBindings.getIcon(MCVersion.V1_18_2, name);
        Item icon = Registry.ITEM.get(new ResourceLocation(iconId));
        DisplayInfo displayInfo = new DisplayInfo(
            new ItemStack(icon),
            new TextComponent(SmartStringBuilder.capitalize(name)),
            new TextComponent(SmartStringBuilder.buildDescription("Explore", name.replace("_", " "))),
            null,
            mapFrameType(AdvancementHierarchy.getFrameType(name)),
            true,
            true,
            false
        );
        displayInfo.setLocation(x, y);

        String structureId = "minecraft:" + name;
        TagLocationTriggerConditions locationTrigger = new TagLocationTriggerConditions(new ResourceLocation("minecraft", "location"), structureId);

        Advancement advancement = Advancement.Builder.advancement()
            .parent(builtAdvancements.get(AdvancementHierarchy.getParent(name)))
            .display(displayInfo)
            .addCriterion("location", locationTrigger)
            .save(consumer, "structureadvancements:structures/" + name);
        builtAdvancements.put(name, advancement);

        rootBuilder.addCriterion(name, locationTrigger);
    }

    public static class TagLocationTriggerConditions implements CriterionTriggerInstance {
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
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = new JsonObject();
            JsonObject location = new JsonObject();
            location.addProperty("feature", this.structureId);
            json.add("location", location);
            return json;
        }
    }
}
