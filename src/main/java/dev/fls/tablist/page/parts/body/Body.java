package dev.fls.tablist.page.parts.body;

import dev.fls.tablist.TabListTemplate;
import dev.fls.tablist.page.PagePart;
import dev.fls.tablist.page.PartType;
import dev.fls.tablist.page.parts.body.lines.BodyLine;
import dev.fls.tablist.skin.SkinColor;
import dev.fls.tablist.utils.PacketUtils;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class Body extends PagePart {

    public static final int MAX_LINES = 80;
    public static final int MAX_LINES_PER_COLUMN = 20;
    public static final int MAX_COLUMNS = MAX_LINES / MAX_LINES_PER_COLUMN;

    private final BodyLine[][] lines = new BodyLine[20][4];
    private int columns;
    private int lineWidth = 10;
    private boolean removeBaseLines = true;

    public Body() {
        super(PartType.BODY);
    }

    public Body addLine(BodyLine line) {
        if(line.getText().length() > lineWidth && lineWidth > -1) line.setText(line.getText().substring(0, lineWidth - 1));
        removeLine(line.getX(), line.getZ());
        lines[line.getZ()][line.getX()] = line;
        return this;
    }

    public Body addLines(BodyLine... line) {
        for(BodyLine l : line) {
            addLine(l);
        }
        return this;
    }

    public Body removeLine(int x, int z) {
        BodyLine line = lines[z][x];
        if(line != null) {
            // TODO faire if tablist affiché alors hide
            Bukkit.getOnlinePlayers().forEach(player ->  line.hide(player));
            addFakeLine(x, z, applyLineWidth(lineWidth));
        }
        return this;
    }

    public Body removeLines(List<int[][]> index) {
        for(int x = 0; x < index.size(); x++) {
            for(int z = 0; z < index.get(x).length; z++) {
                Bukkit.broadcastMessage("DEBUG: removeLine " + x + " " + z);
            }
        }
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

    private String applyLineWidth(int lineWidth) {
        String text = "";

        for(int i = 1; i <= lineWidth; i++) {
            text += " ";
        }
        return text;
    }

    public Body setColumns(int columns) {
        boolean debug = false;

        if(columns > MAX_COLUMNS) columns = MAX_COLUMNS;
        this.columns = columns;

        String text = applyLineWidth(lineWidth);

        for(int x = 0; x < lines.length; x++) {
            for(int z = 0; z < columns; z++) {
                BodyLine line = new BodyLine(debug ? x + "." + z : text, 0, z, x)
                        .setSkin(SkinColor.DARK_GRAY.getTexture(), SkinColor.DARK_GRAY.getSignature());
                removeLine(z, x);
                lines[x][z] = line;
            }
        }
        return this;
    }

    private BodyLine addFakeLine(int x, int z, String txt) {
        boolean debug = false;
        String text = debug ? x + "." + z + txt : txt;

        SkinColor skinColor = SkinColor.DARK_GRAY;
        BodyLine line = new BodyLine(text, 0, x, z)
                .setSkin(skinColor.getTexture(), skinColor.getSignature());

        lines[z][x] = line;

        return line;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        int maxLength = 0;
        for(int x = 0; x < lines.length; x++) {
            for(int z = 0; z < lines[x].length; z++) {
                BodyLine line = lines[x][z];
                if(line == null) continue;

                int index = line.getText().length() < lineWidth ? line.getText().length() : lineWidth - 1;
                if (index + 1 > lineWidth) index = lineWidth;

                String text = line.getText().substring(0, index);
                if (maxLength < text.length()) maxLength = text.length();

                line.setCorrectLengthText(text);
            }
        }
    }

    private int maxLines() {
        return columns * 20;
    }

    public void show(Player player) {
        if(removeBaseLines)
            Bukkit.getScheduler().runTaskLaterAsynchronously(TabListTemplate.getPlugin(), () -> {
                removeBaseLines(player);
            }, 2);

        for(int x = 0; x < lines.length; x++) {
            for (int z = 0; z < lines[x].length; z++) {
                BodyLine line = lines[x][z];
                if(line == null) continue;

                line.show(player);
            }
        }
    }

    public void hide(Player player) {
        for(int x = 0; x < lines.length; x++) {
            for (int z = 0; z < lines[x].length; z++) {
                BodyLine line = lines[x][z];
                if(line == null) continue;

                line.hide(player);
            }
        }
    }

    public List<BodyLine> linesAsList() {
        List<BodyLine> lines = new ArrayList<>();
        for (BodyLine[] line : this.lines) {
            lines.addAll(Arrays.asList(line));
        }
        return lines;
    }

    public void reset(Player player) {

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for(Player online : players) {
            int z = 0;
            int index = players.indexOf(online);

            if(index >= 20) {
                if(index < 40) z = 1;
                if(index < 60) z = 2;
            }

            int x = index - MAX_LINES_PER_COLUMN * z;

            BodyLine line = new BodyLine(online.getPlayerListName(), x, z, online.getName(), ((CraftPlayer) player).getHandle())
                    .setSkin(online.getUniqueId());
            line.show(player);
        }
        hide(player);
    }
}
