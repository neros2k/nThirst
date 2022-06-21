package n2k_.nthirst.items;
import n2k_.nthirst.base.AWaterItem;
import n2k_.nthirst.base.model.main.ItemsModel;
import org.jetbrains.annotations.NotNull;
public class DirtyWaterItem extends AWaterItem {
    public DirtyWaterItem(@NotNull ItemsModel MODEL) {
        super(MODEL.DIRTY_WATER);
    }
}
