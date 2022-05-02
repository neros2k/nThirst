package n2k.nthirst.base;
import org.bukkit.event.Listener;
public abstract class APresenter implements IInitializable, Listener {
    private final IInteractor INTERACTOR;
    public APresenter(IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    public IInteractor getInteractor() {
        return this.INTERACTOR;
    }
}
