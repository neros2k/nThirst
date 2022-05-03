package n2k.nthirst.base;
import n2k.nthirst.base.modifier.EModifierType;
import n2k.nthirst.base.modifier.Modifier;
import org.bukkit.entity.Player;
import java.util.List;
public interface IEngine extends IInitializable {
    void start();
    void stop();
    void tick();
    void setWaterLevel(Float NEW_LEVEL);
    void addWaterLevel(Float VALUE);
    void addModifier(Modifier EMODIFIER);
    void addModifier(EModifierType MODIFIER);
    void removeModifier(Modifier EMODIFIER);
    void removeModifier(EModifierType MODIFIER);
    Float getWaterLevel();
    List<Modifier> getModifierList();
    IInteractor getInteractor();
    Player getPlayer();
    Boolean isDisabledGamemode();
}
