package net.favouriteless.stateobserver.mixin;

import net.favouriteless.stateobserver.StateObserverManagerImpl;
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
		LevelChunk levelChunk = (LevelChunk)(Object)this;
		if(!levelChunk.getLevel().isClientSide)
			StateObserverManagerImpl.INSTANCE.notifyChange(levelChunk.getLevel(), pos, levelChunk.getBlockState(pos), state);
	}

}
