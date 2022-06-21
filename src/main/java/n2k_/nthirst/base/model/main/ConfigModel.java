package n2k_.nthirst.base.model.main;
import n2k_.nthirst.base.model.sub.EffectModel;
public final class ConfigModel {
    public boolean ENABLE_AB;
    public boolean ENABLE_CRITICAL_LEVEL;
    public boolean RESET_ON_DEATH;
    public String AB_MESSAGE;
    public String DATAFILE_NAME;
    public String TABLE_NAME;
    public String AB_FORMAT;
    public String VISIBILITY;
    public int DEFAULT_WATER_LEVEL;
    public int MAX_WATER_LEVEL;
    public int CRITICAL_WATER_LEVEL;
    public int CLEAR_WATER_COOKING_TIME;
    public float CLEAR_WATER_EXPERIENCE;
    public EffectModel[] CRITICAL_LEVEL_EFFECTS;
    public String[] DISABLED_GAME_MODES;
    public String[] DISABLED_WORLDS;
}
