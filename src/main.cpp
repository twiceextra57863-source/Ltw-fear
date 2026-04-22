#include "vulkan_renderer.hpp"

int main() {
    VulkanRenderer renderer;
    try {
        renderer.initVulkan();
        std::cout << "Ltw-fear Engine Running..." << std::endl;
        renderer.cleanup();
    } catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return 1;
    }
    return 0;
}