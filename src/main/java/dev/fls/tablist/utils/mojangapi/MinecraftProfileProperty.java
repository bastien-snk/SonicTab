package dev.fls.tablist.utils.mojangapi;

/**
 * This class is a copy of Mojang's API MinecraftProfile JSON file on
 * https://sessionserver.mojang.com/session/minecraft/profile/uuid-that-you-want?unsigned=false
 *
 * More precisely the profile properties array
 *
 * @author _FearLessS
 * @since v1.0 (2021)
 */
public class MinecraftProfileProperty {

    private final String name;
    private final String value;
    private final String signature;

    public MinecraftProfileProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "MinecraftProfileProperty{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
