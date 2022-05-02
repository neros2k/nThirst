package n2k.nthirst.base;
import java.sql.Connection;
public interface ISQLite extends IInitializable {
    Connection getSqlConnection();
    Float findValue(String PLAYER_NAME);
    void saveValue(String PLAYER_NAME, Float VALUE);
}
