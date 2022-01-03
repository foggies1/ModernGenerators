package net.foggies.moderngens.api;

import foggielib.location.SimpleLocation;
import net.foggies.moderngens.impl.obj.Generator;

import java.util.Optional;

public interface IGenPlayer {

    /**
     * Get a generator object from it's location.
     *
     * @param location the simple location.
     * @return the optional generator object.
     */
    Optional<Generator> byLocation(SimpleLocation location);

    /**
     * Remove a generator from cache by location.
     *
     * @param location the simple location.
     */
    void removeByLocation(SimpleLocation location);

    /**
     * Adds a generator to player's cache.
     * @param generator the generator object.
     * @param simpleLocation the location the generator was placed.
     */
    void add(SimpleLocation simpleLocation, Generator generator);

}
