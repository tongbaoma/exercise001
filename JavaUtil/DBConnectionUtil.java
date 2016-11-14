package jp.microad.blade.batch.kizasi.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import jp.microad.blade.batch.common.Config;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * DB接続のプール
 *
 */
public class DBConnectionUtil {

    private static final int MAX_POOL_NUM = 10;
    private static ComboPooledDataSource cpdsCore = null;
    private static ComboPooledDataSource cpdsDrtgSlave = null;
    private static ComboPooledDataSource cpdsDrtgMaster = null;
    static {
        // set c3p0 log level
        Properties p = new Properties(System.getProperties());
        p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "INFO"); // Off or any other level
        System.setProperties(p);

        setupConnectionCore();
        setupConnectionDrtgSlave();
        setupConnectionDrtgMaster();
    }

    /**
     * core_master_db
     */
    public static void setupConnectionCore() {

        String driver = "com.mysql.jdbc.Driver";
        String user = Config.getProperty("db.core_master_db.user");
        String pass = Config.getProperty("db.core_master_db.password");
        String url = Config.getProperty("db.core_master_db.url");
        int maxPoolSize = Config.getInt("parallel_processing_count", MAX_POOL_NUM);

        // connection
        cpdsCore = new ComboPooledDataSource();
        try {
            cpdsCore.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        cpdsCore.setJdbcUrl(url);
        cpdsCore.setUser(user);
        cpdsCore.setPassword(pass);

        // config
        cpdsCore.setMaxPoolSize(maxPoolSize);
    }

    /**
     * drtg_slave
     */
    public static void setupConnectionDrtgSlave() {

        String driver = "com.mysql.jdbc.Driver";
        String user = Config.getProperty("db.drtg_slave_db.user");
        String pass = Config.getProperty("db.drtg_slave_db.password");
        String url = Config.getProperty("db.drtg_slave_db.url");
        int maxPoolSize = Config.getInt("parallel_processing_count", MAX_POOL_NUM);

        // connection
        cpdsDrtgSlave = new ComboPooledDataSource();
        try {
            cpdsDrtgSlave.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        cpdsDrtgSlave.setJdbcUrl(url);
        cpdsDrtgSlave.setUser(user);
        cpdsDrtgSlave.setPassword(pass);

        // config
        cpdsDrtgSlave.setMaxPoolSize(maxPoolSize);
    }

    /**
     * drtg_master
     */
    public static void setupConnectionDrtgMaster() {

        String driver = "com.mysql.jdbc.Driver";
        String user = Config.getProperty("db.drtg_db.user");
        String pass = Config.getProperty("db.drtg_db.password");
        String url = Config.getProperty("db.drtg_db.url");
        int maxPoolSize = Config.getInt("parallel_processing_count", MAX_POOL_NUM);

        // connection
        cpdsDrtgMaster = new ComboPooledDataSource();
        try {
            cpdsDrtgMaster.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        cpdsDrtgMaster.setJdbcUrl(url);
        cpdsDrtgMaster.setUser(user);
        cpdsDrtgMaster.setPassword(pass);

        // config
        cpdsDrtgMaster.setMaxPoolSize(maxPoolSize);
    }

    /**
     * cpdsCore接続を取得する
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnectionCore() throws SQLException {
        return cpdsCore.getConnection();
    }

    /**
     * cpdsDrtgSlave接続を取得する
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnectionDrtgSlave() throws SQLException {
        return cpdsDrtgSlave.getConnection();
    }

    /**
     * cpdsDrtgMaster接続を取得する
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnectionDrtgMaster() throws SQLException {
        return cpdsDrtgMaster.getConnection();
    }

    /**
     * データベースのロールバックを操作
     *
     * @param conn
     */
    public static void rollbackConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * ＤＢ接続を切れます（poolへリターンする）
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ＤＢ接続を切れます（poolへリターンする）
     * @param conn
     * @param boolean
     */
    public static void closeConnection(Connection conn, boolean autoCommit) {
        if (conn == null) {
            return;
        }
        try {
            conn.setAutoCommit(autoCommit);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
