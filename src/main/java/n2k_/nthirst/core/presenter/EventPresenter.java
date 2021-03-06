package n2k_.nthirst.core.presenter;
import n2k_.nthirst.base.AbstractPresenter;
import n2k_.nthirst.base.IEngine;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.model.main.MainModel;
import n2k_.nthirst.base.model.main.ItemsModel;
import n2k_.nthirst.base.modifier.EModifierType;
import n2k_.nthirst.base.modifier.Modifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
public final class EventPresenter extends AbstractPresenter implements Listener {
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
        ItemsModel ITEMS_MODEL = ENGINE.getInteractor().getItemsConfig();
        if(EVENT.isCancelled() || ENGINE.isDisabledGamemode()) return;
        String TYPE = EVENT.getItem().getType().toString();
        if(TYPE.equals(ITEMS_MODEL.CLEAR_WATER.NAME)) TYPE = "CLEAR_WATER";
        if(TYPE.equals(ITEMS_MODEL.DIRTY_WATER.NAME)) TYPE = "DIRTY_WATER";
        final String FINAL_TYPE = TYPE;
        Arrays.stream(this.getInteractor().getModifiersConfig().FOOD).forEach(
                FOOD -> {
                    if(FOOD.TYPE.equals(FINAL_TYPE)) {
                        ENGINE.addModifier(new Modifier(
                                EModifierType.FOOD,
                                ARG_ENGINE -> FOOD.VALUE,
                                ARG_ENGINE -> FOOD.DURATION,
                                ARG_ENGINE -> false
                        ));
                    }
                }
        );
    }
    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent EVENT) {
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getEntity().getName());
        MainModel MODEL = this.getInteractor().getMainConfig();
        if(ENGINE.isDisabledGamemode() || !MODEL.RESET_ON_DEATH) return;
        ENGINE.setWaterLevel((float) MODEL.DEFAULT_WATER_LEVEL);
    }
    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent EVENT) {
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getPlayer().getName());
        if(EVENT.isCancelled() || ENGINE.isDisabledGamemode()) return;
        this.moveReload(ENGINE);
    }
    private void moveReload(@NotNull IEngine ENGINE) {
        if(!ENGINE.containsModifier(EModifierType.WALK)) {
            ENGINE.addModifier(EModifierType.WALK);
        }
    }
}