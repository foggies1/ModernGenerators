package net.foggies.moderngens.api;

import net.foggies.moderngens.impl.obj.Resource;

import java.io.IOException;

public interface IGenerator {

    /**
     * Change the generator's resource.
     *
     * @param resource the resource object.
     */
    void changeResource(Resource resource);

    /**
     * Increase the generator's level.
     *
     * @param amount the amount of levels you want to add.
     */
    void addLevel(long amount);

    /**
     * Decrease the generator's level.
     *
     * @param amount the amount of levels you want to remove.
     */
    void removeLevel(long amount);

    /**
     * Serialize the generator object.
     * @return the serialized string.
     */
    String serialize() throws IOException;

}
