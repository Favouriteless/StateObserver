package com.favouriteless.stateobserver;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(StateObserverMod.MOD_ID)
public class StateObserverMod {

    public static final String MOD_ID = "stateobserver";
    public static final Logger LOGGER = LogUtils.getLogger();

    public StateObserverMod() {}

}
