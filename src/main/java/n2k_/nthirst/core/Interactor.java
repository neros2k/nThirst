package n2k_.nthirst.core;
import n2k_.nthirst.base.APresenter;
import n2k_.nthirst.base.IEngine;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.ISaver;
import n2k_.nthirst.base.model.ConfigModel;
import n2k_.nthirst.core.presenter.CommandPresenter;
import n2k_.nthirst.core.presenter.EventPresenter;
import n2k_.nthirst.core.presenter.WaterPresenter;
import n2k_.nthirst.nThirst;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
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
                        new EventPresenter(this),
                        new CommandPresenter(this),
                        new WaterPresenter(this)
        ));
        this.PRESENTER_LIST.forEach(APresenter::init);
        this.SAVER.init();
    }
    @Override
    public void startEngine(@NotNull Player PLAYER) {
        if(!this.ENGINE_MAP.containsKey(PLAYER.getName())) {
            IEngine ENGINE = new Engine(this, PLAYER);
            ENGINE.init();
            ENGINE.setWaterLevel(this.SAVER.getByName(PLAYER.getName()));
            this.ENGINE_MAP.put(PLAYER.getName(), ENGINE);
            ENGINE.start();
        }
    }
    @Override
    public void stopEngine(String NAME) {
        if(this.ENGINE_MAP.containsKey(NAME)) {
            IEngine ENGINE = this.ENGINE_MAP.get(NAME);
            this.SAVER.save(NAME, ENGINE.getWaterLevel());
            ENGINE.stop();
        }
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
        return ((nThirst) this.getPlugin()).getJsonConfig().getJson();
    }
}
