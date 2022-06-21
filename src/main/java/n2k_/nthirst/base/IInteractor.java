package n2k_.nthirst.base;
import n2k_.nthirst.base.model.main.ConfigModel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
public interface IInteractor extends IInitializable {
    void startEngine(Player PLAYER);
    void stopEngine(String NAME);
    IEngine getEngine(String NAME);
    JavaPlugin getPlugin();
    ConfigModel getConfig();
}
