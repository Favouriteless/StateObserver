package favouriteless.stateobserver.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class StateChangeSet {

	private final List<StateChange> changes = new ArrayList<>();

	public void change(BlockPos pos, BlockState oldState, BlockState newState) {
		for(int i = 0; i < changes.size(); i++) {
			StateChange change = changes.get(i);
			if(change.pos == pos) {
				changes.set(i, new StateChange(pos, change.oldState, newState));
				return;
			}
		}
		changes.add(new StateChange(pos, oldState, newState));
	}

	public void clear() {
		changes.clear();
	}

	public List<StateChange> getChanges() {
		return changes;
	}


	public record StateChange(BlockPos pos, BlockState oldState, BlockState newState) {}
}