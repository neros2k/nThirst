package n2k.nthirst.base;
import n2k.nthirst.base.model.ConfigModel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
public interface IInteractor extends IInitializable {
    void startEngine(Player PLAYER);
    void stopEngine(String NAME);
    IEngine getEngine(String NAME);
    JavaPlugin getPlugin();
    ConfigModel getConfig();
}
