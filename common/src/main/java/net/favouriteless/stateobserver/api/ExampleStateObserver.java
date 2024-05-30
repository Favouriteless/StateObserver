package net.favouriteless.stateobserver.api;

import net.favouriteless.stateobserver.StateObserverMod;
import net.favouriteless.stateobserver.api.StateChangeSet.StateChange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ExampleStateObserver extends StateObserver {

    public ExampleStateObserver(Level level, BlockPos pos, int radiusX, int radiusY, int radiusZ) {
        super(level, pos, radiusX, radiusY, radiusZ);
    }

    @Override
    protected void handleChanges() {
        StateObserverMod.LOGGER.info("Since the last time you checked, the following changes have occurred:");
        for(StateChange change : getChangeSet().getChanges()) {
            StateObserverMod.LOGGER.info("At {}: {} changed into {}",
                    change.pos().toShortString(),
                    change.oldState().getBlock().getName().getString(),
                    change.newState().getBlock().getName().getString()
            );
        }
    }

    @Override
    public void onInit() {
        StateObserverMod.LOGGER.info("Hello StateObserver World!");
    }

    @Override
    public void onRemove() {
        StateObserverMod.LOGGER.info("Goodbye StateObserver!");
    }

}
