package com.performance.vulkanmod.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(World.class)
public abstract class ParticleOptimizationMixin {
    private final Random random = new Random();

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(CallbackInfo ci) {
        // Skip 50% of non-critical particles to save FPS in public SMPs and blasting
        if (random.nextFloat() < 0.5f) {
            ci.cancel();
        }
    }
}
