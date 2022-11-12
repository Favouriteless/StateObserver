package com.favouriteless.stateobserver.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class StateChangeSet {

	private final Map<BlockPos, StateChange> changes = new HashMap<>();

	public void change(BlockPos pos, BlockState oldState, BlockState newState) {
		if(changes.containsKey(pos)) {
			StateChange change = changes.get(pos);
			if(!change.newState.equals(newState)) {
				changes.put(pos, new StateChange(change.oldState, newState)); // Update old change if already present
			}
		}
		else
			changes.put(pos, new StateChange(oldState, newState));
	}

	public void clear() {
		changes.clear();
	}

	public Map<BlockPos, StateChange> getChanges() {
		return changes;
	}

	public record StateChange(BlockState oldState, BlockState newState) { }
}
