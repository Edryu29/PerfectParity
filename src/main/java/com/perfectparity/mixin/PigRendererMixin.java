package com.perfectparity.mixin;

import com.google.common.collect.Maps;
import com.perfectparity.entity.utils.MobVariant;
import com.perfectparity.utils.interfaces.VariantMob;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PigRenderer.class)
public class PigRendererMixin {
    @Unique
    private static final Map<MobVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MobVariant.class), map -> {
                map.put(MobVariant.NORMAL,
                        ResourceLocation.withDefaultNamespace("textures/entity/pig/temperate_pig.png"));
                map.put(MobVariant.COLD,
                        ResourceLocation.withDefaultNamespace("textures/entity/pig/cold_pig.png"));
                map.put(MobVariant.WARM,
                        ResourceLocation.withDefaultNamespace("textures/entity/pig/warm_pig.png"));
            });

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Pig;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void resourceLocation(Pig pig, CallbackInfoReturnable<ResourceLocation> cir) {
        try {
            cir.setReturnValue(LOCATION_BY_VARIANT.get(((VariantMob)pig).projectParity$getVariant()));
        } catch (Exception e) {
            cir.setReturnValue(ResourceLocation.withDefaultNamespace("textures/entity/pig/temperate_pig.png"));
        }
    }
}
