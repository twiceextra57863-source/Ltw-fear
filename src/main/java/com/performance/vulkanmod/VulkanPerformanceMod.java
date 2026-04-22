package com.performance.vulkanmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public class VulkanPerformanceMod implements ModInitializer {
    public static final String MOD_ID = "vulkan-performance-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Vulkan Performance Mod...");
        loadNativeLibrary();
        try {
            boostPerformance();
            LOGGER.info("Native performance boost activated.");
        } catch (UnsatisfiedLinkError e) {
            LOGGER.warn("Native performance boost could not be initialized.");
        }
    }

    private void loadNativeLibrary() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        String libFileName;
        String resourceFolder;

        if (osName.contains("win")) {
            libFileName = "vulkan_performance.dll";
            resourceFolder = "x64";
        } else if (osName.contains("linux")) {
            libFileName = "libvulkan_performance.so";
            resourceFolder = arch.contains("aarch64") || arch.contains("arm") ? "arm64" : "x64";
        } else {
            return;
        }

        String resourcePath = "/nativelib/" + resourceFolder + "/" + libFileName;
        try (InputStream is = VulkanPerformanceMod.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.error("Native library not found in resources: " + resourcePath);
                return;
            }

            Path tempDir = FabricLoader.getInstance().getGameDir().resolve("vulkan-natives");
            Files.createDirectories(tempDir);
            Path tempLib = tempDir.resolve(libFileName);

            Files.copy(is, tempLib, StandardCopyOption.REPLACE_EXISTING);
            System.load(tempLib.toAbsolutePath().toString());
            LOGGER.info("Loaded native library: " + libFileName);
        } catch (Exception e) {
            LOGGER.error("Failed to load native library: " + e.getMessage());
        }
    }

    public native void boostPerformance();
}
