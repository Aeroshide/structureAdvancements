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
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.Optional;

public class StructureAdvancementProvider extends FabricAdvancementProvider {

    public StructureAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private static ResourceKey<Structure> key(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }

    private static TagKey<Structure> tag(String name) {
        return TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }

    private AdvancementType mapFrameType(CommonFrameType commonFrameType) {
        switch (commonFrameType) {
            case CHALLENGE: return AdvancementType.CHALLENGE;
            case GOAL: return AdvancementType.GOAL;
            case TASK:
            default: return AdvancementType.TASK;
        }
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> exporter) {
        Map<String, AdvancementHolder> builtAdvancements = new HashMap<>();

        String rootIconId = IconBindings.getIcon(MCVersion.V26_2, "root");
        Item rootIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(rootIconId));
        DisplayInfo rootDisplay = new DisplayInfo(
                new ItemStack(rootIcon),
                Component.literal(CommonStrings.getRootTitle()),
                Component.literal(CommonStrings.getRootDescription()),
                Optional.of(ResourceLocation.withDefaultNamespace("block/amethyst_block")),
                mapFrameType(AdvancementHierarchy.getFrameType("root")),
                true,
                true,
                false
        );
        rootDisplay.setLocation(0.0f, 0.0f);

        AdvancementHolder rootPlaceholder = Advancement.Builder.advancement()
                .display(rootDisplay)
                .addCriterion("dummy", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                .build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/root"));
        builtAdvancements.put("root", rootPlaceholder);

        Advancement.Builder rootBuilder = Advancement.Builder.advancement()
                .display(rootDisplay);

        String overworldIconId = IconBindings.getIcon(MCVersion.V26_2, "overworld");
        Item overworldIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(overworldIconId));
        DisplayInfo overworldDisplay = new DisplayInfo(
                new ItemStack(overworldIcon),
                Component.literal(CommonStrings.getDimensionTitle(MCVersion.V26_2, "overworld")),
                Component.literal(CommonStrings.getDimensionDescription(MCVersion.V26_2, "overworld")),
                Optional.empty(),
                mapFrameType(AdvancementHierarchy.getFrameType("overworld")),
                true,
                false,
                false
        );
        overworldDisplay.setLocation(-1.0f, 0.0f);

        AdvancementHolder overworld = Advancement.Builder.advancement()
                .parent(rootPlaceholder)
                .display(overworldDisplay)
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/overworld"));
        exporter.accept(overworld);
        builtAdvancements.put("overworld", overworld);

