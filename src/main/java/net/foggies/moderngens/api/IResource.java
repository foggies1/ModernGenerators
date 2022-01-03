package net.foggies.moderngens.api;

import org.bukkit.Material;

public interface IResource {

    /**
     * Convert the resource type into
     * a bukkit material.
     *
     * @return the bukkit material.
     */
    Material toMaterial();

}
