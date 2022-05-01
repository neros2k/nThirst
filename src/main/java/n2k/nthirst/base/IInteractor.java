package n2k.nthirst.base;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
public interface IInteractor extends IInitializable {
    void startEngine(Player PLAYER);
    void stopEngine(IEngine ENGINE);
    JavaPlugin getPlugin();
}
