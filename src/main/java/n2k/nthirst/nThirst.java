package n2k.nthirst;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.core.Interactor;
import neros2k.jcapi.JCApi;
import neros2k.jcapi.JSONConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.Optional;
public final class nThirst extends JavaPlugin {
    private final IInteractor INTERACTOR;
    private JSONConfig<ConfigModel> JSON_CONFIG;
    public nThirst() {
        this.INTERACTOR = new Interactor(this);
    }
    @Override
    public void onEnable() {
        List.of("nThirst v1.0 by n2k",
                "GitHub: https://github.com/neros2k",
                "Discord: n2k#9665").forEach(this.getLogger()::info);
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.getLogger().info("Hooked into PlaceholderAPI");
        }
        if(Bukkit.getPluginManager().isPluginEnabled("JSONConfigAPI")) {
            Optional<JSONConfig<ConfigModel>> JSON_CONFIG_OPT = JCApi.getNew(
                    this, ConfigModel.class, "config.json");
            if(JSON_CONFIG_OPT.isPresent()) {
                this.JSON_CONFIG = JSON_CONFIG_OPT.get();
                this.JSON_CONFIG.reload();
            } else return;
            this.INTERACTOR.init();
        }
    }
    public ConfigModel getJsonConfig() {
        assert this.JSON_CONFIG != null;
        return this.JSON_CONFIG.getJson();
    }
}
