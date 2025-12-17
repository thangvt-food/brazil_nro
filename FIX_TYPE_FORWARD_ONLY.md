# Hướng dẫn sửa lỗi TYPE_FORWARD_ONLY cho MySQL 8.x

## Vấn đề
Lỗi `TYPE_FORWARD_ONLY` xảy ra vì MySQL Driver 8.x mặc định chỉ cho phép con trỏ ResultSet di chuyển một chiều (forward only). Khi code gọi `.last()`, driver sẽ báo lỗi vì không được phép nhảy xuống cuối.

## Nguyên nhân
File `GirlkunDB.java` và `ResultSetImpl.java` nằm trong file JAR `lib/girlkundb-1.0.0.jar` - đây là thư viện đã được biên dịch sẵn và không thể sửa trực tiếp.

## Giải pháp

### Phương án 1: Sửa source code trong JAR (Khuyến nghị)

1. **Giải nén file JAR:**
   ```bash
   cd lib
   mkdir girlkundb-temp
   cd girlkundb-temp
   jar xf ../girlkundb-1.0.0.jar
   ```

2. **Tìm và sửa file GirlkunDB.java:**
   - Tìm file `GirlkunDB.class` trong thư mục `com/girlkun/database/`
   - Vì đây là file `.class` (đã biên dịch), bạn cần decompile nó hoặc có source code gốc
   - Nếu có source code, tìm phương thức `executeQuery` và sửa như sau:

   ```java
   // TÌM dòng cũ (khoảng dòng 140-150):
   PreparedStatement ps = conn.prepareStatement(sql);
   
   // THAY bằng:
   PreparedStatement ps = conn.prepareStatement(sql, 
       ResultSet.TYPE_SCROLL_INSENSITIVE, 
       ResultSet.CONCUR_READ_ONLY);
   ```

3. **Biên dịch lại và đóng gói JAR:**
   ```bash
   # Biên dịch (nếu có source code)
   javac -cp . com/girlkun/database/GirlkunDB.java
   
   # Đóng gói lại JAR
   jar cf ../girlkundb-1.0.0-fixed.jar *
   
   # Thay thế file JAR cũ
   cd ..
   mv girlkundb-1.0.0.jar girlkundb-1.0.0.backup
   mv girlkundb-1.0.0-fixed.jar girlkundb-1.0.0.jar
   ```

### Phương án 2: Sửa tất cả các file sử dụng prepareStatement

Nếu không thể sửa JAR, bạn cần sửa tất cả các file `.java` trong source code:

#### Các file đã được sửa:
✅ `src/com/girlkun/services/func/TopService.java` - Đã sửa 3 chỗ

#### Các file cần sửa tiếp:

1. **src/com/girlkun/server/Manager.java** - Khoảng 30+ chỗ cần sửa
2. **src/com/girlkun/jdbc/daos/GodGK.java** - 2 chỗ
3. **src/com/girlkun/jdbc/daos/PlayerDAO.java** - 1 chỗ
4. **src/com/girlkun/jdbc/daos/ShopDAO.java** - 4 chỗ
5. **src/com/girlkun/jdbc/daos/GiftDAO.java** - 1 chỗ
6. **src/com/girlkun/models/player/Tamkjllgift.java** - 1 chỗ
7. **src/com/girlkun/MaQuaTang/MaQuaTangManager.java** - 1 chỗ
8. **src/com/girlkun/models/clan/Clan.java** - 3 chỗ
9. **src/com/girlkun/services/ClanService.java** - 2 chỗ

#### Mẫu code sửa:

**TRƯỚC:**
```java
ps = conn.prepareStatement("SELECT * FROM table");
```

**SAU:**
```java
ps = conn.prepareStatement("SELECT * FROM table", 
    ResultSet.TYPE_SCROLL_INSENSITIVE, 
    ResultSet.CONCUR_READ_ONLY);
```

### Phương án 3: Liên hệ tác giả thư viện

Nếu bạn có liên hệ với tác giả của `girlkundb-1.0.0.jar`, hãy yêu cầu họ cập nhật thư viện để hỗ trợ MySQL 8.x.

## Giải thích kỹ thuật

- **TYPE_SCROLL_INSENSITIVE**: Cho phép con trỏ ResultSet di chuyển tự do (lên, xuống, nhảy cóc) mà không quan tâm đến những thay đổi của database trong lúc đang đọc.
- **CONCUR_READ_ONLY**: Chỉ đọc dữ liệu, không cho phép cập nhật thông qua ResultSet.

## Lưu ý

1. Sau khi sửa, cần **biên dịch lại** toàn bộ project
2. **Backup** tất cả file trước khi sửa
3. Test kỹ sau khi sửa để đảm bảo không có lỗi mới

## Bước 3: Kết nối Web Đăng ký với Database

Nếu bạn đang làm web đăng ký, đảm bảo file `app.js` sử dụng đúng cấu trúc database:

```javascript
// Trong file app.js của Web Reg
const sql = "INSERT INTO account (username, password, active, is_admin) VALUES (?, ?, 1, 0)";
db.query(sql, [username, password], (err, result) => {
    if (err) return res.send("Lỗi: " + err.message);
    res.send("Đăng ký thành công!");
});
```

## Kiểm tra sau khi sửa

Chạy lại server và kiểm tra log. Nếu không còn lỗi `TYPE_FORWARD_ONLY` là đã thành công!
