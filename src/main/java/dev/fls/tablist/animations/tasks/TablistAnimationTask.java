package dev.fls.tablist.animations.tasks;

import dev.fls.tablist.animations.TablistAnimation;
import dev.fls.tablist.page.OptionalPart;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TablistAnimationTask extends BukkitRunnable {

    private final TablistAnimation animation;

    public TablistAnimationTask(TablistAnimation animation) {
        this.animation = animation;
    }

    private int currentHeaderPause, currentFooterPause, currentBodyPause;
    private int currentHeaderDelay, currentFooterDelay, currentBodyDelay;
    private int currentHeader, currentFooter, currentBody;

    @Override
    public void run() {
        animation.setRunning(true);

        // TODO actualiser qu'une fois header et footer ou bien actualiser indépendement header et footer

        if (currentHeaderDelay != -1) currentHeaderDelay++;
        if (currentBodyDelay != -1) currentBodyDelay++;
        if (currentFooterDelay != -1) currentFooterDelay++;

        header: {
            if (animation.isAnimateHeader()) {
                if (currentHeaderDelay == animation.getHeaderInterval()) {
                    if(currentHeader < 0) break header;
                    animation.getTemplate().getHeader().setLines(animation.getHeaderFrames().get(currentHeader));
                    Bukkit.getOnlinePlayers().forEach(player -> OptionalPart.sendPacket(player, animation.getTemplate().getHeader(), animation.getTemplate().getFooter()));
                    currentHeader = applyCurrent(currentHeader, animation.getHeaderFrames().size());
                    currentHeaderDelay = 0;
                } else {
                    if(currentHeader == -1) {
                        if(currentHeaderPause >= animation.getHeaderAnimationDelay()) {
                            currentHeaderPause = 0;
                            currentHeaderDelay = 0;
                            currentHeader = 0;
                        } else {
                            currentHeaderPause++;
                        }
                    }
                }
            }
        }

        body: {
            if (animation.isAnimateBody()) {
                if (currentBodyDelay == animation.getBodyInterval()) {
                    if(currentBody < 0) break body;
                    animation.getTemplate().setBody(animation.getBodyFrames().get(currentBody));
                    Bukkit.getOnlinePlayers().forEach(player -> animation.getTemplate().getBody().show(player));
                    currentBody = applyCurrent(currentBody, animation.getBodyFrames().size());
                    currentBodyDelay = 0;
                } else {
                    if(currentBody == -1) {
                        if(currentBodyPause >= animation.getBodyAnimationDelay()) {
                            currentBodyPause = 0;
                            currentBodyDelay = 0;
                            currentBody = 0;
                        } else {
                            currentBodyPause++;
                        }
                    }
                }
            }
        }

        footer: {
            if (animation.isAnimateFooter()) {
                if (currentFooterDelay == animation.getFooterInterval()) {
                    if(currentFooter < 0) break footer;
                    animation.getTemplate().getFooter().setLines(animation.getFooterFrames().get(currentFooter));
                    Bukkit.getOnlinePlayers().forEach(player -> OptionalPart.sendPacket(player, animation.getTemplate().getHeader(), animation.getTemplate().getFooter()));
                    currentFooter = applyCurrent(currentFooter, animation.getFooterFrames().size());
                    currentFooterDelay = 0;
                } else {
                    if(currentFooter == -1) {
                        if(currentFooterPause >= animation.getBodyAnimationDelay()) {
                            currentFooterPause = 0;
                            currentFooterDelay = 0;
                            currentFooter = 0;
                        } else {
                            currentFooterPause++;
                        }
                    }
                }
            }
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        animation.setRunning(false);
        super.cancel();
    }

    private int applyCurrent(int current, int frames) {
        if (current < frames - 1) {
            current++;
        } else {
            current = -1;
        }

        return current;
    }
}
