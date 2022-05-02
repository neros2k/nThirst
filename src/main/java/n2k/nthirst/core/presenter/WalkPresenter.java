package n2k.nthirst.core.presenter;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IInteractor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
public class WalkPresenter extends APresenter {
    private final Map<String, Integer> SESSION_MAP = new HashMap<>();
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
        if(EVENT.isCancelled()) return;
        this.reload(EVENT.getPlayer().getName());
    }
    private void reload(String NAME) {
        if(this.SESSION_MAP.containsKey(NAME)) {
            Bukkit.getScheduler().cancelTask(this.SESSION_MAP.get(NAME));
        } else {
            this.getInteractor().getEngine(NAME).addActiveModifier(EModifiers.WALK);
        }
        Integer TASK_ID = Bukkit.getScheduler().runTaskLaterAsynchronously(
                this.getInteractor().getPlugin(),
                () -> {
                    this.getInteractor().getEngine(NAME).removeModifier(EModifiers.WALK);
                    this.SESSION_MAP.remove(NAME);
                }, 20L
        ).getTaskId();
        this.SESSION_MAP.put(NAME, TASK_ID);
    }
}