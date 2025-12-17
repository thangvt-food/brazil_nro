package com.girlkun.database;

import com.girlkun.result.GirlkunResultSet;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class GirlkunDB {

    private static HikariDataSource dataSource;

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initDataSource();
        }
        return dataSource.getConnection();
    }

    private static void initDataSource() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("data/config/girlkundb.properties"));

            String host = properties.getProperty("girlkun.database.host");
            String port = properties.getProperty("girlkun.database.port");
            String dbName = properties.getProperty("girlkun.database.name");
            String user = properties.getProperty("girlkun.database.user");
            String pass = properties.getProperty("girlkun.database.pass");
            String maxConn = properties.getProperty("girlkun.database.max", "10");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(
                    "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&characterEncoding=UTF-8");
            config.setUsername(user);
            config.setPassword(pass);
            config.setMaximumPoolSize(Integer.parseInt(maxConn));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            dataSource = new HikariDataSource(config);
            System.out.println("Load file properites thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GirlkunResultSet executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            // FIX: Thêm TYPE_SCROLL_INSENSITIVE để hỗ trợ MySQL 8.x
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rs = ps.executeQuery();
            return new com.girlkun.result.ResultSetImpl(rs);
        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra khi thực thi câu lệnh: " + sql);
            e.printStackTrace();

            // Close resources on error
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    public static int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra khi thực thi câu lệnh: " + sql);
            e.printStackTrace();
            return -1;
        }
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
