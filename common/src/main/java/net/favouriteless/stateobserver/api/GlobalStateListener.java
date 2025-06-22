package net.favouriteless.stateobserver.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface GlobalStateListener {

    void notify(ServerLevel level, BlockPos pos, BlockState oldState, BlockState newState);

}
