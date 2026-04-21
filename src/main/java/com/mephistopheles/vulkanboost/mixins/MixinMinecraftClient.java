package com.mephistopheles.vulkanboost.mixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(
        method = "run",
        at = @At("HEAD")
    )
    private void onClientStart(CallbackInfo ci) {
        // LTW (LWJGL) optimizations
        System.setProperty("org.lwjgl.librarypath", System.getProperty("java.library.path"));
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "false");
    }
}
