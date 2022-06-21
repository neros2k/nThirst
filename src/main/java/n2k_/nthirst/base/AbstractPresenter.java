package n2k_.nthirst.base;
public abstract class AbstractPresenter implements IInitializable {
    private final IInteractor INTERACTOR;
    public AbstractPresenter(IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    public IInteractor getInteractor() {
        return this.INTERACTOR;
    }
}
