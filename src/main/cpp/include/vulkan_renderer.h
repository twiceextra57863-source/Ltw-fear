#ifndef VULKAN_RENDERER_H
#define VULKAN_RENDERER_H

#include <vulkan/vulkan.h>
#include <vector>
#include <stdexcept>

// Vulkan core objects
extern VkInstance instance;
extern VkPhysicalDevice physicalDevice;
extern VkDevice device;
extern VkQueue graphicsQueue;

// Functions
bool isVulkanSupported();
void initVulkan();
void renderFrameVulkan();
void cleanupVulkan();

// Helper functions
VkPhysicalDevice pickPhysicalDevice(VkInstance instance);
uint32_t findGraphicsQueueFamily(VkPhysicalDevice device);
void createLogicalDevice(VkPhysicalDevice physicalDevice, VkDevice* device, VkQueue* graphicsQueue);

#endif // VULKAN_RENDERER_H
