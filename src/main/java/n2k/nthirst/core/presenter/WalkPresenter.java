package n2k.nthirst.core.presenter;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
public class WalkPresenter extends APresenter {
    public WalkPresenter(IInteractor INTERACTOR) {
        super(INTERACTOR);
    }
    @Override
    public void init() {
        JavaPlugin PLUGIN = this.getInteractor().getPlugin();
        PLUGIN.getServer().getPluginManager().registerEvents(this, PLUGIN);
    }
    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent EVENT) {
        IEngine ENGINE = this.getInteractor().getEngine(EVENT.getPlayer().getName());
        if(EVENT.isCancelled()) return;
        this.reload(ENGINE);
    }
    private void reload(IEngine ENGINE) {
        if(!ENGINE.getModifierList().contains(EModifiers.WALK)) {
            ENGINE.addActiveModifier(EModifiers.WALK);
        } else {
            ENGINE.removeModifier(EModifiers.WALK);
        }
    }
}