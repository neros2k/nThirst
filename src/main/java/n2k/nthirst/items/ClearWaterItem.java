package n2k.nthirst.items;
import n2k.nthirst.base.AWaterItem;
import n2k.nthirst.base.model.ConfigModel;
import org.jetbrains.annotations.NotNull;
public class ClearWaterItem extends AWaterItem {
    public ClearWaterItem(@NotNull ConfigModel MODEL) {
        super(MODEL.CLEAR_WATER);
    }
}
