package n2k.nthirst.base;
import java.util.List;
public interface IEngine {
    void setWaterLevel(Float NEW_LEVEL);
    void addActiveModifier(IModifier MODIFIER);
    void removeModifier(IModifier MODIFIER);
    void tick();
    Float getWaterLevel();
    List<IModifier> getModifierList();
}
