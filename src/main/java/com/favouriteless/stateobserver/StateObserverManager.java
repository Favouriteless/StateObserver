package com.favouriteless.stateobserver;

import com.favouriteless.stateobserver.api.AbstractStateObserver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid= StateObserverMod.MOD_ID, bus=Bus.FORGE)
public class StateObserverManager {

	private static final List<AbstractStateObserver> OBSERVERS = new ArrayList<>();

	public static <T extends AbstractStateObserver> T createObserver(T observer) {
		OBSERVERS.add(observer);
		observer.onInit();
		return observer;
	}

	public static void removeObserver(AbstractStateObserver observer) {
		observer.onRemove();
		OBSERVERS.remove(observer);
	}

	public static void notifyChange(Level level, BlockPos pos, BlockState oldState, BlockState newState) {
		for(AbstractStateObserver observer : OBSERVERS) {
			if(level == observer.getLevel() && observer.isWithinBounds(pos)) {
				observer.getStateChangeSet().change(pos, oldState, newState);
			}
		}
	}

	@SubscribeEvent
	public static void onServerStopping(ServerStoppedEvent event) {
		OBSERVERS.clear();
	}

}
