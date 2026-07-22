package com.aerohide.structureadvancements.client;

import com.aerohide.structureadvancements.common.MCVersion;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class StructureadvancementsDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider((FabricDataOutput output) -> new StructureAdvancementProvider(output,MCVersion.V1_16, "structureadvancements_1_16"));
        pack.addProvider((FabricDataOutput output) -> new StructureAdvancementProvider(output, MCVersion.V1_14, "structureadvancements_1_14"));
    }
}
