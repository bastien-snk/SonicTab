package dev.fls.tablist.utils.mojangapi;

/**
 * This class is a copy of Mojang's API MinecraftProfile JSON file on
 * https://sessionserver.mojang.com/session/minecraft/profile/uuid-that-you-want?unsigned=false
 *
 * @author _FearLessS
 * @since v1.0 (2021)
 */
public class MinecraftProfile {

    private final String id;
    private final String name;
    private final MinecraftProfileProperty[] properties;

    public MinecraftProfile(String id, String name, MinecraftProfileProperty[] properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MinecraftProfileProperty[] getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "MinecraftProfile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", properties=" + properties.toString() +
                '}';
    }
}
