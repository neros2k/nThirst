package n2k_.nthirst.items;
import n2k_.nthirst.base.AWaterItem;
import n2k_.nthirst.base.model.ConfigModel;
import org.jetbrains.annotations.NotNull;
public class DirtyWaterItem extends AWaterItem {
    public DirtyWaterItem(@NotNull ConfigModel MODEL) {
        super(MODEL.DIRTY_WATER);
    }
}
