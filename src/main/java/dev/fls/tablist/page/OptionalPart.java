package dev.fls.tablist.page;

import dev.fls.tablist.page.parts.Footer;
import dev.fls.tablist.page.parts.Header;
import dev.fls.tablist.utils.PacketUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class OptionalPart extends PagePart {

    public OptionalPart(PartType type) {
        super(type);
    }

    private String[] lines = new String[]{};

    public static void sendPacket(Player player, Header header, Footer footer) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(IChatBaseComponent.ChatSerializer.a(header.getHeaderText()));

        try {
            Field headerField = packet.getClass().getDeclaredField("b");
            headerField.setAccessible(true);
            headerField.set(packet, IChatBaseComponent.ChatSerializer.a(footer.getHeaderText()));
            headerField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PacketUtils.sendPacket(player, packet);
    }

    public void addLine(String line) {
        List<String> lines = new ArrayList<>(Arrays.asList(this.lines));
        lines.add(line);
        this.lines = lines.toArray(new String[0]);
    }

    public void removeLine(int index) {
        List<String> lines = new ArrayList<>(Arrays.asList(this.lines));
        lines.remove(index);
        this.lines = lines.toArray(new String[0]);
    }

    public void addLines(String... added) {
        List<String> lines = new ArrayList<>(Arrays.asList(this.lines));
        lines.addAll(Arrays.asList(added));
        this.lines = lines.toArray(new String[0]);
    }

    public void removeLines(int... removed) {
        List<String> lines = new ArrayList<>(Arrays.asList(this.lines));
        lines.removeAll(Arrays.asList(removed));
        this.lines = lines.toArray(new String[0]);
    }


    /**
     * This method returns all header lines as a single JSON String
     * By this way, it could be sended in a packet
     *
     * @return
     */
    public String getHeaderText() {
        String text = "{\"text\": \"";
        List<String> linesList = Arrays.asList(lines);

        for(String line : linesList) {
            int index = linesList.indexOf(line);
            String lineTxt = line;
            if(index < linesList.size() - 1) lineTxt += "\n";
            text += lineTxt;
        }

        text += "\"}";
        return text;
    }
}
