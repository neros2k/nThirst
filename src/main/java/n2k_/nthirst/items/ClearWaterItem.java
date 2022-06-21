package n2k_.nthirst.items;
import n2k_.nthirst.base.AbstractWaterItem;
import n2k_.nthirst.base.model.main.ItemsModel;
import org.jetbrains.annotations.NotNull;
public final class ClearWaterItem extends AbstractWaterItem {
    public ClearWaterItem(@NotNull ItemsModel MODEL) {
        super(MODEL.CLEAR_WATER);
    }
}
