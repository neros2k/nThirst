package n2k.nthirst.core;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;
public class Interactor implements IInteractor {
    private final Map<String, IEngine> ENGINE_MAP;
    private final JavaPlugin PLUGIN;
    public Interactor(JavaPlugin PLUGIN) {
        this.ENGINE_MAP = new HashMap<>();
        this.PLUGIN = PLUGIN;
    }
    @Override
    public void init() {

    }
    @Override
    public void startEngine(Player PLAYER) {
        IEngine ENGINE = new Engine(this, PLAYER);
        this.ENGINE_MAP.put(PLAYER.getName(), ENGINE);
        ENGINE.start();
    }
    @Override
    public void stopEngine(String NAME) {
        this.ENGINE_MAP.get(NAME).stop();
    }
    @Override
    public IEngine getEngine(String NAME) {
        return this.ENGINE_MAP.get(NAME);
    }
    @Override
    public JavaPlugin getPlugin() {
        return this.PLUGIN;
    }
}
