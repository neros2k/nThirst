package n2k_.nthirst.items;
import n2k_.nthirst.base.AWaterItem;
import n2k_.nthirst.base.model.main.ConfigModel;
import org.jetbrains.annotations.NotNull;
public class ClearWaterItem extends AWaterItem {
    public ClearWaterItem(@NotNull ConfigModel MODEL) {
        super(MODEL.CLEAR_WATER);
    }
}
