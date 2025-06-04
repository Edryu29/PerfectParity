package com.perfectparity.entity.models;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation COLD_COW = registerLayer("cold_cow");
    public static final ModelLayerLocation WARM_COW = registerLayer("warm_cow");

    public static final ModelLayerLocation COLD_CHICKEN = registerLayer("cold_chicken");

    public static final ModelLayerLocation COLD_PIG = registerLayer("cold_pig");

    private static ModelLayerLocation registerLayer(String name) {
        return new ModelLayerLocation(ResourceLocation.withDefaultNamespace(name), "main");
    }
}
