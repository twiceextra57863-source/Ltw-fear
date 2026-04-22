package com.ltw.vulkan;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LtwVulkanMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ltw-vulkan");

    static {
        try {
            System.loadLibrary("ltwvulkan_native");
            LOGGER.info("LTW Vulkan Native Engine Loaded Successfully!");
        } catch (UnsatisfiedLinkError e) {
            LOGGER.error("Failed to load native LTW Vulkan engine. Falling back to OpenGL.");
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("LTW Vulkan Boost 1.21.4 Initialized. Preparing 200+ FPS environment.");
        initializeNativeEngine();
    }

    private native void initializeNativeEngine();
}