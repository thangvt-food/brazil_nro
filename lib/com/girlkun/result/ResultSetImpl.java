package com.girlkun.result;

import java.sql.*;

/**
 * Implementation của GirlkunResultSet cho MySQL 8.x
 * FIX: Không sử dụng .last() để tránh lỗi TYPE_FORWARD_ONLY
 */
public class ResultSetImpl implements GirlkunResultSet {

    private ResultSet res;
    private int rowCount = -1; // -1 = chưa biết

    public ResultSetImpl(ResultSet res) {
        this.res = res;
        // FIX: KHÔNG gọi .last() vì MySQL 8.x driver mặc định là TYPE_FORWARD_ONLY
        // Nếu cần đếm số dòng, sẽ dùng COUNT(*) query riêng
    }

    @Override
    public boolean next() {
        try {
            return res != null && res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean first() {
        try {
            return res != null && res.first();
        } catch (SQLException e) {
            // Nếu lỗi TYPE_FORWARD_ONLY, return false
            return false;
        }
    }

    public void close() {
        try {
            if (res != null && !res.isClosed()) {
                res.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRows() {
        // FIX: Không thể dùng .last() với TYPE_FORWARD_ONLY
        // Return -1 để indicate không biết số dòng
        return rowCount;
    }

    @Override
    public boolean gotoFirst() {
        try {
            if (res != null) {
                return res.first();
            }
            return false;
        } catch (SQLException e) {
            // Ignore nếu TYPE_FORWARD_ONLY
            return false;
        }
    }

    @Override
    public boolean gotoLast() {
        try {
            if (res != null) {
                return res.last();
            }
            return false;
        } catch (SQLException e) {
            // Ignore nếu TYPE_FORWARD_ONLY
            return false;
        }
    }

    @Override
    public void gotoBeforeFirst() {
        try {
            if (res != null) {
                res.beforeFirst();
            }
        } catch (SQLException e) {
            // Ignore nếu TYPE_FORWARD_ONLY
        }
    }

    @Override
    public boolean gotoResult(int row) {
        try {
            if (res != null) {
                return res.absolute(row);
            }
            return false;
        } catch (SQLException e) {
            // Ignore nếu TYPE_FORWARD_ONLY
            return false;
        }
    }

    // ========== GET BY COLUMN NAME ==========

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return res.getInt(columnLabel);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return res.getLong(columnLabel);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        return res.getString(columnLabel);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return res.getByte(columnLabel);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        return res.getShort(columnLabel);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return res.getBoolean(columnLabel);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return res.getFloat(columnLabel);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return res.getDouble(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return res.getTimestamp(columnLabel);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return res.getObject(columnLabel);
    }

    // ========== GET BY COLUMN INDEX ==========

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return res.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return res.getLong(columnIndex);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return res.getString(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return res.getByte(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return res.getShort(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return res.getBoolean(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return res.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return res.getDouble(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return res.getTimestamp(columnIndex);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return res.getObject(columnIndex);
    }

    // ========== UTILITY METHODS ==========

    public void dispose() {
        close();
    }
}
