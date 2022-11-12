package com.favouriteless.stateobserver;

import com.favouriteless.stateobserver.api.AbstractStateObserver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid=StateObserver.MOD_ID, bus=Bus.FORGE)
public class StateObserverManager {

	private static final List<AbstractStateObserver> OBSERVERS = new ArrayList<>();

	public static void addObserver(AbstractStateObserver observer) {
		OBSERVERS.add(observer);
	}

	public static void removeObserver(AbstractStateObserver observer) {
		OBSERVERS.remove(observer);
	}

	public static void notifyChange(BlockPos pos, BlockState oldState, BlockState newState) {
		for(AbstractStateObserver observer : OBSERVERS) {
			if(observer.isWithinBounds(pos)) {
				observer.getChangeSet().change(pos, oldState, newState);
			}
		}
	}

}
