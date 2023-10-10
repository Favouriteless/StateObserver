package com.favouriteless.stateobserver;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class StateObserverFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STOPPING.register((server -> StateObserverManager.reset())); // Make sure to clear Observers when the server stops.
    }

}
