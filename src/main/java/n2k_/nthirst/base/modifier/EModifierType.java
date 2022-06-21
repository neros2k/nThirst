package n2k_.nthirst.base.modifier;
import n2k_.nthirst.utils.ActionBar;
import n2k_.nthirst.base.IEngine;
import n2k_.nthirst.base.model.main.MainModel;
import n2k_.nthirst.base.model.sub.TypeModel;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
public enum EModifierType {
    WALK(ENGINE -> ENGINE.getInteractor().getModifiersConfig().WALK.VALUE,
         ENGINE -> ENGINE.getInteractor().getModifiersConfig().WALK.DURATION,
         ENGINE -> ENGINE.getInteractor().getModifiersConfig().WALK.PERMANENT),
    ACTION_BAR(ENGINE -> {
        MainModel MODEL = ENGINE.getInteractor().getMainConfig();
        String FORMAT = String.format(MODEL.AB_FORMAT, ENGINE.getWaterLevel());
        ENGINE.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ActionBar.get(FORMAT, ENGINE)));
        return ENGINE.getInteractor().getModifiersConfig().ACTION_BAR.VALUE;
    }, ENGINE -> ENGINE.getInteractor().getModifiersConfig().ACTION_BAR.DURATION,
       ENGINE -> ENGINE.getInteractor().getModifiersConfig().ACTION_BAR.PERMANENT),
    BIOME(ENGINE -> {
        Modifier MODIFIER = new Modifier(null,
                ARG_ENGINE -> EModifierType.getBiomeConfig(ENGINE).VALUE,
                ARG_ENGINE -> EModifierType.getBiomeConfig(ENGINE).DURATION,
                ARG_ENGINE -> false
        );
        if(!ENGINE.containsModifier(MODIFIER) && EModifierType.getBiomeConfig(ENGINE) != null) {
            ENGINE.addModifier(MODIFIER);
        }
        return 0F;
    }, ENGINE -> 0L, ENGINE -> true),
    FOOD(),
    SET();
    private final Modifier DEFAULT_MODIFIER;
    EModifierType(IValue VALUE, IDuration DURATION, IPermanent PERMANENT) {
        this.DEFAULT_MODIFIER = new Modifier(this, VALUE, DURATION, PERMANENT);
    }
    EModifierType() {
        this(ENGINE -> 0F, ENGINE -> 0L, ENGINE -> false);
    }
    public Modifier getDefaultModifier() {
        return this.DEFAULT_MODIFIER;
    }
    private static TypeModel getBiomeConfig(@NotNull IEngine ENGINE) {
        AtomicReference<TypeModel> RETURN_MODEL = new AtomicReference<>(null);
        Arrays.stream(ENGINE.getInteractor().getModifiersConfig().BIOMES).forEach(BIOME -> {
            if(BIOME.TYPE.equals(ENGINE.getPlayer().getLocation().getBlock().getBiome().name())) {
                RETURN_MODEL.set(BIOME);
            }
        });
        return RETURN_MODEL.get();
    }
}
