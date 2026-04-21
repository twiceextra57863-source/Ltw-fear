#ifndef JNI_BRIDGE_H
#define JNI_BRIDGE_H

#include <jni.h>

// JNI function declarations for VulkanRenderer
extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_isVulkanSupported(JNIEnv *env, jclass clazz);

JNIEXPORT void JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_init(JNIEnv *env, jclass clazz);

JNIEXPORT void JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_renderFrame(JNIEnv *env, jclass clazz);

JNIEXPORT void JNICALL
Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_cleanup(JNIEnv *env, jclass clazz);

}

#endif // JNI_BRIDGE_H
