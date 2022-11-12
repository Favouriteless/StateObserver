package com.favouriteless.stateobserver.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

	@Inject(method="setBlockState", at=@At("RETURN"))
	public void onStateChange(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir) {

	}

}
