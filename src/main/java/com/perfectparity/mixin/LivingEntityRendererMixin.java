package com.perfectparity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.perfectparity.entity.models.ModModelLayers;
import com.perfectparity.entity.utils.MobVariant;
import com.perfectparity.utils.interfaces.VariantMob;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow
    protected M model;

    @Unique
    public Map<MobVariant, CowModel<Cow>> COW_VARIANT_MODELS = new HashMap<>();

    @Unique
    public Map<MobVariant, PigModel<Pig>> PIG_VARIANT_MODELS = new HashMap<>();

    @Unique
    public Map<MobVariant, ChickenModel<Chicken>> CHICKEN_VARIANT_MODELS = new HashMap<>();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityRendererProvider.Context context, EntityModel entityModel, float f, CallbackInfo ci) {
        this.COW_VARIANT_MODELS = bakeCowModels(context);
        this.PIG_VARIANT_MODELS = bakePigModels(context);
        this.CHICKEN_VARIANT_MODELS = bakeChickenModels(context);
    }

    @Unique
    private static Map<MobVariant, CowModel<Cow>> bakeCowModels(EntityRendererProvider.Context context) {
        EnumMap<MobVariant, CowModel<Cow>> map = new EnumMap<>(MobVariant.class);
        map.put(MobVariant.NORMAL,
                new CowModel<>(context.bakeLayer(ModelLayers.COW)));
        map.put(MobVariant.WARM,
                new CowModel<>(context.bakeLayer(ModModelLayers.WARM_COW)));
        map.put(MobVariant.COLD,
                new CowModel<>(context.bakeLayer(ModModelLayers.COLD_COW)));
        return map;
    }

    @Unique
    private static Map<MobVariant, PigModel<Pig>> bakePigModels(EntityRendererProvider.Context context) {
        EnumMap<MobVariant, PigModel<Pig>> map = new EnumMap<>(MobVariant.class);
        map.put(MobVariant.NORMAL,
                new PigModel<>(context.bakeLayer(ModelLayers.PIG))
        );
        map.put(MobVariant.WARM,
                new PigModel<>(context.bakeLayer(ModelLayers.PIG))
        );
        map.put(MobVariant.COLD,
                new PigModel<>(context.bakeLayer(ModModelLayers.COLD_PIG)));
        return map;
    }

    @Unique
    private static Map<MobVariant, ChickenModel<Chicken>> bakeChickenModels(EntityRendererProvider.Context context) {
        EnumMap<MobVariant, ChickenModel<Chicken>> map = new EnumMap<>(MobVariant.class);
        map.put(MobVariant.NORMAL,
                new ChickenModel<>(context.bakeLayer(ModelLayers.CHICKEN)));
        map.put(MobVariant.WARM,
                new ChickenModel<>(context.bakeLayer(ModelLayers.CHICKEN)));
        map.put(MobVariant.COLD,
                new ChickenModel<>(context.bakeLayer(ModModelLayers.COLD_CHICKEN)));
        return map;
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void render(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (livingEntity instanceof Cow) {
            if (((VariantMob)livingEntity).projectParity$getVariant() != null) {
                this.model = (M) (this.COW_VARIANT_MODELS.get(((VariantMob)livingEntity).projectParity$getVariant()));
            }
        }

        if (livingEntity instanceof Pig) {
            if (((VariantMob)livingEntity).projectParity$getVariant() != null) {
                this.model = (M) (this.PIG_VARIANT_MODELS.get(((VariantMob)livingEntity).projectParity$getVariant()));
            }
        }

        if (livingEntity instanceof Chicken) {
            if (((VariantMob)livingEntity).projectParity$getVariant() != null) {
                this.model = (M) (this.CHICKEN_VARIANT_MODELS.get(((VariantMob)livingEntity).projectParity$getVariant()));
            }
        }
    }

}
