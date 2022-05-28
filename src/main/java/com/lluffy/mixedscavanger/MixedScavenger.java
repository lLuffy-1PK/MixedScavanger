package com.lluffy.mixedscavanger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mixedscavenger")
public class MixedScavenger {

    private static final Logger LOGGER = LogManager.getLogger();

    public MixedScavenger() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("MixedScavenger initialized!");
    }

    public static boolean hasPermission(PlayerEntity player, String permission) {
        try {
            return BukkitUtils.toBukkitEntity(player).hasPermission(permission);
        } catch (Exception ignored) {
        }
        return false;
    }


}