        String netherIconId = IconBindings.getIcon(MCVersion.V26_2, "nether");
        Item netherIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(netherIconId));
        DisplayInfo netherDisplay = new DisplayInfo(
                new ItemStack(netherIcon),
                Component.literal(CommonStrings.getDimensionTitle(MCVersion.V26_2, "nether")),
                Component.literal(CommonStrings.getDimensionDescription(MCVersion.V26_2, "nether")),
                Optional.empty(),
                mapFrameType(AdvancementHierarchy.getFrameType("nether")),
                true,
                false,
                false
        );
        netherDisplay.setLocation(-1.0f, 3.0f);

        AdvancementHolder nether = Advancement.Builder.advancement()
                .parent(rootPlaceholder)
                .display(netherDisplay)
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/nether"));
        exporter.accept(nether);
        builtAdvancements.put("nether", nether);

        String endIconId = IconBindings.getIcon(MCVersion.V26_2, "end");
        Item endIcon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(endIconId));
        DisplayInfo endDisplay = new DisplayInfo(
                new ItemStack(endIcon),
                Component.literal(CommonStrings.getDimensionTitle(MCVersion.V26_2, "end")),
                Component.literal(CommonStrings.getDimensionDescription(MCVersion.V26_2, "end")),
                Optional.empty(),
                mapFrameType(AdvancementHierarchy.getFrameType("the_end")),
                true,
                false,
                false
        );
        endDisplay.setLocation(-1.0f, -3.0f);

        AdvancementHolder end = Advancement.Builder.advancement()
                .parent(rootPlaceholder)
                .display(endDisplay)
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/the_end"));
        exporter.accept(end);
        builtAdvancements.put("the_end", end);

        addStructureAdvancement(registries, exporter, builtAdvancements, "ocean_ruin", tag("ocean_ruin"), -8.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "shipwreck", tag("shipwreck"), -2.0f, 0.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "monument", key("monument"), -3.0f, 0.0f, rootBuilder);

        addStructureAdvancement(registries, exporter, builtAdvancements, "village", tag("village"), -3.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "desert_pyramid", key("desert_pyramid"), -5.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "jungle_pyramid", key("jungle_pyramid"), -6.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "igloo", key("igloo"), -7.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "swamp_hut", key("swamp_hut"), -7.0f, 0.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "mansion", key("mansion"), -4.0f, 0.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "pillager_outpost", key("pillager_outpost"), -6.0f, 0.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "trail_ruins", key("trail_ruins"), -2.0f, 1.0f, rootBuilder);

        addStructureAdvancement(registries, exporter, builtAdvancements, "buried_treasure", key("buried_treasure"), -5.0f, 0.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "mineshaft", tag("mineshaft"), -2.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "stronghold", key("stronghold"), -4.0f, -1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "trial_chambers", key("trial_chambers"), -3.0f, 1.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "ancient_city", key("ancient_city"), -8.0f, 0.0f, rootBuilder);

        addStructureAdvancement(registries, exporter, builtAdvancements, "fortress", key("fortress"), -2.0f, 3.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "bastion_remnant", key("bastion_remnant"), -3.0f, 3.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "nether_fossil", key("nether_fossil"), -4.0f, 3.0f, rootBuilder);
        addStructureAdvancement(registries, exporter, builtAdvancements, "ruined_portal", tag("ruined_portal"), -5.0f, 3.0f, rootBuilder);

        addStructureAdvancement(registries, exporter, builtAdvancements, "end_city", key("end_city"), -2.0f, -3.0f, rootBuilder);

        exporter.accept(rootBuilder.build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/root")));
    }

    private void addStructureAdvancement(
            HolderLookup.Provider registries,
            Consumer<AdvancementHolder> exporter,
            Map<String, AdvancementHolder> builtAdvancements,
            String name,
            ResourceKey<Structure> structureKey,
            float x,
            float y,
            Advancement.Builder rootBuilder
    ) {
        String iconId = IconBindings.getIcon(MCVersion.V26_2, name);
        Item icon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(iconId));
        String capitalizedName = SmartStringBuilder.capitalize(name);
        DisplayInfo display = new DisplayInfo(
                new ItemStack(icon),
                Component.literal(capitalizedName),
                Component.literal(SmartStringBuilder.buildDescription("Enter", capitalizedName)),
                Optional.empty(),
                mapFrameType(AdvancementHierarchy.getFrameType(name)),
                true,
                true,
                false
        );
        display.setLocation(x, y);

        Criterion<PlayerTrigger.TriggerInstance> trigger = PlayerTrigger.TriggerInstance.located(
                LocationPredicate.Builder.location().inStructure(
                        registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(structureKey)
                )
        );

        AdvancementHolder holder = Advancement.Builder.advancement()
                .parent(builtAdvancements.get(AdvancementHierarchy.getParent(name)))
                .display(display)
                .addCriterion("has_structure", trigger)
                .build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/" + name));
        exporter.accept(holder);
        builtAdvancements.put(name, holder);

        rootBuilder.addCriterion(name, trigger);
    }

    private void addStructureAdvancement(
            HolderLookup.Provider registries,
            Consumer<AdvancementHolder> exporter,
            Map<String, AdvancementHolder> builtAdvancements,
            String name,
            TagKey<Structure> tagKey,
            float x,
            float y,
            Advancement.Builder rootBuilder
    ) {
        String iconId = IconBindings.getIcon(MCVersion.V26_2, name);
        Item icon = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(iconId));
        String capitalizedName = SmartStringBuilder.capitalize(name);
        DisplayInfo display = new DisplayInfo(
                new ItemStack(icon),
                Component.literal(capitalizedName),
                Component.literal(SmartStringBuilder.buildDescription("Enter", capitalizedName)),
                Optional.empty(),
                mapFrameType(AdvancementHierarchy.getFrameType(name)),
                true,
                true,
                false
        );
        display.setLocation(x, y);

        Criterion<PlayerTrigger.TriggerInstance> trigger = PlayerTrigger.TriggerInstance.located(
                LocationPredicate.Builder.location().setStructures(
                        registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(tagKey)
                )
        );

        AdvancementHolder holder = Advancement.Builder.advancement()
                .parent(builtAdvancements.get(AdvancementHierarchy.getParent(name)))
                .display(display)
                .addCriterion("has_structure", trigger)
                .build(ResourceLocation.fromNamespaceAndPath("structureadvancements", "structures/" + name));
        exporter.accept(holder);
        builtAdvancements.put(name, holder);

        rootBuilder.addCriterion(name, trigger);
    }
}
