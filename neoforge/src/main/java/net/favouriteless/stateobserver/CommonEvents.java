package net.favouriteless.stateobserver;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@EventBusSubscriber(modid = StateObserverMod.MOD_ID, bus = Bus.GAME)
public class CommonEvents {

	@SubscribeEvent
	public static void onServerStopping(ServerStoppedEvent event) {
		StateObserverManagerImpl.INSTANCE.reset(); // Make sure to clear Observers when the server stops.
	}

}
