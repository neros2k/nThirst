package n2k.nthirst.core.presenter;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
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
        if(EVENT.isCancelled()) return;
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getPlayer().getName());
        ENGINE.addWaterLevel(EModifiers.FOOD.getModifier().getValue(ENGINE, new String[]{EVENT.getItem().getType().toString()}));
    }
}