package n2k.nthirst.core;
import n2k.nthirst.base.EModifiers;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.ModifierData;
import n2k.nthirst.base.model.ConfigModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public final class Engine implements IEngine {
    private final List<ModifierData> MODIFIER_LIST;
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
        Arrays.stream(EModifiers.values()).forEach((MODIFIER) -> {
            ModifierData DATA = MODIFIER.getModifier().getData(this, null);
            if(DATA.isPermanent()) this.addActiveModifier(DATA);
        });
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
        if(this.isDisabledGamemode()) return;
        ConfigModel MODEL = this.getInteractor().getConfig();
        final Float[] FINAL_RESULT = {0F};
        this.MODIFIER_LIST.forEach((MODIFIER) -> FINAL_RESULT[0] = MODIFIER.getValue());
        this.PREV_WATER_LEVEL = this.WATER_LEVEL;
        this.addWaterLevel(FINAL_RESULT[0]);
        if(!EModifiers.ACTION_BAR.getModifier().getData(this, null).isPermanent() && MODEL.ENABLE_AB) {
            String VISIBILITY = MODEL.VISIBILITY;
            if(!Objects.equals(String.format(VISIBILITY, this.WATER_LEVEL),
                               String.format(VISIBILITY, this.PREV_WATER_LEVEL))) {
                this.addActiveModifier(EModifiers.ACTION_BAR);
            }
        }
        if(this.WATER_LEVEL <= MODEL.CRITICAL_WATER_LEVEL) {
            Arrays.stream(MODEL.CRITICAL_LEVEL_EFFECTS).forEach(EFFECT ->
                    this.PLAYER.addPotionEffect(new PotionEffect(
                    Objects.requireNonNull(PotionEffectType.getByName(EFFECT.TYPE)),
                            2, EFFECT.AMPLIFIER))
            );
        }
    }
    @Override
    public void setWaterLevel(Float NEW_LEVEL) {
        this.WATER_LEVEL = NEW_LEVEL;
    }
    @Override
    public void addWaterLevel(Float VALUE) {
        float RESULT = this.WATER_LEVEL + VALUE;
        if(RESULT > (float) this.getInteractor().getConfig().MAX_WATER_LEVEL) return;
        this.setWaterLevel(RESULT);
    }
    @Override
    public void addActiveModifier(@NotNull EModifiers EMODIFIER) {
        this.addActiveModifier(EMODIFIER.getModifier().getData(this, null));
    }
    @Override
    public void addActiveModifier(ModifierData MODIFIER) {
        this.MODIFIER_LIST.add(MODIFIER);
        if(MODIFIER.getDuration() != 0) {
            Bukkit.getScheduler().runTaskLater(
                    this.getInteractor().getPlugin(),
                    () -> this.removeModifier(MODIFIER),
                    MODIFIER.getDuration()
            );
        }
    }
    @Override
    public void removeModifier(@NotNull ModifierData MODIFIER) {
        if(!MODIFIER.isPermanent()) {
            this.MODIFIER_LIST.remove(MODIFIER);
        }
    }
    @Override
    public Float getWaterLevel() {
        return this.WATER_LEVEL;
    }
    @Override
    public List<ModifierData> getModifierList() {
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
    @NotNull
    public Boolean isDisabledGamemode() {
        return List.of(this.INTERACTOR.getConfig().DISABLED_GAME_MODES).contains(this.PLAYER.getGameMode().name());
    }
}
