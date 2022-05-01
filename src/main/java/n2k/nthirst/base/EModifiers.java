package n2k.nthirst.base;
public enum EModifiers {
    WALK((ENGINE, EVENT) -> -0.1F),
    ON_SUN((ENGINE, EVENT) -> -0.5F),
    IN_FIRE((ENGINE, EVENT) -> -3F);
    private final IModifier MODIFIER;
    EModifiers(IModifier MODIFIER) {
        this.MODIFIER = MODIFIER;
    }
    public IModifier getModifier() {
        return this.MODIFIER;
    }
}
