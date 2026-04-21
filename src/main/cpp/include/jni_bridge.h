#include <jni.h>

#ifndef JNI_BRIDGE_H
#define JNI_BRIDGE_H

extern "C" {
    JNIEXPORT void JNICALL Java_com_mephistopheles_vulkanboost_vulkan_VulkanRenderer_initVulkan(JNIEnv *, jobject);
}

#endif