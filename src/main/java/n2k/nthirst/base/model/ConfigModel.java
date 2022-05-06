package n2k.nthirst.base.model;
public final class ConfigModel {
    public boolean ENABLE_AB;
    public boolean ENABLE_CRITICAL_VALUE;
    public boolean RESET_ON_DEATH;
    public boolean ENABLE_DIRTY_WATER;
    public String AB_MESSAGE;
    public String DATAFILE_NAME;
    public String TABLE_NAME;
    public String AB_FORMAT;
    public String VISIBILITY;
    public int DEFAULT_WATER_LEVEL;
    public int MAX_WATER_LEVEL;
    public int CRITICAL_WATER_LEVEL;
    public EffectModel[] CRITICAL_LEVEL_EFFECTS;
    public EffectModel[] DIRTY_WATER_EFFECTS;
    public EffectModel[] CLEAR_WATER_EFFECTS;
    public String[] DISABLED_GAME_MODES;
    public ModifiersModel MODIFIERS;
    public MessagesModel MESSAGES;
}
