package com.performance.vulkanmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class CritLagMixin {
    @Unique
    private long lastCritParticleTick = -1;
    @Unique
    private long lastEnchantedParticleTick = -1;

    @Inject(method = "handleStatus", at = @At("HEAD"), cancellable = true)
    private void onHandleStatus(byte status, CallbackInfo ci) {
        // Status 4 is Critical Hit particles, 5 is Enchanted Hit particles
        if (status == 4) {
            long currentTick = ((Entity) (Object) this).getWorld().getTime();
            if (currentTick == lastCritParticleTick) {
                ci.cancel();
            }
            lastCritParticleTick = currentTick;
        } else if (status == 5) {
            long currentTick = ((Entity) (Object) this).getWorld().getTime();
            if (currentTick == lastEnchantedParticleTick) {
                ci.cancel();
            }
            lastEnchantedParticleTick = currentTick;
        }
    }
}
