package n2k.nthirst;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.core.Interactor;
import neros2k.jcapi.JCApi;
import neros2k.jcapi.JSONConfig;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Optional;
public final class nThirst extends JavaPlugin {
    private final IInteractor INTERACTOR;
    private JSONConfig<ConfigModel> JSON_CONFIG;
    public nThirst() {
        this.INTERACTOR = new Interactor(this);
    }
    @Override
    public void onEnable() {
        this.INTERACTOR.init();
        Optional<JSONConfig<ConfigModel>> JSON_CONFIG_OPT = JCApi.getNew(
                this, ConfigModel.class, "config.json");
        JSON_CONFIG_OPT.ifPresent(configModelJSONConfig -> JSON_CONFIG = configModelJSONConfig);
    }
    public ConfigModel getJsonConfig() {
        assert this.JSON_CONFIG != null;
        return this.JSON_CONFIG.getJson();
    }
}
