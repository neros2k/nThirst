package n2k_.nthirst.base.modifier;
public class Modifier {
    private final EModifierType TYPE;
    private final IValue VALUE;
    private final IDuration DURATION;
    private final IPermanent PERMANENT;
    public Modifier(EModifierType TYPE, IValue VALUE, IDuration DURATION, IPermanent PERMANENT) {
        this.TYPE = TYPE;
        this.VALUE = VALUE;
        this.DURATION = DURATION;
        this.PERMANENT = PERMANENT;
    }
    public EModifierType getType() {
        return this.TYPE;
    }
    public IValue getValue() {
        return this.VALUE;
    }
    public IDuration getDuration() {
        return this.DURATION;
    }
    public IPermanent getPermanent() {
        return this.PERMANENT;
    }
}
