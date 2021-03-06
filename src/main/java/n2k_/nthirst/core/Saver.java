package n2k_.nthirst.core;
import n2k_.nthirst.utils.SQLite;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.ISaver;
import org.jetbrains.annotations.NotNull;
public final class Saver implements ISaver {
    private final IInteractor INTERACTOR;
    private SQLite SQ_LITE;
    public Saver(@NotNull IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    @Override
    public void init() {
        this.SQ_LITE = new SQLite(INTERACTOR.getPlugin(), INTERACTOR.getMainConfig().DATAFILE_NAME , INTERACTOR.getMainConfig().TABLE_NAME);
        this.SQ_LITE.init();
    }
    @Override
    public void save(String NAME, Float VALUE) {
        this.SQ_LITE.saveValue(NAME, VALUE);
    }
    @Override @NotNull
    public Float getByName(String NAME) {
        return this.SQ_LITE.findValue(NAME);
    }
}
