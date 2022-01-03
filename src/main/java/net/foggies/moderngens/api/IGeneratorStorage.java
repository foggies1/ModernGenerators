package net.foggies.moderngens.api;

import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface IGeneratorStorage {

    /**
     * Get a generator item-stack from it's
     * given identifier.
     *
     * @param identifier the generator's identifier.
     * @return the optional bukkit item-stack.
     */
    Optional<ItemStack> get(String identifier);

}
