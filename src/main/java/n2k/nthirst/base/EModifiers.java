package n2k.nthirst.base;
public enum EModifiers {
    SHOW_ACTION_BAR((ENGINE) -> 0F, 200L),
    WALK((ENGINE) -> -0.001F);
    private final IModifier MODIFIER;
    private final Long DURATION;
    EModifiers(IModifier MODIFIER, Long DURATION) {
        this.MODIFIER = MODIFIER;
        this.DURATION = DURATION;
    }
    EModifiers(IModifier MODIFIER) {
        this(MODIFIER, 0L);
    }
    public IModifier getModifier() {
        return this.MODIFIER;
    }
    public Long getDuration() {
        return this.DURATION;
    }
}
