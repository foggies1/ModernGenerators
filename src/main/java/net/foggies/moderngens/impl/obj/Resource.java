package net.foggies.moderngens.impl.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.foggies.moderngens.api.IResource;
import org.bukkit.Material;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Resource implements IResource, Serializable {

    private static final long serialVersionUID = 1L;
    private String resourceType;
    private double sellPrice;

    @Override
    public Material toMaterial() {
        return Material.valueOf(this.resourceType.toUpperCase());
    }

}
