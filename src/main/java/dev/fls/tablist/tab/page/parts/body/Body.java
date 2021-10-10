package dev.fls.tablist.tab.page.parts.body;

import dev.fls.tablist.tab.TabListTemplate;
import dev.fls.tablist.tab.page.PagePart;
import dev.fls.tablist.tab.page.PartType;
import dev.fls.tablist.tab.skin.SkinColor;
import dev.fls.tablist.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Body extends PagePart {

    private static final int MAX_LINES = 80;
    private static final int MAX_LINES_PER_COLUMN = 20;
    private static final int MAX_COLUMNS = MAX_LINES / MAX_LINES_PER_COLUMN;

    private Queue<BodyLine> lines = new LinkedList<>();
    private List<BodyLine> emptyLines = new ArrayList<>();

    public final BodyLine[][] empty = new BodyLine[20][4];
    public final BodyLine[][] full = new BodyLine[20][4];
    private int columns;
    private int lineWidth;
    private boolean removeBaseLines;

    public Body() {
        super(PartType.BODY);
    }

    public Body(int lineWidth) {
        this();
        this.lineWidth = lineWidth;
    }

    public Body addLine(BodyLine line) {
        if(line.getText().length() > lineWidth) line.setText(line.getText().substring(0, lineWidth - 1));
        full[line.getZ()][line.getX()] = line;
        empty[line.getZ()][line.getX()] = null;

        // OLD
        lines.offer(line);
        if(emptyLines.size() > 0) emptyLines.remove(0);
        return this;
    }

    public Body addLines(BodyLine... line) {
        lines.addAll(Arrays.asList(line));
        return this;
    }

    public Body removeLine(int index) {
        // TODO addd array deletion

        // OLD
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

    public Body setColumns(int columns, int lineWidth) {
        if(columns > MAX_COLUMNS) columns = MAX_COLUMNS;
        this.columns = columns;

        String text = "";
        applyLineWidth: {
            for(int i = 1; i <= lineWidth; i++) {
                text += " ";
            }
        }

        int linesToAdd = maxLines() - lines.size();
        for(int i = 0; i <= linesToAdd; i++) {
            int x = i / 20;
            int z = i - 20 * x;

            BodyLine line = new BodyLine(x + "." + z + text, 0, x, z)
                    .setSkin(SkinColor.DARK_GRAY.getTexture(), SkinColor.DARK_GRAY.getSignature());
            empty[z][x <= 0 ? 0 : x - 1] = line;
            emptyLines.add(line);
        }
        return this;
    }

    public Queue<BodyLine> getLines() {
        return lines;
    }

    private int maxLines() {
        return columns * 20;
    }

    public void show(Player player) {
        if(removeBaseLines)
            Bukkit.getScheduler().runTaskLaterAsynchronously(TabListTemplate.getPlugin(), () -> {
                removeBaseLines(player);
            }, 2);

        lines.forEach(line -> line.show(player));
        emptyLines.forEach(line -> line.show(player));
    }

    public void hide(Player player) {
        for(BodyLine line : emptyLines) {
            line.hide(player);
        }

        for(BodyLine line : lines) {
            line.hide(player);
        }
    }

    public void reset(Player player) {
        for(Player online : Bukkit.getOnlinePlayers()) {
            BodyLine line = new BodyLine(online.getPlayerListName(), -1, -1, online.getName(), ((CraftPlayer) player).getHandle())
                    .setSkin(online.getUniqueId());
            line.show(player);
        }
        hide(player);
    }
}
