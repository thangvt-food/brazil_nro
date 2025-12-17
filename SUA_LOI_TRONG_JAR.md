# 🔴 LỖI TYPE_FORWARD_ONLY VẪN CÒN - TỪ FILE JAR

## Vấn đề

Lỗi `TYPE_FORWARD_ONLY` vẫn xuất hiện, nhưng lần này đến từ **file JAR `lib/girlkundb-1.0.0.jar`**:

```
at com.girlkun.result.ResultSetImpl.<init>(ResultSetImpl.java:22)
at com.girlkun.database.GirlkunDB.executeQuery(GirlkunDB.java:148)
```

Chúng ta đã sửa tất cả các file `.java` trong source code, nhưng class `GirlkunDB` trong JAR vẫn chưa được sửa.

---

## Giải pháp

### Phương án 1: Decompile và sửa JAR (Khuyến nghị)

#### Bước 1: Decompile JAR

1. **Download JD-GUI**: https://java-decompiler.github.io/
2. **Mở file JAR**:
   ```
   lib/girlkundb-1.0.0.jar
   ```
3. **Tìm file**: `com/girlkun/database/GirlkunDB.java`
4. **Export source code** (File > Save All Sources)

#### Bước 2: Sửa source code

Tìm phương thức `executeQuery` (khoảng dòng 148) và sửa:

**TRƯỚC:**
```java
PreparedStatement ps = conn.prepareStatement(sql);
```

**SAU:**
```java
PreparedStatement ps = conn.prepareStatement(sql, 
    ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_READ_ONLY);
```

#### Bước 3: Biên dịch lại

```bash
# Giải nén JAR
cd lib
mkdir girlkundb-temp
cd girlkundb-temp
jar xf ../girlkundb-1.0.0.jar

# Copy source code đã sửa vào đây
# Biên dịch
javac -cp . com/girlkun/database/GirlkunDB.java

# Đóng gói lại
jar cf ../girlkundb-1.0.0-fixed.jar *

# Backup và thay thế
cd ..
move girlkundb-1.0.0.jar girlkundb-1.0.0.backup
move girlkundb-1.0.0-fixed.jar girlkundb-1.0.0.jar
```

---

### Phương án 2: Tạo wrapper class (Nhanh hơn)

Tạo file mới để override class trong JAR:

**File: `src/com/girlkun/database/GirlkunDBFixed.java`**

```java
package com.girlkun.database;

import java.sql.*;

public class GirlkunDBFixed {
    
    public static PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql, 
            ResultSet.TYPE_SCROLL_INSENSITIVE, 
            ResultSet.CONCUR_READ_ONLY);
    }
    
    public static PreparedStatement prepareStatement(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql, 
            ResultSet.TYPE_SCROLL_INSENSITIVE, 
            ResultSet.CONCUR_READ_ONLY);
        
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        
        return ps;
    }
}
```

Sau đó tìm và thay thế tất cả `GirlkunDB.executeQuery` bằng wrapper mới.

---

### Phương án 3: Sửa trực tiếp trong JAR (Dùng tool)

#### Sử dụng Recaf (Java Bytecode Editor)

1. **Download Recaf**: https://github.com/Col-E/Recaf/releases
2. **Mở file JAR**: `lib/girlkundb-1.0.0.jar`
3. **Tìm class**: `com.girlkun.database.GirlkunDB`
4. **Edit bytecode** để thêm tham số `TYPE_SCROLL_INSENSITIVE`
5. **Save JAR**

---

## Giải pháp TẠM THỜI (Nhanh nhất)

Nếu bạn không muốn sửa JAR, hãy **tắt chức năng `.last()`** trong `ResultSetImpl`:

1. Decompile JAR
2. Tìm file `com/girlkun/result/ResultSetImpl.java`
3. Tìm dòng 22: `this.res.last();`
4. Comment hoặc xóa dòng này
5. Biên dịch và đóng gói lại

**Lưu ý**: Cách này có thể ảnh hưởng đến logic của ứng dụng nếu code có sử dụng kết quả từ `.last()`.

---

## Hướng dẫn chi tiết - Phương án 1

### 1. Extract JAR

```powershell
cd c:\Users\aczaa\Downloads\BASILV4.0\lib
mkdir girlkundb-source
cd girlkundb-source
jar xf ..\girlkundb-1.0.0.jar
```

### 2. Decompile (nếu chưa có source)

Sử dụng JD-GUI hoặc CFR:
```powershell
# Download CFR
# https://www.benf.org/other/cfr/

java -jar cfr.jar girlkundb-1.0.0.jar --outputdir decompiled
```

### 3. Sửa file

Tìm `com/girlkun/database/GirlkunDB.java` và sửa như đã hướng dẫn.

### 4. Compile lại

```powershell
# Compile
javac -cp . com/girlkun/database/GirlkunDB.java
javac -cp . com/girlkun/result/ResultSetImpl.java

# Repackage
jar cf ..\girlkundb-1.0.0-fixed.jar *
```

### 5. Thay thế

```powershell
cd ..
move girlkundb-1.0.0.jar girlkundb-1.0.0.backup
move girlkundb-1.0.0-fixed.jar girlkundb-1.0.0.jar
```

---

## Kết luận

Vấn đề chính là **file JAR chứa code đã biên dịch** mà chúng ta không thể sửa trực tiếp từ source code. Bạn cần:

1. ✅ Decompile JAR
2. ✅ Sửa code trong JAR
3. ✅ Biên dịch lại
4. ✅ Thay thế JAR cũ

Hoặc liên hệ tác giả của `girlkundb-1.0.0.jar` để yêu cầu phiên bản hỗ trợ MySQL 8.x.
