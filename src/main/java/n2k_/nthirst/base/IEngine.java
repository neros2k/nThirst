package n2k_.nthirst.base;
import n2k_.nthirst.base.modifier.EModifierType;
import n2k_.nthirst.base.modifier.Modifier;
import org.bukkit.entity.Player;
import java.util.List;
public interface IEngine extends IInitializable {
    void start();
    void stop();
    void tick();
    void setWaterLevel(Float NEW_LEVEL);
    void addWaterLevel(Float VALUE);
    void addModifier(Modifier MODIFIER);
    void addModifier(EModifierType TYPE);
    void removeModifier(Modifier MODIFIER);
    void removeModifier(EModifierType TYPE);
    Boolean containsModifier(Modifier MODIFIER);
    Boolean containsModifier(EModifierType TYPE);
    Float getWaterLevel();
    List<Modifier> getModifierList();
    IInteractor getInteractor();
    Player getPlayer();
    Boolean isDisabledGamemode();
}
