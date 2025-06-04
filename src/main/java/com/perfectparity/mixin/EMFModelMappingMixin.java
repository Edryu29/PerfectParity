package com.perfectparity.mixin;

import com.perfectparity.entity.utils.OptifineMapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.entity_model_features.models.EMFModelMappings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static traben.entity_model_features.models.EMFModelMappings.partMapping;

@Environment(EnvType.CLIENT)
@Mixin(value = EMFModelMappings.class, remap = false)
public class EMFModelMappingMixin {

    @Shadow
    public static Map<String, String> DEFAULT_TEXTURE_MAPPINGS;

    @Shadow
    private static String texture(String folder, String name) {
        return folder;
    }

    @Inject(at = @At("TAIL"), method = "initDefaultTextureMappings", remap = false)
    private static void init(CallbackInfo info) {
        Map<String, String> temp = new HashMap<>(DEFAULT_TEXTURE_MAPPINGS);

        temp.put("cow",            texture("cow",            "temperate_cow"));
        temp.put("warm_cow",       texture("cow",            "warm_cow"));
        temp.put("cold_cow",       texture("cow",            "cold_cow"));
        temp.put("pig",            texture("pig",            "temperate_pig"));
        temp.put("warm_pig",       texture("pig",            "warm_pig"));
        temp.put("cold_pig",       texture("pig",            "cold_pig"));
        temp.put("chicken",        texture("chicken",        "chicken"));
        temp.put("warm_chicken",   texture("chicken",        "warm_chicken"));
        temp.put("cold_chicken",   texture("chicken",        "cold_chicken"));

        DEFAULT_TEXTURE_MAPPINGS = Collections.unmodifiableMap(temp);
    }

    @Inject(at = @At("TAIL"), method = "initOptifineMappings", remap = false)
    private static void initOptifineMappings (CallbackInfo ci) {
        var genericQuadraped2 = Map.ofEntries(
                partMapping("head"),
                partMapping("body"),
                partMapping("leg1", "right_hind_leg"),
                partMapping("leg2", "left_hind_leg"),
                partMapping("leg3", "right_front_leg"),
                partMapping("leg4", "left_front_leg"));

        OptifineMapper.models("sheep", "cow", "warm_cow", "mooshroom", "panda", "pig", "cold_pig", "warm_pig", "pig_saddle", "polar_bear", "sheep_wool")
                .parts(genericQuadraped2);
        OptifineMapper.models("cold_cow")
                .parts( new HashMap<>(genericQuadraped2) {{
                    putAll(Map.ofEntries(
                            partMapping("right_horn"),
                            partMapping("left_horn")));
                }});

        OptifineMapper.models("chicken", "warm_chicken", "cold_chicken")
                .parts(Map.ofEntries(
                        partMapping("head"),
                        partMapping("body"),
                        partMapping("right_leg"),
                        partMapping("left_leg"),
                        partMapping("right_wing"),
                        partMapping("left_wing"),
                        partMapping("bill", "beak"),
                        partMapping("chin", "red_thing")

                ));
    }
}