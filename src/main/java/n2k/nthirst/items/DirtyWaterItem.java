package n2k.nthirst.items;
import n2k.nthirst.base.AWaterItem;
import n2k.nthirst.base.model.ConfigModel;
import org.jetbrains.annotations.NotNull;
public class DirtyWaterItem extends AWaterItem {
    public DirtyWaterItem(@NotNull ConfigModel MODEL) {
        super(MODEL.CLEAR_WATER);
    }
}
