package net.foggies.moderngens.impl.storage;

import foggielib.player.PlayerUtils;
import lombok.Getter;
import lombok.Setter;
import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.api.IPlayerStorage;
import net.foggies.moderngens.impl.obj.GenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class PlayerStorage implements IPlayerStorage {

    private ModernGens modernGens;
    private PlayerDatabase playerDatabase;
    private Map<UUID, GenPlayer> genPlayerMap;

    public PlayerStorage(ModernGens modernGens) throws IOException {
        this.modernGens = modernGens;
        this.playerDatabase = modernGens.getPlayerDatabase();
        this.genPlayerMap = new HashMap<>();
        loadOnline();
    }


    private void loadOnline() throws IOException {
        for(Player p : Bukkit.getOnlinePlayers()){
            load(p.getUniqueId());
        }
    }

    @Override
    public void unloadOnline() throws IOException {
        for(Player p : Bukkit.getOnlinePlayers()){
            unload(p.getUniqueId());
        }
    }

    @Override
    public void load(UUID uuid) throws IOException {
        Optional<GenPlayer> genPlayer = playerDatabase.get(uuid);

        if(genPlayer.isEmpty()) {
            GenPlayer gPlayer = new GenPlayer(uuid, new HashMap<>());
            playerDatabase.put(gPlayer);
            genPlayerMap.put(uuid, gPlayer);
            return;
        }

        genPlayerMap.put(uuid, genPlayer.get());
    }

    @Override
    public void unload(UUID uuid) throws IOException {
        Optional<GenPlayer> genPlayer = get(uuid);
        if(genPlayer.isEmpty()) return;

        playerDatabase.save(genPlayer.get());
        genPlayerMap.remove(uuid);
    }

    @Override
    public Optional<GenPlayer> get(UUID uuid){
        return Optional.ofNullable(genPlayerMap.get(uuid));
    }

    @Override
    public Optional<GenPlayer> get(String name){
        return get(PlayerUtils.get(name).getUniqueId());
    }

}
