package com.mephistopheles.vulkanboost;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VulkanBoostMod implements ClientModInitializer {
    static {
        // Load C++ Native Library (Vulkan)
        NativeLibraryLoader.loadLibrary();
    }

    @Override
    public void onInitializeClient() {
        // Initialize Vulkan Renderer (if Vulkan is available)
        if (VulkanRenderer.isVulkanSupported()) {
            VulkanRenderer.init();
        }

        // Initialize OpenGL/LTW Optimizations
        OpenGLOptimizer.init();
    }
}
