package dev.fls.tablist.tab.page.parts.body;

import dev.fls.tablist.tab.page.PagePart;
import dev.fls.tablist.tab.page.PartType;
import dev.fls.tablist.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Body extends PagePart {

    private static final int MAX_SIZE = 80;

    private Queue<BodyLine> lines = new LinkedList<>();
    private List<BodyLine> emptyLines = new ArrayList<>();
    private int columns;
    private boolean removeBaseLines;

    public Body() {
        super(PartType.BODY);
    }

    public Body addLine(BodyLine line) {
        lines.offer(line);
        if(emptyLines.size() > 0) emptyLines.remove(0);
        return this;
    }

    public Body addLines(BodyLine... line) {
        lines.addAll(Arrays.asList(line));
        return this;
    }

    public Body removeLine(int index) {
        lines.remove(index);
        return this;
    }

    public Body removeLines(int... index) {
        lines.removeAll(Arrays.asList(index));
        return this;
    }

    private Body removeBaseLines(Player player) {
        for(Player online : Bukkit.getOnlinePlayers()) {
            PacketUtils.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) online).getHandle()));
        }
        return this;
    }

    public Body setRemoveBaseLines(boolean removeBaseLines) {
        this.removeBaseLines = removeBaseLines;
        return this;
    }

    public Body setColumns(int columns) {
        if(columns > MAX_SIZE / 20) columns = MAX_SIZE / 20;
        this.columns = columns;

        int linesToAdd = maxLines() - lines.size();
        for(int i = 0; i < linesToAdd; i++) {
            emptyLines.add(new BodyLine("", 0, 999));
        }
        return this;
    }

    public Queue<BodyLine> getLines() {
        return lines;
    }

    private int maxLines() {
        return columns * 20;
    }

    public void display(Player player) {
        if(removeBaseLines) removeBaseLines(player);
        /*for(int i = 0; i < lines.size(); i++) {
            lines.get(i).sendPacket(player);
        }*/

        lines.forEach(line -> {
            Bukkit.getConsoleSender().sendMessage(line.getText());
            line.sendPacket(player);
        });

        for(int i = 0; i < emptyLines.size(); i++) {
            emptyLines.get(i).sendPacket(player);
        }
    }

}