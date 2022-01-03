package net.foggies.moderngens.impl.events;

import lombok.SneakyThrows;
import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.impl.storage.PlayerStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinQuit implements Listener {

    private ModernGens modernGens;
    private PlayerStorage playerStorage;

    public JoinQuit(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.playerStorage = modernGens.getPlayerStorage();
    }

    @SneakyThrows
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        playerStorage.load(uuid);
    }

    @SneakyThrows
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        playerStorage.unload(uuid);
    }

}
