package net.favouriteless.stateobserver.mixin;

import net.favouriteless.stateobserver.StateObserverManagerImpl;
import net.favouriteless.stateobserver.StateObserverMod;
import net.favouriteless.stateobserver.api.StateObserver;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
		LevelChunk chunk = (LevelChunk)(Object)this;
		if(!chunk.getLevel().isClientSide)
			StateObserverManagerImpl.INSTANCE.notifyChange((ServerLevel)chunk.getLevel(), pos, chunk.getBlockState(pos), state);
	}

}
