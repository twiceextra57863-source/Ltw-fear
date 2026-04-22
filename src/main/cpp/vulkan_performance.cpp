#include <jni.h>
#include <iostream>

#ifdef _WIN32
#include <windows.h>
#else
#include <sys/resource.h>
#include <unistd.h>
#include <sched.h>
#endif

extern "C" {
    JNIEXPORT void JNICALL Java_com_performance_vulkanmod_VulkanPerformanceMod_boostPerformance(JNIEnv* env, jobject obj) {
        #ifdef _WIN32
            // Set process priority to High on Windows to reduce micro-stutters
            if (SetPriorityClass(GetCurrentProcess(), HIGH_PRIORITY_CLASS)) {
                std::cout << "[VulkanMod] Windows Process priority set to HIGH" << std::endl;
            }

            // Set main thread priority to Highest
            if (SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_HIGHEST)) {
                std::cout << "[VulkanMod] Windows Main thread priority set to HIGHEST" << std::endl;
            }
        #else
            // Set process niceness to -10 on Linux/Android for better scheduling
            if (setpriority(PRIO_PROCESS, 0, -10) == 0) {
                std::cout << "[VulkanMod] Unix/Android Process niceness set to -10" << std::endl;
            }

            // Attempt to set real-time scheduling for the main render thread
            struct sched_param param;
            param.sched_priority = sched_get_priority_max(SCHED_RR);
            if (sched_setscheduler(0, SCHED_RR, &param) == 0) {
                std::cout << "[VulkanMod] Unix/Android Thread scheduled with SCHED_RR" << std::endl;
            }
        #endif

        // Final optimization: signal the game that native components are ready
        std::cout << "[VulkanMod] Native Performance Engine Initialized Successfully." << std::endl;
    }
}
