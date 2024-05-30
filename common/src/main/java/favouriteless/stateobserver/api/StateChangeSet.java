package favouriteless.stateobserver.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;

/**
 * Represents a set of changes made within a {@link StateObserver}'s observed area.
 */
public interface StateChangeSet {

    /**
     * @return A {@link Collection<StateChange>} of all changes within this set.
     */
    Collection<StateChange> getChanges();

    /**
     * Add a new change to this {@link StateChangeSet}.
     *
     * @param pos The {@link BlockPos} of the change.
     * @param oldState The {@link BlockState} before the change.
     * @param newState The {@link BlockState} after the change.
     */
    void change(BlockPos pos, BlockState oldState, BlockState newState);

    /**
     * Discard the changes in this set.
     */
    void clear();

    /**
     * Represents a change in BlockState at a position.
     *
     * @param pos The {@link BlockPos} of the change.
     * @param oldState The {@link BlockState} before the change.
     * @param newState The {@link BlockState} after the change.
     */
    record StateChange(BlockPos pos, BlockState oldState, BlockState newState) {}
}
