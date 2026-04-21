package com.mephistopheles.vulkanboost.vulkan;

public class VulkanRenderer {
    static {
        NativeLibraryLoader.loadLibrary();
    }

    // Check if Vulkan is supported
    public static native boolean isVulkanSupported();

    // Native Methods (C++ Implementation)
    public static native void init();
    public static native void renderFrame();
    public static native void cleanup();
}
