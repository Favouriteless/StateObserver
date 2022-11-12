package com.favouriteless.stateobserver.mixin;

import com.favouriteless.stateobserver.StateObserverManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

	@Inject(method="setBlockState", at=@At(value="HEAD"))
	public void onStateChange(BlockPos pos, BlockState state, boolean isMoving, CallbackInfoReturnable<BlockState> cir) {
		if(!((LevelChunk)(Object)this).getLevel().isClientSide) {
			StateObserverManager.notifyChange(pos, ((LevelChunk)(Object)this).getBlockState(pos), state);
		}
	}

}
