package com.lluffy.mixedscavanger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public final class BukkitUtils {
    private static final Method getBukkitEntity;

    static {
        try {
            getBukkitEntity = Entity.class.getDeclaredMethod("getBukkitEntity", new Class[0]);
            getBukkitEntity.setAccessible(true);
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed hooking CraftBukkit methods!", throwable);
        }
    }

    public static Player toBukkitEntity(PlayerEntity player)
            throws Exception {
        return (Player) getBukkitEntity.invoke(player, new Object[0]);
    }
}
