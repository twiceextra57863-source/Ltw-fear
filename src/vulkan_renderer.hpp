#pragma once
#define VK_USE_PLATFORM_ANDROID_KHR
#include <vulkan/vulkan.h>
#include <vector>
#include <iostream>

class VulkanRenderer {
public:
    void initVulkan();
    void cleanup();
private:
    VkInstance instance;
    VkPhysicalDevice physicalDevice = VK_NULL_HANDLE;
    VkDevice device;
    VkQueue graphicsQueue;
    void createInstance();
    void pickPhysicalDevice();
    void createLogicalDevice();
};