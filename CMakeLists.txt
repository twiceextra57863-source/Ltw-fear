cmake_minimum_required(VERSION 3.10)
project(VulkanBoost)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find Vulkan
find_package(Vulkan REQUIRED)

# Find JNI
find_package(JNI REQUIRED)

# Source Files
set(SOURCES
    src/cpp/src/vulkan_renderer.cpp
    src/cpp/src/jni_bridge.cpp
)

# Include Directories
include_directories(
    src/cpp/include
    ${JNI_INCLUDE_DIRS}
    ${Vulkan_INCLUDE_DIRS}
)

# Link Libraries
add_library(vulkan_boost SHARED ${SOURCES})
target_link_libraries(vulkan_boost PRIVATE
    Vulkan::Vulkan
    ${JNI_LIBRARIES}
)

# Output Directory
set_target_properties(vulkan_boost PROPERTIES
    LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/native
)
