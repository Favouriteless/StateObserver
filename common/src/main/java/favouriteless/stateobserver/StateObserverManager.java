package favouriteless.stateobserver;

import favouriteless.stateobserver.api.StateObserver;
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

	@SuppressWarnings("unchecked") // This is an "unchecked" cast-- class is checked instead.
	public static <T extends StateObserver> T getObserver(Level level, BlockPos pos, Class<T> clazz) {
		for(StateObserver observer : OBSERVERS) {
			if(observer.getClass() == clazz) {
				if(observer.getLevel() == level && observer.getPos().equals(pos))
					return (T)observer;
			}
		}
		return null;
	}

	public static void reset() {
		OBSERVERS.clear();
	}

}