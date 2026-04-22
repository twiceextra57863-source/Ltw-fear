cmake_minimum_required(VERSION 3.10)
project(LtwFearVulkan)

set(CMAKE_CXX_STANDARD 17)
find_package(Vulkan REQUIRED)
find_package(glfw3 REQUIRED)

add_executable(LtwFear
    src/main.cpp
    src/vulkan_renderer.cpp
)

target_link_libraries(LtwFear Vulkan::Vulkan glfw)
target_include_directories(LtwFear PRIVATE ${VULKAN_INCLUDE_DIRS})