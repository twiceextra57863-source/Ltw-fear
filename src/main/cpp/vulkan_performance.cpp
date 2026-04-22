#include <jni.h>
#include <iostream>

#ifdef _WIN32
#include <windows.h>
#else
#include <sys/resource.h>
#include <unistd.h>
#include <sched.h>
#endif

#ifdef __ANDROID__
#include <android/log.h>
#define LOG_TAG "VulkanMod"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#else
#define LOGI(...) std::cout << "[VulkanMod] " << __VA_ARGS__ << std::endl
#endif

extern "C" {
    JNIEXPORT void JNICALL Java_com_performance_vulkanmod_VulkanPerformanceMod_boostPerformance(JNIEnv* env, jobject obj) {
        #ifdef _WIN32
            if (SetPriorityClass(GetCurrentProcess(), HIGH_PRIORITY_CLASS)) {
                LOGI("Windows Process priority set to HIGH");
            }
            if (SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_HIGHEST)) {
                LOGI("Windows Main thread priority set to HIGHEST");
            }
        #else
            if (setpriority(PRIO_PROCESS, 0, -10) == 0) {
                LOGI("Unix/Android Process niceness set to -10");
            }
            struct sched_param param;
            param.sched_priority = sched_get_priority_max(SCHED_RR);
            if (sched_setscheduler(0, SCHED_RR, &param) == 0) {
                LOGI("Unix/Android Thread scheduled with SCHED_RR");
            }
        #endif
        LOGI("Native Performance Engine Initialized.");
    }
}
