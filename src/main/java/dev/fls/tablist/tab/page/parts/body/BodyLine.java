package dev.fls.tablist.tab.page.parts.body;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.fls.tablist.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class BodyLine {

    private final EntityPlayer entityPlayer;
    private int ping;
    private String text;

    public BodyLine(String text, int ping, int index) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
        this.text = text;
        entityPlayer = new EntityPlayer(nmsServer, world, new GameProfile(UUID.randomUUID(), "line" + index), new PlayerInteractManager(world));
        this.ping = ping;
        entityPlayer.ping = this.ping;
        setName();
    }

    private void setName() {
        try {
            Field headerField = entityPlayer.getClass().getDeclaredField("listName");
            headerField.setAccessible(true);
            headerField.set(entityPlayer, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + " \"}"));
            headerField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BodyLine setSkin(String value, String signature) {
        entityPlayer.getProfile().getProperties().removeAll("textures");
        entityPlayer.getProfile().getProperties().put("textures", new Property("textures", value, signature));
        return this;
    }

    public void setPing(int ping) {
        this.ping = ping;
        entityPlayer.ping = this.ping;
    }

    public void setText(String text) {
        if(text.length() > 48) text = text.substring(0,47);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void sendPacket(Player player) {
        Packet[] packets = new Packet[]{
                new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer),
                new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, entityPlayer),
        };

        Bukkit.getConsoleSender().sendMessage(text);

        for(Packet packet : packets) {
            PacketUtils.sendPacket(player, packet);
        }
    }
}
