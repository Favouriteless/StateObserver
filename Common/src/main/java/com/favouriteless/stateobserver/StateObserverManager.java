package com.favouriteless.stateobserver;

import com.favouriteless.stateobserver.api.StateObserver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class StateObserverManager {

	private static final List<StateObserver> OBSERVERS = new ArrayList<>();

	public static <T extends StateObserver> T createObserver(T observer) {
		OBSERVERS.add(observer);
		observer.onInit();
		return observer;
	}

	public static void removeObserver(StateObserver observer) {
		observer.onRemove();
		OBSERVERS.remove(observer);
	}

	public static void notifyChange(Level level, BlockPos pos, BlockState oldState, BlockState newState) {
		for(StateObserver observer : OBSERVERS) {
			if(level == observer.getLevel() && observer.isWithinBounds(pos)) {
				observer.getStateChangeSet().change(pos, oldState, newState);
			}
		}
	}

	public static void reset() {
		OBSERVERS.clear();
	}

}