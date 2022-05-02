package n2k.nthirst.base;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
public enum EModifiers {
    SHOW_ACTION_BAR((ENGINE) -> {
        ENGINE.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ENGINE.getWaterLevel().toString()));
        return 0F;
    }, (ENGINE) -> 200L),
    WALK((ENGINE) -> -0.001F);
    private final IModifier MODIFIER;
    private final IDuration DURATION;
    EModifiers(IModifier MODIFIER, IDuration DURATION) {
        this.MODIFIER = MODIFIER;
        this.DURATION = DURATION;
    }
    EModifiers(IModifier MODIFIER) {
        this(MODIFIER, (ENGINE) -> null);
    }
    public IModifier getModifier() {
        return this.MODIFIER;
    }
    public IDuration getDuration() {
        return this.DURATION;
    }
}
