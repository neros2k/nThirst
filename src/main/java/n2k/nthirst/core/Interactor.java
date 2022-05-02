package n2k.nthirst.core;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.ISaver;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.core.presenter.EventPresenter;
import n2k.nthirst.core.presenter.WalkPresenter;
import n2k.nthirst.nThirst;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public final class Interactor implements IInteractor {
    private final Map<String, IEngine> ENGINE_MAP;
    private final List<APresenter> PRESENTER_LIST;
    private final ISaver SAVER;
    private final JavaPlugin PLUGIN;
    public Interactor(JavaPlugin PLUGIN) {
        this.PLUGIN = PLUGIN;
        this.ENGINE_MAP = new HashMap<>();
        this.PRESENTER_LIST = new ArrayList<>();
        this.SAVER = new Saver(this);
    }
    @Override
    public void init() {
        this.PRESENTER_LIST.addAll(List.of(
                        new WalkPresenter(this),
                        new EventPresenter(this)
        ));
        this.PRESENTER_LIST.forEach(APresenter::init);
        this.SAVER.init();
    }
    @Override
    public void startEngine(Player PLAYER) {
        IEngine ENGINE = new Engine(this, PLAYER);
        ENGINE.init();
        ENGINE.setWaterLevel(this.SAVER.getByName(PLAYER.getName()));
        this.ENGINE_MAP.put(PLAYER.getName(), ENGINE);
        ENGINE.start();
    }
    @Override
    public void stopEngine(String NAME) {
        IEngine ENGINE = this.ENGINE_MAP.get(NAME);
        this.SAVER.save(NAME, ENGINE.getWaterLevel());
        ENGINE.stop();
    }
    @Override
    public IEngine getEngine(String NAME) {
        return this.ENGINE_MAP.get(NAME);
    }
    @Override
    public JavaPlugin getPlugin() {
        return this.PLUGIN;
    }
    @Override
    public ConfigModel getConfig() {
        return ((nThirst) this.getPlugin()).getJsonConfig();
    }
}
