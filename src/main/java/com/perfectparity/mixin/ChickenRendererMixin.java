package com.perfectparity.mixin;

import com.google.common.collect.Maps;
import com.perfectparity.entity.utils.MobVariant;
import com.perfectparity.utils.interfaces.VariantMob;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ChickenRenderer.class)
public class ChickenRendererMixin {
    @Unique
    private static final Map<MobVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MobVariant.class), map -> {
                map.put(MobVariant.NORMAL,
                        ResourceLocation.withDefaultNamespace("textures/entity/chicken/temperate_chicken.png"));
                map.put(MobVariant.COLD,
                        ResourceLocation.withDefaultNamespace("textures/entity/chicken/cold_chicken.png"));
                map.put(MobVariant.WARM,
                        ResourceLocation.withDefaultNamespace("textures/entity/chicken/warm_chicken.png"));
            });

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Chicken;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void resourceLocation(Chicken chicken, CallbackInfoReturnable<ResourceLocation> cir) {
        try {
            cir.setReturnValue(LOCATION_BY_VARIANT.get(((VariantMob)chicken).projectParity$getVariant()));
        } catch (Exception e) {
            cir.setReturnValue(ResourceLocation.withDefaultNamespace("textures/entity/chicken/temperate_chicken.png"));
        }
    }
}
