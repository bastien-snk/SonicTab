package dev.fls.tablist.animations.tasks;

import dev.fls.tablist.TabListTemplate;
import dev.fls.tablist.page.parts.body.lines.BodyLine;
import dev.fls.tablist.page.parts.body.lines.LineType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This task update every custom lines ping
 *
 */
@Getter
public class TablistPingTask extends BukkitRunnable {

    private final List<TabListTemplate> templates = new ArrayList<>();

    @Override
    public void run() {
        for(TabListTemplate template : templates) {

            List<BodyLine> lines = template.getBody().linesAsList();

            for(UUID uuid : template.getDisplayedTo()) {
                Player player = Bukkit.getPlayer(uuid);
                for(BodyLine line : lines) {
                    if(!line.getLineType().equals(LineType.PLAYER)) continue;
                    line.updatePing(player);
                }
            }
        }
    }
}
