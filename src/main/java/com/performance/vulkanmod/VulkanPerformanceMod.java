package com.performance.vulkanmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class VulkanPerformanceMod implements ModInitializer {
    public static final String MOD_ID = "vulkan-performance-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Vulkan Performance Mod Ultimate...");

        loadNativeLibrary();

        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (osName.contains("linux") || osName.contains("android")) {
            runSystemBoost();
        }

        try {
            boostPerformance();
            LOGGER.info("Vulkan Native Optimization Engine is ACTIVE!");
        } catch (Throwable t) {
            LOGGER.warn("Native optimization engine skipped or failed: " + t.getMessage());
        }
    }

    private void runSystemBoost() {
        try {
            Path boostScript = Paths.get(System.getProperty("java.io.tmpdir")).resolve("vulkan_boost.sh");

            try (InputStream is = VulkanPerformanceMod.class.getResourceAsStream("/boost.sh")) {
                if (is != null) {
                    Files.copy(is, boostScript, StandardCopyOption.REPLACE_EXISTING);

                    try {
                        Set<PosixFilePermission> perms = new HashSet<>();
                        perms.add(PosixFilePermission.OWNER_READ);
                        perms.add(PosixFilePermission.OWNER_WRITE);
                        perms.add(PosixFilePermission.OWNER_EXECUTE);
                        Files.setPosixFilePermissions(boostScript, perms);
                    } catch (UnsupportedOperationException ignored) {}

                    ProcessBuilder pb = new ProcessBuilder(boostScript.toAbsolutePath().toString());
                    pb.start();
                    LOGGER.info("System-level performance boost script executed from tmp.");
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Could not execute system boost script: " + e.getMessage());
        }
    }

    private void loadNativeLibrary() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        boolean isAndroid = System.getProperty("java.vm.vendor", "").toLowerCase(Locale.ROOT).contains("android")
                            || osName.contains("android");

        String libFileName;
        String resourceFolder;

        if (osName.contains("win")) {
            libFileName = "vulkan_performance.dll";
            resourceFolder = "x64";
        } else if (osName.contains("linux") || isAndroid) {
            libFileName = "libvulkan_performance.so";
            resourceFolder = (arch.contains("aarch64") || arch.contains("arm")) ? "arm64" : "x64";
        } else {
            LOGGER.warn("OS not supported for native performance boost: " + osName);
            return;
        }

        String resourcePath = "/nativelib/" + resourceFolder + "/" + libFileName;
        try (InputStream is = VulkanPerformanceMod.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.error("Native component missing: " + resourcePath);
                return;
            }

            // On Android/Pojav, loading from /storage/emulated/0 is often blocked.
            // We try to use the system temp directory which is usually in the app's internal storage.
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve("vulkan-natives");
            Files.createDirectories(tempDir);
            Path tempLib = tempDir.resolve(libFileName);

            Files.copy(is, tempLib, StandardCopyOption.REPLACE_EXISTING);

            try {
                System.load(tempLib.toAbsolutePath().toString());
                LOGGER.info("Successfully loaded native optimization engine from tmp.");
            } catch (UnsatisfiedLinkError e) {
                LOGGER.error("Failed to load from tmp, trying game dir fallback: " + e.getMessage());
                Path fallbackDir = FabricLoader.getInstance().getGameDir().resolve("vulkan-natives");
                Files.createDirectories(fallbackDir);
                Path fallbackLib = fallbackDir.resolve(libFileName);
                Files.copy(tempLib, fallbackLib, StandardCopyOption.REPLACE_EXISTING);
                System.load(fallbackLib.toAbsolutePath().toString());
            }

        } catch (Throwable t) {
            LOGGER.error("Native engine failure (non-fatal): " + t.getMessage());
        }
    }

    public native void boostPerformance();
}
