package com.mephistopheles.vulkanboost.mixins;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(
        method = "render",
        at = @At("HEAD")
    )
    private void onRenderStart(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        // Pre-render optimizations (OpenGL)
        OpenGLOptimizer.preRender();
    }

    @Inject(
        method = "render",
        at = @At("TAIL")
    )
    private void onRenderEnd(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        // Post-render optimizations (OpenGL)
        OpenGLOptimizer.postRender();
    }
}
