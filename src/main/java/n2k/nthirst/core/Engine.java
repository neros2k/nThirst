package n2k.nthirst.core;
import n2k.nthirst.base.modifier.EModifierType;
import n2k.nthirst.base.IEngine;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.base.modifier.Modifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
public final class Engine implements IEngine {
    private final List<Modifier> MODIFIER_LIST;
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
        Arrays.stream(EModifierType.values()).forEach(TYPE -> {
            if(TYPE.getDefaultModifier().getPermanent().get(this)) this.addModifier(TYPE);
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
        this.MODIFIER_LIST.forEach(MODIFIER -> FINAL_RESULT[0] += MODIFIER.getValue().get(this));
        this.PREV_WATER_LEVEL = this.WATER_LEVEL;
        this.addWaterLevel(FINAL_RESULT[0]);
        if(MODEL.ENABLE_AB && !this.containsModifier(EModifierType.ACTION_BAR)) {
            String VISIBILITY = MODEL.VISIBILITY;
            if(!Objects.equals(String.format(VISIBILITY, this.WATER_LEVEL),
                               String.format(VISIBILITY, this.PREV_WATER_LEVEL))) {
                this.addModifier(EModifierType.ACTION_BAR);
            }
        }
        if(this.WATER_LEVEL <= MODEL.CRITICAL_WATER_LEVEL && MODEL.ENABLE_CRITICAL_LEVEL) {
            Arrays.stream(MODEL.CRITICAL_LEVEL_EFFECTS).forEach(EFFECT ->
                    this.PLAYER.addPotionEffect(new PotionEffect(
                        Objects.requireNonNull(
                                PotionEffectType.getByName(EFFECT.TYPE)), EFFECT.DURATION, EFFECT.AMPLIFIER)
                    )
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
        float MAX_LEVEL = (float) this.getInteractor().getConfig().MAX_WATER_LEVEL;
        if(RESULT > MAX_LEVEL && !this.containsModifier(EModifierType.SET) && this.WATER_LEVEL <= MAX_LEVEL) RESULT = MAX_LEVEL;
        this.setWaterLevel(RESULT);
    }
    @Override
    public void addModifier(Modifier MODIFIER) {
        this.MODIFIER_LIST.add(MODIFIER);
        Long DURATION = MODIFIER.getDuration().get(this);
        if(DURATION != 0L) {
            Bukkit.getScheduler().runTaskLater(
                    this.getInteractor().getPlugin(), () -> this.removeModifier(MODIFIER), DURATION
            );
        }
    }
    @Override
    public void addModifier(@NotNull EModifierType TYPE) {
        this.addModifier(TYPE.getDefaultModifier());
    }
    @Override
    public void removeModifier(Modifier MODIFIER) {
        this.MODIFIER_LIST.remove(MODIFIER);
    }
    @Override
    public void removeModifier(@NotNull EModifierType TYPE) {
        this.MODIFIER_LIST.forEach(LIST_MODIFIER -> {
            if(LIST_MODIFIER.getType() == TYPE) this.removeModifier(LIST_MODIFIER);
        });
    }
    @Contract(pure = true) @NotNull @Override
    public Boolean containsModifier(Modifier MODIFIER) {
        return this.MODIFIER_LIST.contains(MODIFIER);
    }
    @Contract(pure = true) @Override
    public Boolean containsModifier(EModifierType TYPE) {
        AtomicReference<Boolean> RETURN = new AtomicReference<>(false);
        this.MODIFIER_LIST.forEach(LIST_MODIFIER -> {
            if(LIST_MODIFIER.getType() == TYPE) RETURN.set(true);
        });
        return RETURN.get();
    }
    @Override
    public Float getWaterLevel() {
        return this.WATER_LEVEL;
    }
    @Override
    public List<Modifier> getModifierList() {
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
