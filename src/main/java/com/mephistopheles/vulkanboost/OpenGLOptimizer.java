package com.mephistopheles.vulkanboost;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexBuffer;

public class OpenGLOptimizer {
    public static void init() {
        // Enable OpenGL optimizations
        enableFastRendering();
        enableMultithreadedUploads();
    }

    private static void enableFastRendering() {
        // Force VBOs instead of immediate mode
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "false");
        System.setProperty("org.lwjgl.opengl.Display.allowBuffering", "true");

        // Enable GPU-side batching
        ShaderProgram.setUseEntityOutlineShader(false); // Reduces shader switches
    }

    private static void enableMultithreadedUploads() {
        // Use LWJGL's multithreaded buffer uploads
        VertexBuffer.ENABLE_MULTITHREADING = true;
    }
}
