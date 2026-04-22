package com.performance.vulkanmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void onRender(E entity, double x, double y, double z, float yaw, float tickDelta,
                                            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // Performance optimization: Cull entities based on distance and type
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || entity == client.player) return;

        double distanceSq = client.player.squaredDistanceTo(entity);

        // Don't render common entities beyond 64 blocks in SMPs to save massive FPS
        // Players always render within vanilla distance
        if (!(entity instanceof PlayerEntity) && distanceSq > 4096) { // 64 * 64 = 4096
            ci.cancel();
        }
    }
}
