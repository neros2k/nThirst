package n2k_.nthirst.base;
public abstract class APresenter implements IInitializable {
    private final IInteractor INTERACTOR;
    public APresenter(IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    public IInteractor getInteractor() {
        return this.INTERACTOR;
    }
}
