package n2k.nthirst.base;
import org.bukkit.entity.Player;
import java.util.List;
public interface IEngine extends IInitializable {
    void start();
    void stop();
    void tick();
    void setWaterLevel(Float NEW_LEVEL);
    void addWaterLevel(Float VALUE);
    void addActiveModifier(EModifiers MODIFIER);
    void removeModifier(EModifiers MODIFIER);
    Float getWaterLevel();
    List<EModifiers> getModifierList();
    IInteractor getInteractor();
    Player getPlayer();
    Boolean isDisabledGamemode();
}
