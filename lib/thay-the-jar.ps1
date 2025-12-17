# Script thay thế JAR - Chạy sau khi dừng server

# Backup JAR cũ
Copy-Item "girlkundb-1.0.0.jar" "girlkundb-1.0.0.backup" -Force

# Xóa JAR cũ
Remove-Item "girlkundb-1.0.0.jar" -Force

# Đổi tên JAR mới
Rename-Item "girlkundb-1.0.0-mysql8-fixed.jar" "girlkundb-1.0.0.jar"

Write-Host "✅ Đã thay thế JAR thành công!"
Write-Host "Bây giờ bạn có thể chạy lại server."
