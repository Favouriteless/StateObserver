package net.favouriteless.stateobserver;

import net.favouriteless.stateobserver.api.StateObserver;
import net.favouriteless.stateobserver.api.StateObserverManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StateObserverManagerImpl implements StateObserverManager {

	public static final StateObserverManagerImpl INSTANCE = new StateObserverManagerImpl();

	private final List<StateObserver> observers = new ArrayList<>();

	public <T extends StateObserver> T addObserver(@NotNull T observer) {
		observers.add(observer);
		observer.onInit();
		return observer;
	}

	public void removeObserver(@NotNull StateObserver observer) {
		observer.onRemove();
		observers.remove(observer);
	}

	@SuppressWarnings("unchecked") // This is an "unchecked" cast-- class is checked instead.
	public <T extends StateObserver> T getObserver(@NotNull Level level, @NotNull BlockPos pos, @NotNull Class<T> clazz) {
		for(StateObserver observer : observers) {
			if(observer.getClass() == clazz) {
				if(observer.getLevel() == level && observer.getPos().equals(pos))
					return (T)observer;
			}
		}
		return null;
	}

	public void notifyChange(Level level, BlockPos pos, BlockState oldState, BlockState newState) {
		for(StateObserver observer : observers) {
			if(level == observer.getLevel() && observer.isWithinBounds(pos)) {
				observer.getChangeSet().change(pos, oldState, newState);
			}
		}
	}

	public void reset() {
		observers.clear();
	}

}