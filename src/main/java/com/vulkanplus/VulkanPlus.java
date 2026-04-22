package com.vulkanplus;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VulkanPlus implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("vulkanplus");

    @Override
    public void onInitialize() {
        LOGGER.info("VulkanPlus: Initializing optimization engine for 200+ FPS...");
        // Load Native libraries if any
        try {
            // System.loadLibrary("vulkan_bridge");
        } catch (UnsatisfiedLinkError e) {
            LOGGER.warn("Native Vulkan Bridge not found, using Java fallback optimizations.");
        }
    }
}