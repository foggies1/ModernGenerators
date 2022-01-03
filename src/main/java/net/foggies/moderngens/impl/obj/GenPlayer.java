package net.foggies.moderngens.impl.obj;

import foggielib.location.SimpleLocation;
import lombok.Getter;
import lombok.Setter;
import net.foggies.moderngens.api.IGenPlayer;

import java.util.*;

@Getter
@Setter
public class GenPlayer implements IGenPlayer {

    private UUID uuid;
    private HashMap<SimpleLocation, Generator> genMap;

    public GenPlayer(UUID uuid, HashMap<SimpleLocation, Generator> genMap) {
        this.uuid = uuid;
        this.genMap = genMap;
    }

    @Override
    public Optional<Generator> byLocation(SimpleLocation location) {
        return Optional.ofNullable(genMap.get(location));
    }

    @Override
    public void removeByLocation(SimpleLocation location) {
        genMap.remove(location);
    }

    @Override
    public void add(SimpleLocation simpleLocation, Generator generator) {
        genMap.putIfAbsent(simpleLocation, generator);
    }

}
