package n2k.nthirst.core;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Engine implements IEngine {
    private final List<EModifiers> MODIFIER_LIST;
    private final IInteractor INTERACTOR;
    private final Player PLAYER;
    private Integer TASK_ID;
    private Float PREV_WATER_LEVEL;
    private Float WATER_LEVEL;
    public Engine(IInteractor INTERACTOR, Player PLAYER) {
        this.MODIFIER_LIST = new ArrayList<>();
        this.INTERACTOR = INTERACTOR;
        this.PLAYER = PLAYER;
        this.PREV_WATER_LEVEL = 0F;
        this.WATER_LEVEL = 0F;
    }
    @Override
    public void init() {

    }
    @Override
    public void start() {
        this.TASK_ID = Bukkit.getScheduler().runTaskTimer(
                this.getInteractor().getPlugin(),
                this::tick, 0L,1L
        ).getTaskId();
    }
    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(this.TASK_ID);
    }
    @Override
    public void tick() {
        final Float[] FINAL_RESULT = {0F};
        this.MODIFIER_LIST.forEach((MODIFIER) -> FINAL_RESULT[0] = MODIFIER.getModifier().getValue(this));
        this.PREV_WATER_LEVEL = this.WATER_LEVEL;
        this.setWaterLevel(this.WATER_LEVEL + FINAL_RESULT[0]);
        String VISIBILITY = "%.1f";
        if(!Objects.equals(String.format(VISIBILITY, this.WATER_LEVEL),
                           String.format(VISIBILITY, this.PREV_WATER_LEVEL))) {
            this.addActiveModifier(EModifiers.SHOW_ACTION_BAR);
        }
    }
    @Override
    public void setWaterLevel(Float NEW_LEVEL) {
        this.WATER_LEVEL = NEW_LEVEL;
    }
    @Override
    public void addActiveModifier(EModifiers MODIFIER) {
        this.MODIFIER_LIST.add(MODIFIER);
        if(MODIFIER.getDuration() != null) {
            Bukkit.getScheduler().runTaskLater(
                    this.getInteractor().getPlugin(),
                    () -> this.removeModifier(MODIFIER),
                    MODIFIER.getDuration().get(this)
            );
        }
    }
    @Override
    public void removeModifier(EModifiers MODIFIER) {
        this.MODIFIER_LIST.remove(MODIFIER);
    }
    @Override
    public Float getWaterLevel() {
        return this.WATER_LEVEL;
    }
    @Override
    public List<EModifiers> getModifierList() {
        return this.MODIFIER_LIST;
    }
    @Override
    public IInteractor getInteractor() {
        return this.INTERACTOR;
    }
    @Override
    public Player getPlayer() {
        return this.PLAYER;
    }
}
