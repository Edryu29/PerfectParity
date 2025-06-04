package com.perfectparity.entity.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import traben.entity_model_features.models.EMFModelMappings;
import traben.entity_model_features.utils.EMFUtils;

import java.util.Map;

public class OptifineMapper {
    String[] modelNames;

    OptifineMapper(String... modelNames) {
        this.modelNames = modelNames;
    }

    @Contract(
            value = "_ -> new",
            pure = true
    )
    public static @NotNull OptifineMapper models(String... modelNames) {
        return new OptifineMapper(modelNames);
    }

    public void parts(Map<String, String> stringStringMap) {
        for(String key : this.modelNames) {
            if (EMFModelMappings.OPTIFINE_MODEL_MAP_CACHE.put(key, stringStringMap) != null) {
                EMFUtils.logError("OptiFine model map for " + key + " already exists, overwriting");
            }
        }

    }
}
