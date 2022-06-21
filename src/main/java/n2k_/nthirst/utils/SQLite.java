package n2k_.nthirst.utils;
import n2k_.nthirst.base.ISQLite;
import n2k_.nthirst.nThirst;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public final class SQLite implements ISQLite {
    private final JavaPlugin PLUGIN;
    private final String DATABASE_NAME;
    private final String TABLE_NAME;
    private Connection CONNECTION;
    public SQLite(JavaPlugin PLUGIN, String DATABASE_NAME, String TABLE_NAME) {
        this.PLUGIN = PLUGIN;
        this.DATABASE_NAME = DATABASE_NAME;
        this.TABLE_NAME = TABLE_NAME;
    }
    @Override
    public void init() {
        this.CONNECTION = getSqlConnection();
        try {
            assert this.CONNECTION != null;
            Statement STATEMENT = this.CONNECTION.createStatement();
            STATEMENT.executeUpdate("CREATE TABLE IF NOT EXISTS " + this.TABLE_NAME + " (`player` varchar(32) NOT NULL,`value` real(10) NOT NULL,PRIMARY KEY (`player`));");
            STATEMENT.close();
            PreparedStatement PREPARED_STATEMENT = this.CONNECTION.prepareStatement("SELECT * FROM " + this.TABLE_NAME + " WHERE player = ?");
            ResultSet RESULT_SET = PREPARED_STATEMENT.executeQuery();
            PREPARED_STATEMENT.close();
            if(RESULT_SET != null) RESULT_SET.close();
        }
        catch(SQLException EXCEPTION) {
            EXCEPTION.printStackTrace();
        }
    }
    @Override @Nullable
    public Connection getSqlConnection() {
        Logger LOGGER = this.PLUGIN.getLogger();
        if(!new File(this.PLUGIN.getDataFolder(), this.DATABASE_NAME).exists()) {
            this.PLUGIN.saveResource(this.DATABASE_NAME, false);
        }
        File DATA_FOLDER = new File(this.PLUGIN.getDataFolder(), this.DATABASE_NAME);
        if(!DATA_FOLDER.exists()) this.PLUGIN.saveResource(DATA_FOLDER.getName(), false);
        try {
            if(this.CONNECTION != null && !this.CONNECTION.isClosed()) return this.CONNECTION;
            Class.forName("org.sqlite.JDBC");
            this.CONNECTION = DriverManager.getConnection("jdbc:sqlite:" + DATA_FOLDER);
            return this.CONNECTION;
        }
        catch(SQLException EXCEPTION) {
            LOGGER.log(Level.SEVERE, "SQLite exception on initialize", EXCEPTION);
        }
        catch(ClassNotFoundException EXCEPTION) {
            LOGGER.log(Level.SEVERE, "You need the SQLite JDBC library");
        }
        return null;
    }
    @Override @NotNull
    public Float findValue(String PLAYER_NAME) {
        Logger LOGGER = this.PLUGIN.getLogger();
        Connection CONNECTION = this.getSqlConnection();
        assert CONNECTION != null;
        try {
            PreparedStatement PREPARED_STATEMENT = CONNECTION.prepareStatement("SELECT * FROM " + this.TABLE_NAME + " WHERE player = '"+PLAYER_NAME+"';");
            ResultSet RESULT_SET = PREPARED_STATEMENT.executeQuery();
            while(RESULT_SET.next()) {
                if(RESULT_SET.getString("player").equalsIgnoreCase(PLAYER_NAME.toLowerCase())) {
                    return RESULT_SET.getFloat("value");
                }
            }
            try {
                PREPARED_STATEMENT.close();
                CONNECTION.close();
            }
            catch(SQLException EXCEPTION) {
                LOGGER.log(Level.SEVERE, "Failed to close connection: ", EXCEPTION);
            }
        }
        catch(SQLException EXCEPTION) {
            LOGGER.log(Level.SEVERE, "Couldn't execute statement: ", EXCEPTION);
        }
        return (float) ((nThirst) this.PLUGIN).getMainConfig().getJson().DEFAULT_WATER_LEVEL;
    }
    @Override
    public void saveValue(@NotNull String PLAYER_NAME, Float VALUE) {
        Logger LOGGER = this.PLUGIN.getLogger();
        Connection CONNECTION = this.getSqlConnection();
        assert CONNECTION != null;
        try {
            PreparedStatement PREPARED_STATEMENT = CONNECTION.prepareStatement("REPLACE INTO " + this.TABLE_NAME + " (player,value) VALUES(?,?)");
            PREPARED_STATEMENT.setString(1, PLAYER_NAME.toLowerCase());
            PREPARED_STATEMENT.setFloat(2, VALUE);
            PREPARED_STATEMENT.executeUpdate();
            try {
                PREPARED_STATEMENT.close();
                CONNECTION.close();
            }
            catch(SQLException EXCEPTION) {
                LOGGER.log(Level.SEVERE, "Failed to close connection: ", EXCEPTION);
            }
        }
        catch(SQLException EXCEPTION) {
            LOGGER.log(Level.SEVERE, "Couldn't execute statement: ", EXCEPTION);
        }
    }
}