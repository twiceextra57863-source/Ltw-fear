#include "jni_bridge.h"
#include "vulkan_renderer.h"

VulkanRenderer renderer;

JNIEXPORT void JNICALL Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_initVulkan(JNIEnv *env, jobject obj) {
    renderer.init();
}