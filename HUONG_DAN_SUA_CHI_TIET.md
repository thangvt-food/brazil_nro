# Script tự động sửa lỗi TYPE_FORWARD_ONLY
# Hướng dẫn: Chạy script này để tự động sửa tất cả các file

## Danh sách các file đã sửa:
✅ src/com/girlkun/services/func/TopService.java - 3 chỗ
✅ src/com/girlkun/jdbc/daos/GodGK.java - 2 chỗ

## Danh sách các file cần sửa thủ công:

### 1. src/com/girlkun/server/Manager.java

Tìm và thay thế TẤT CẢ các dòng:
```
ps = con.prepareStatement(
```

Thành:
```
ps = con.prepareStatement(
```

Sau đó thêm `, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY` TRƯỚC dấu `;`

**Ví dụ cụ thể:**

TRƯỚC (dòng 206):
```java
ps = con.prepareStatement("select * from part");
```

SAU:
```java
ps = con.prepareStatement("select * from part", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
```

**Các dòng cần sửa trong Manager.java:**
- Dòng 206: `ps = con.prepareStatement("select * from part");`
- Dòng 251: `ps = con.prepareStatement("select * from part");`
- Dòng 284: `ps = con.prepareStatement("select count(id) from small_version");`
- Dòng 293: `ps = con.prepareStatement("select * from small_version order by id");`
- Dòng 315: `ps = con.prepareStatement("select * from clan_sv" + SERVER);`
- Dòng 356: `ps = con.prepareStatement("select id from clan_sv" + SERVER + " order by id desc limit 1");`
- Dòng 364: `ps = con.prepareStatement("select * from dhvt_template");`
- Dòng 381: `ps = con.prepareStatement("select * from skill_template order by nclass_id, slot");`
- Dòng 433: `ps = con.prepareStatement("select * from head_avatar");`
- Dòng 442: `ps = con.prepareStatement("select * from flag_bag");`
- Dòng 461: `ps = con.prepareStatement("select * from intrinsic");`
- Dòng 493-496: `ps = con.prepareStatement("SELECT id, task_main_template.name, detail, ..." + ...);`
- Dòng 521: `ps = con.prepareStatement("select * from side_task_template");`
- Dòng 547: `ps = con.prepareStatement("select * from item_template");`
- Dòng 570: `ps = con.prepareStatement("select id, name from item_option_template");`
- Dòng 698: `ps = con.prepareStatement("select * from caption");`
- Dòng 706: `ps = con.prepareStatement("select name, n_frame from img_by_name");`
- Dòng 714: `ps = con.prepareStatement("select * from mob_template");`
- Dòng 732: `ps = con.prepareStatement("select * from npc_template");`
- Dòng 747: `ps = con.prepareStatement("select count(id) from map_template");`
- Dòng 752: `ps = con.prepareStatement("select * from map_template");`
- Dòng 874: `PreparedStatement ps = con.prepareStatement(query);`

### 2. src/com/girlkun/jdbc/daos/PlayerDAO.java

TRƯỚC (dòng 849):
```java
PreparedStatement ps = con.prepareStatement("select * from account where id = ? limit 1");
```

SAU:
```java
PreparedStatement ps = con.prepareStatement("select * from account where id = ? limit 1", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
```

### 3. src/com/girlkun/jdbc/daos/ShopDAO.java

Sửa 4 dòng:
- Dòng 22: `PreparedStatement ps = con.prepareStatement("select * from shop order by npc_id asc");`
- Dòng 50: `PreparedStatement ps = con.prepareStatement("select * from tab_shop where shop_id = ? order by id");`
- Dòng 77: `PreparedStatement ps = con.prepareStatement("select * from item_shop where is_sell = 1 and tab_id = ? " + ...);`
- Dòng 109: `PreparedStatement ps = con.prepareStatement("select * from item_shop_option where item_shop_id = ?");`

### 4. src/com/girlkun/jdbc/daos/GiftDAO.java

TRƯỚC (dòng 27):
```java
ps = con.prepareStatement("select * from gift where player_id = ?");
```

SAU:
```java
ps = con.prepareStatement("select * from gift where player_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
```

### 5. src/com/girlkun/models/player/Tamkjllgift.java

TRƯỚC (dòng 50):
```java
PreparedStatement ps = con.prepareStatement("SELECT * FROM TamkjllGift WHERE Luot >= 1");
```

SAU:
```java
PreparedStatement ps = con.prepareStatement("SELECT * FROM TamkjllGift WHERE Luot >= 1", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
```

### 6. src/com/girlkun/MaQuaTang/MaQuaTangManager.java

TRƯỚC (dòng 40):
```java
PreparedStatement ps = con.prepareStatement("SELECT * FROM giftcode");
```

SAU:
```java
PreparedStatement ps = con.prepareStatement("SELECT * FROM giftcode", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
```

## LƯU Ý QUAN TRỌNG:

1. **CHỈ SỬA** các lệnh `prepareStatement` có chứa **SELECT** (đọc dữ liệu)
2. **KHÔNG SỬA** các lệnh `prepareStatement` có chứa **INSERT, UPDATE, DELETE** (ghi dữ liệu)
3. Sau khi sửa xong, **BIÊN DỊCH LẠI** project

## Lệnh biên dịch lại project:

```bash
# Dừng server nếu đang chạy
# Sau đó biên dịch lại
cd c:\Users\aczaa\Downloads\BASILV4.0
# Sử dụng IDE (IntelliJ IDEA hoặc NetBeans) để Build lại project
# Hoặc dùng lệnh:
javac -cp "lib/*" -d out/production/CBRO src/com/girlkun/**/*.java
```

## Kiểm tra sau khi sửa:

1. Biên dịch lại project
2. Chạy lại server
3. Kiểm tra log - nếu không còn lỗi `TYPE_FORWARD_ONLY` là thành công!
