package net.foggies.moderngens.api;

import foggielib.player.PlayerUtils;
import net.foggies.moderngens.impl.obj.GenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public interface IPlayerStorage {

    /**
     * Unloading all online player's by
     * saving their data and removing them from
     * cache.
     *
     * @throws IOException
     */
    void unloadOnline() throws IOException;

    /**
     * Loading a player into cache by getting
     * their data from the database.
     *
     * @param uuid the player's uuid.
     * @throws IOException
     */
    void load(UUID uuid) throws IOException;

    /**
     * Unload a player, saving their data to
     * the database and removing them from cache.
     *
     * @param uuid the player's uuid.
     * @throws IOException
     */
    void unload(UUID uuid) throws IOException;

    /**
     * Get a GenPlayer by their uuid.
     *
     * @param uuid the player's uuid.
     * @return the optional gen player object.
     */
    Optional<GenPlayer> get(UUID uuid);

    /**
     * Get a GenPlayer by their name.
     *
     * @param name the player's name.
     * @return the optional gen player object.
     */
    Optional<GenPlayer> get(String name);

}
