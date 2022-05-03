package n2k.nthirst.base;
import org.bukkit.entity.Player;
import java.util.List;
public interface IEngine extends IInitializable {
    void start();
    void stop();
    void tick();
    void setWaterLevel(Float NEW_LEVEL);
    void addWaterLevel(Float VALUE);
    void addActiveModifier(EModifiers EMODIFIER);
    void addActiveModifier(ModifierData MODIFIER);
    void removeModifier(ModifierData MODIFIER);
    Float getWaterLevel();
    List<ModifierData> getModifierList();
    IInteractor getInteractor();
    Player getPlayer();
    Boolean isDisabledGamemode();
}
