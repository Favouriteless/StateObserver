package net.favouriteless.stateobserver;

import net.favouriteless.stateobserver.api.GlobalStateListener;
import net.favouriteless.stateobserver.api.StateObserver;
import net.favouriteless.stateobserver.api.StateObserverManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class StateObserverManagerImpl implements StateObserverManager {

	public static final StateObserverManagerImpl INSTANCE = new StateObserverManagerImpl();

	private final Map<ChunkPos, Set<StateObserver>> observers = new HashMap<>();
	private final List<GlobalStateListener> globalListeners = new ArrayList<>();

	public <T extends StateObserver> T addObserver(T observer) {
		getObservedChunks(observer).forEach(c -> observers.computeIfAbsent(c, k -> new HashSet<>()).add(observer));
		observer.onInit();
		return observer;
	}

	public void removeObserver(StateObserver observer) {
		observer.onRemove();
		getObservedChunks(observer).forEach(c -> Optional.ofNullable(observers.get(c)).ifPresent(s -> s.remove(observer)));
	}

	@SuppressWarnings("unchecked") // This is an "unchecked" cast-- class is checked instead.
	public <T extends StateObserver> T getObserver(Level level, BlockPos pos, Class<T> clazz) {
		ChunkPos chunkPos = new ChunkPos(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
		if(!observers.containsKey(chunkPos))
			return null;

		for(StateObserver observer : observers.get(chunkPos)) {
			if(observer.getClass() == clazz) {
				if(observer.getLevel() == level && observer.getPos().equals(pos))
					return (T)observer;
			}
		}
		return null;
	}

	@Override
	public void registerGlobalListener(GlobalStateListener listener) {
		globalListeners.add(listener);
	}

	public void notifyChange(ServerLevel level, BlockPos pos, BlockState oldState, BlockState newState) {
		globalListeners.forEach(l -> l.notify(level, pos, oldState, newState));

		ChunkPos chunkPos = new ChunkPos(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
		if(!observers.containsKey(chunkPos))
			return;

		for(StateObserver observer : observers.get(chunkPos)) {
			if(level == observer.getLevel() && observer.isWithinBounds(pos))
				observer.getChangeSet().change(pos, oldState, newState);
		}
	}

	protected Stream<ChunkPos> getObservedChunks(StateObserver observer) {
		BlockPos center = observer.getPos();

		return ChunkPos.rangeClosed(
				new ChunkPos(
						SectionPos.blockToSectionCoord(center.getX() - observer.getRadiusX()),
						SectionPos.blockToSectionCoord(center.getZ() - observer.getRadiusZ())
				),
				new ChunkPos(
						SectionPos.blockToSectionCoord(center.getX() + observer.getRadiusX()),
						SectionPos.blockToSectionCoord(center.getZ() + observer.getRadiusZ())
				)
		);
	}

	public void reset() {
		observers.clear();
	}

}