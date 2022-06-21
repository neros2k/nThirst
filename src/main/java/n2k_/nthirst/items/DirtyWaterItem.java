package n2k_.nthirst.items;
import n2k_.nthirst.base.AbstractWaterItem;
import n2k_.nthirst.base.model.main.ItemsModel;
import org.jetbrains.annotations.NotNull;
public final class DirtyWaterItem extends AbstractWaterItem {
    public DirtyWaterItem(@NotNull ItemsModel MODEL) {
        super(MODEL.DIRTY_WATER);
    }
}
