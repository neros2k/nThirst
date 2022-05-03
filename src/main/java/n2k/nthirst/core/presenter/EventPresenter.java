package n2k.nthirst.core.presenter;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.model.ConfigModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
public final class EventPresenter extends APresenter {
    public EventPresenter(IInteractor INTERACTOR) {
        super(INTERACTOR);
    }
    @Override
    public void init() {
        JavaPlugin PLUGIN = this.getInteractor().getPlugin();
        PLUGIN.getServer().getPluginManager().registerEvents(this, PLUGIN);
    }
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent EVENT) {
        this.getInteractor().startEngine(EVENT.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent EVENT) {
        this.getInteractor().stopEngine(EVENT.getPlayer().getName());
    }
    @EventHandler
    public void onPlayerEating(@NotNull PlayerItemConsumeEvent EVENT) {
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getPlayer().getName());
        if(EVENT.isCancelled() || ENGINE.isDisabledGamemode()) return;
        String ITEM = EVENT.getItem().getType().toString();
        Arrays.stream(this.getInteractor().getConfig().MODIFIERS.FOOD).forEach(
                FOOD -> {
                    if(FOOD.TYPE.equals(ITEM)) {
                        ENGINE.addActiveModifier(
                                EModifiers.FOOD.getModifier().getData(ENGINE, new String[]{ITEM})
                        );
                    }
                }
        );
    }
    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent EVENT) {
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getEntity().getName());
        ConfigModel MODEL = this.getInteractor().getConfig();
        if(ENGINE.isDisabledGamemode() || !MODEL.RESET_ON_DEATH) return;
        ENGINE.setWaterLevel((float) MODEL.DEFAULT_WATER_LEVEL);
    }
    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent EVENT) {
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getPlayer().getName());
        if(EVENT.isCancelled() || ENGINE.isDisabledGamemode()) return;
        this.moveReload(ENGINE);
        this.biomeReload(ENGINE, EVENT.getPlayer().getLocation().getBlock().getBiome().name());
    }
    private void moveReload(@NotNull IEngine ENGINE) {
        if(!ENGINE.getModifierList().contains(EModifiers.WALK.getModifier().getData(ENGINE, null))) {
            ENGINE.addActiveModifier(EModifiers.WALK);
        }
    }
    private void biomeReload(@NotNull IEngine ENGINE, String BIOME) {
        String[] ARGS = new String[]{BIOME};
        if(!ENGINE.getModifierList().contains(EModifiers.BIOMES.getModifier().getData(ENGINE, ARGS))) {
            ENGINE.addActiveModifier(EModifiers.BIOMES.getModifier().getData(ENGINE, ARGS));
        }
    }
}