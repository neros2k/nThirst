package n2k.nthirst.base;
public class ModifierData {
    private final Float VALUE;
    private final Long DURATION;
    private final Boolean PERMANENT;
    public ModifierData(Float VALUE, Long DURATION, Boolean PERMANENT) {
        this.VALUE = VALUE;
        this.DURATION = DURATION;
        this.PERMANENT = PERMANENT;
    }
    public Float getValue() {
        return this.VALUE;
    }
    public Long getDuration() {
        return this.DURATION;
    }
    public Boolean isPermanent() {
        return this.PERMANENT;
    }
}
