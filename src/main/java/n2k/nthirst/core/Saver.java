package n2k.nthirst.core;
import n2k.nthirst.utils.SQLite;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.ISaver;
import org.jetbrains.annotations.NotNull;
public final class Saver implements ISaver {
    private final IInteractor INTERACTOR;
    private SQLite SQ_LITE;
    public Saver(@NotNull IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    @Override
    public void init() {
        this.SQ_LITE = new SQLite(INTERACTOR.getPlugin(), INTERACTOR.getConfig().DATAFILE_NAME , INTERACTOR.getConfig().TABLE_NAME);
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
