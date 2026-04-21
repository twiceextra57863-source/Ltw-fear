package com.mephistopheles.vulkanboost.vulkan;

public class VulkanRenderer {
    public native void initVulkan();
    public native void render();
    public native void cleanup();
}