package n2k.nthirst.core;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
public class Engine implements IEngine {
    private final IInteractor INTERACTOR;
    private final Player PLAYER;
    private Integer TASK_ID;
    private List<EModifiers> MODIFIER_LIST;
    private Float WATER_LEVEL;
    public Engine(IInteractor INTERACTOR, Player PLAYER) {
        this.INTERACTOR = INTERACTOR;
        this.PLAYER = PLAYER;
    }
    @Override
    public void init() {
        this.MODIFIER_LIST = new ArrayList<>();
        this.setWaterLevel(100F);
    }
    @Override
    public void start() {
        this.TASK_ID = Bukkit.getScheduler().runTaskTimerAsynchronously(
                this.getInteractor().getPlugin(),
                this::tick, 0L,5L
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
        this.setWaterLevel(this.getWaterLevel() + FINAL_RESULT[0]);
    }
    @Override
    public void setWaterLevel(Float NEW_LEVEL) {
        this.WATER_LEVEL = NEW_LEVEL;
    }
    @Override
    public void addActiveModifier(EModifiers MODIFIER) {
        this.MODIFIER_LIST.add(MODIFIER);
        if(MODIFIER.getDuration() != 0) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(
                    this.getInteractor().getPlugin(),
                    () -> this.removeModifier(MODIFIER),
                    MODIFIER.getDuration()
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
