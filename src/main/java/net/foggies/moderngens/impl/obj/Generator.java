package net.foggies.moderngens.impl.obj;

import foggielib.location.SimpleLocation;
import foggielib.serializer.SerializeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.foggies.moderngens.api.IGenerator;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Generator implements IGenerator, Serializable {

    // We will use the generator type to retrieve the item-stack
    // from a hashmap of loaded in generators.

    // Upgrading the level will allow you to unlock more items that can be
    // generated, each item has higher sell prices.

    // Generators have virtual inventories, meaning when you click a generator
    // it'll open a menu containing stats, upgrade button, menu to buy more resources and then
    // menu to pick resource to generate.

    private static final long serialVersionUID = 1L;
    private UUID generatorOwner;
    private String generatorType;
    private long generatorLevel;
    private Resource generatedResource;

    @Override
    public void changeResource(Resource resource) {
        setGeneratedResource(resource);
    }

    @Override
    public void addLevel(long amount) {
        setGeneratorLevel(getGeneratorLevel() + amount);
    }

    @Override
    public void removeLevel(long amount) {
        if(getGeneratorLevel() - amount < 0) amount = getGeneratorLevel();
        setGeneratorLevel(getGeneratorLevel() - amount);
    }

    @Override
    public String serialize() throws IOException {
        return SerializeUtils.toString(this);
    }

}
