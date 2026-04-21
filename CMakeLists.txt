cmake_minimum_required(VERSION 3.10)
project(VulkanBoostNative)

set(CMAKE_CXX_STANDARD 17)

find_package(Vulkan REQUIRED)

include_directories(src/main/cpp/include ${Vulkan_INCLUDE_DIRS})

add_library(vulkanboost SHARED
    src/main/cpp/src/vulkan_renderer.cpp
    src/main/cpp/src/jni_bridge.cpp
)