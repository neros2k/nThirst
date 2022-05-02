package n2k.nthirst.base;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.core.ActionBarDecorator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
public enum EModifiers {
    BIOMES((ENGINE, ARGS) -> {
        ConfigModel MODEL = ENGINE.getInteractor().getConfig();
        AtomicReference<Float> RESULT = new AtomicReference<>(0F);
        String BIOME = ENGINE.getPlayer().getLocation().getBlock().getBiome().toString();
        Arrays.stream(MODEL.MODIFIERS.BIOMES).forEach((BIOMES) -> {if(BIOMES.TYPE.equals(BIOME)) RESULT.set(BIOMES.VALUE);});
        return RESULT.get();
    }, null, ENGINE -> true),
    FOOD((ENGINE, ARGS) -> {
        ConfigModel MODEL = ENGINE.getInteractor().getConfig();
        AtomicReference<Float> RESULT = new AtomicReference<>(0F);
        Arrays.stream(MODEL.MODIFIERS.FOOD).forEach((FOOD) -> {if(FOOD.TYPE.equals(ARGS[0])) RESULT.set(FOOD.VALUE);});
        return RESULT.get();
    }, null, ENGINE -> false),
    ACTION_BAR((ENGINE, ARGS) -> {
        ConfigModel MODEL = ENGINE.getInteractor().getConfig();
        String FORMAT = String.format(MODEL.AB_FORMAT, ENGINE.getWaterLevel());
        ENGINE.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ActionBarDecorator.get(FORMAT, ENGINE)));
        return MODEL.MODIFIERS.ACTION_BAR.VALUE;
    }, ENGINE -> ENGINE.getInteractor().getConfig().MODIFIERS.ACTION_BAR.DURATION,
       ENGINE -> ENGINE.getInteractor().getConfig().MODIFIERS.ACTION_BAR.PERMANENT),
    WALK((ENGINE, ARGS) -> ENGINE.getInteractor().getConfig().MODIFIERS.WALK.VALUE,
          ENGINE -> ENGINE.getInteractor().getConfig().MODIFIERS.WALK.DURATION,
          ENGINE -> ENGINE.getInteractor().getConfig().MODIFIERS.WALK.PERMANENT);
    private final IModifier MODIFIER;
    private final IDuration DURATION;
    private final IPermanent PERMANENT;
    EModifiers(IModifier MODIFIER, IDuration DURATION, IPermanent PERMANENT) {
        this.MODIFIER = MODIFIER;
        this.DURATION = DURATION;
        this.PERMANENT = PERMANENT;
    }
    public IModifier getModifier() {
        return this.MODIFIER;
    }
    public IDuration getDuration() {
        return this.DURATION;
    }
    public IPermanent isPermanent() {
        return this.PERMANENT;
    }
}
