package net.favouriteless.stateobserver;

import net.favouriteless.stateobserver.api.StateChangeSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateChangeSetImpl implements StateChangeSet {

	private final List<StateChange> changes = new ArrayList<>();

	@Override
	public void change(BlockPos pos, BlockState oldState, BlockState newState) {
		for(int i = 0; i < changes.size(); i++) {
			StateChange change = changes.get(i);
			if(change.pos() == pos) {
				changes.set(i, new StateChange(pos, change.oldState(), newState));
				return;
			}
		}
		changes.add(new StateChange(pos, oldState, newState));
	}

	@Override
	public void clear() {
		changes.clear();
	}

	@Override
	public Collection<StateChange> getChanges() {
		return changes;
	}

}