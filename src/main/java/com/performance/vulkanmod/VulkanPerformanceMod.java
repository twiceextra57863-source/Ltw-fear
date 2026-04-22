package com.performance.vulkanmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VulkanPerformanceMod implements ModInitializer {
    public static final String MOD_ID = "vulkan-performance-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Vulkan Performance Mod...");
        try {
            System.loadLibrary("vulkan_performance_native");
            LOGGER.info("Native library loaded successfully.");
        } catch (UnsatisfiedLinkError e) {
            LOGGER.error("Failed to load native library: " + e.getMessage());
        }
    }

    public native void boostPerformance();
}
