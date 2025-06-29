package net.favouriteless.stateobserver.api;

import net.favouriteless.stateobserver.StateObserverManagerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Main API for StateObserver, used to manage which {@link StateObserver}s are being tracked.
 */
public interface StateObserverManager {

    /**
     * @return The current {@link StateObserverManager} instance.
     */
    static StateObserverManager get() {
        return StateObserverManagerImpl.INSTANCE;
    }

    /**
     * Add a {@link StateObserver} to tracking.
     *
     * @param observer The {@link StateObserver} to add.
     *
     * @return The {@link StateObserver} after it has been added.
     */
    <T extends StateObserver> T addObserver(T observer);

    /**
     * Remove a {@link StateObserver} from tracking.
     *
     * @param observer The {@link StateObserver} to remove.
     */
    void removeObserver(StateObserver observer);

    /**
     * Get the {@link StateObserver} at a given level and position, provided it matches a class.
     *
     * @param level The {@link Level} to check.
     * @param pos The {@link BlockPos} to check.
     * @param clazz The {@link Class} of the desired observer.
     *
     * @return A {@link StateObserver} of the given level, position and type, or null if none were found.
     */
    @Nullable <T extends StateObserver> T getObserver(Level level, BlockPos pos, Class<T> clazz);

    void registerGlobalListener(GlobalStateListener listener);

}
