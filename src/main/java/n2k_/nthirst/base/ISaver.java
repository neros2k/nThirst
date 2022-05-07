package n2k_.nthirst.base;
public interface ISaver extends IInitializable {
    void save(String NAME, Float VALUE);
    Float getByName(String NAME);
}
