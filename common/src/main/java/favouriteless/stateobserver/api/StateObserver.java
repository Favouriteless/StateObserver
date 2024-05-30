package favouriteless.stateobserver.api;

import favouriteless.stateobserver.StateChangeSetImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * Base class for all State Observers.
 */
public abstract class StateObserver {

	private final StateChangeSet changeSet = new StateChangeSetImpl();

	private final Level level;
	private final BlockPos pos;
	private final int radiusX;
	private final int radiusY;
	private final int radiusZ;

	public StateObserver(Level level, BlockPos pos, int radiusX, int radiusY, int radiusZ) {
		this.level = level;
		this.pos = pos;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.radiusZ = radiusZ;

		if(radiusX <= 0)
			throw new IllegalStateException("StateObserver's X radius must be greater than Zero");
		if(radiusY <= 0)
			throw new IllegalStateException("StateObserver's Y radius must be greater than Zero");
		if(radiusZ <= 0)
			throw new IllegalStateException("StateObserver's Z radius must be greater than Zero");
	}

	/**
	 * Called by {@link #checkChanges()} when there are changes available. Use this to handle the changes before
	 * they get discarded.
	 */
	protected abstract void handleChanges();

	/**
	 * Called when the observer is first created.
	 */
	public abstract void onInit();

	/**
	 * Called when observer is removed.
	 */
	public abstract void onRemove();

	/**
	 * Check if there are any changes available, if so, call {@link #handleChanges()}
	 */
	public void checkChanges() {
		if(!changeSet.getChanges().isEmpty()) {
			handleChanges();
			changeSet.clear();
		}
	}

	public StateChangeSet getChangeSet() {
		return changeSet;
	}

	// --------------------------------------------- NON-API METHODS BELOW ---------------------------------------------

	/**
	 * @return true if pos is within this observer's bounds, otherwise false.
	 */
	public boolean isWithinBounds(BlockPos pos) {
		return pos.getX() >= this.pos.getX()-radiusX && pos.getX() <= this.pos.getX()+radiusX
				&& pos.getY() >= this.pos.getY()-radiusY && pos.getY() <= this.pos.getY()+radiusY
				&& pos.getZ() >= this.pos.getZ()-radiusZ && pos.getZ() <= this.pos.getZ()+radiusZ;
	}

	public Level getLevel() {
		return level;
	}

	public BlockPos getPos() {
		return pos;
	}

	public int getRadiusX() {
		return radiusX;
	}

	public int getRadiusY() {
		return radiusY;
	}

	public int getRadiusZ() {
		return radiusZ;
	}

}