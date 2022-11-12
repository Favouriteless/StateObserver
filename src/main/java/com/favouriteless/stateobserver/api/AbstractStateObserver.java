package com.favouriteless.stateobserver.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class AbstractStateObserver {

	private final StateChangeSet changeSet = new StateChangeSet();

	private final Level level;
	private final BlockPos pos;
	private final int radiusX;
	private final int radiusY;
	private final int radiusZ;

	public AbstractStateObserver(Level level, BlockPos pos, int radiusX, int radiusY, int radiusZ) {
		this.level = level;
		this.pos = pos;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.radiusZ = radiusZ;

		if(radiusX > 0)
			throw new IllegalStateException("StateObserver's X radius must be greater than Zero");
		if(radiusY > 0)
			throw new IllegalStateException("StateObserver's X radius must be greater than Zero");
		if(radiusZ > 0)
			throw new IllegalStateException("StateObserver's X radius must be greater than Zero");
	}

	/**
	 * Override this to add behaviour which needs to read state changes
	 */
	public abstract void handleChanges();

	/**
	 * @param pos
	 * @return True if pos is within this observer's bounds
	 */
	public boolean isWithinBounds(BlockPos pos) {
		return pos.getX() >= this.pos.getX()-radiusX && pos.getX() <= this.pos.getX()+radiusX
				&& pos.getY() >= this.pos.getY()-radiusY && pos.getY() <= this.pos.getY()+radiusY
				&& pos.getZ() >= this.pos.getZ()-radiusZ && pos.getZ() <= this.pos.getZ()+radiusZ;
	}

	public StateChangeSet getChangeSet() {
		return changeSet;
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
