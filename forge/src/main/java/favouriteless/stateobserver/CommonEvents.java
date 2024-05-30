package favouriteless.stateobserver;

import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid= StateObserverMod.MOD_ID, bus=Bus.FORGE)
public class CommonEvents {

	@SubscribeEvent
	public static void onServerStopping(ServerStoppedEvent event) {
		StateObserverManagerImpl.INSTANCE.reset(); // Make sure to clear Observers when the server stops.
	}

}
