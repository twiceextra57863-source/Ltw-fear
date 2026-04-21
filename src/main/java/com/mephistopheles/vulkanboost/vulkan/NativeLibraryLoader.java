package com.mephistopheles.vulkanboost.vulkan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeLibraryLoader {
    private static final String LIBRARY_NAME = "vulkan_boost";
    private static boolean loaded = false;

    public static void loadLibrary() {
        if (loaded) {
            return;
        }

        try {
            // Determine the OS and architecture
            String osName = System.getProperty("os.name").toLowerCase();
            String arch = System.getProperty("os.arch").toLowerCase();

            // Load the library from resources or system path
            String libraryPath = System.getProperty("java.library.path");
            String libraryFileName;

            if (osName.contains("win")) {
                libraryFileName = LIBRARY_NAME + ".dll";
            } else if (osName.contains("linux")) {
                libraryFileName = "lib" + LIBRARY_NAME + ".so";
            } else if (osName.contains("mac")) {
                libraryFileName = "lib" + LIBRARY_NAME + ".dylib";
            } else {
                throw new UnsupportedOperationException("Unsupported OS: " + osName);
            }

            // Try to load from the classpath
            try (InputStream inputStream = NativeLibraryLoader.class.getResourceAsStream("/native/" + libraryFileName)) {
                if (inputStream != null) {
                    // Create a temporary file
                    Path tempFile = Files.createTempFile(LIBRARY_NAME, libraryFileName);
                    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

                    // Load the library
                    System.load(tempFile.toAbsolutePath().toString());
                    loaded = true;
                    return;
                }
            }

            // If not found in resources, try to load from the system library path
            System.loadLibrary(LIBRARY_NAME);
            loaded = true;
        } catch (IOException | UnsatisfiedLinkError | SecurityException e) {
            throw new RuntimeException("Failed to load native library: " + LIBRARY_NAME, e);
        }
    }
}
