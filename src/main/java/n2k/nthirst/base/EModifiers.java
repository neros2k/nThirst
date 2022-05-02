package n2k.nthirst.base;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
public enum EModifiers {
    ACTION_BAR((ENGINE, ARGS) -> {
        String TEXT = String.format("%.1f", ENGINE.getWaterLevel());
        ENGINE.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(TEXT));
        return 0F;
    }, ENGINE -> 20L, ENGINE -> false),
    WALK((ENGINE, ARGS) -> -0.001F, ENGINE -> 5L);
    private final IModifier MODIFIER;
    private final IDuration DURATION;
    private final IPermanent PERMANENT;
    EModifiers(IModifier MODIFIER, IDuration DURATION, IPermanent PERMANENT) {
        this.MODIFIER = MODIFIER;
        this.DURATION = DURATION;
        this.PERMANENT = PERMANENT;
    }
    EModifiers(IModifier MODIFIER, IDuration DURATION) {
        this(MODIFIER, DURATION, ENGINE -> false);
    }
    EModifiers(IModifier MODIFIER) {
        this(MODIFIER, null, ENGINE -> false);
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
