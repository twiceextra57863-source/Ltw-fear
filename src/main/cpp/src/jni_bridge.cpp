#include <jni.h>
#include "vulkan_renderer.h"

extern "C" JNIEXPORT jboolean JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_isVulkanSupported(JNIEnv *env, jclass clazz) {
    return isVulkanSupported();
}

extern "C" JNIEXPORT void JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_init(JNIEnv *env, jclass clazz) {
    initVulkan();
}

extern "C" JNIEXPORT void JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_renderFrame(JNIEnv *env, jclass clazz) {
    renderFrameVulkan();
}

extern "C" JNIEXPORT void JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_cleanup(JNIEnv *env, jclass clazz) {
    cleanupVulkan();
}
