const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const app = express();

app.use(bodyParser.urlencoded({ extended: true }));

// Cấu hình kết nối tới HeidiSQL (MySQL) của bạn
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1', // Mật khẩu bạn đã thiết lập
    database: 'nr'  // Tên database trong HeidiSQL
});

db.connect((err) => {
    if (err) throw err;
    console.log('Đã kết nối Database thành công!');
});

// Trang giao diện đăng ký đơn giản
app.get('/', (req, res) => {
    res.send(`
        <h2>ĐĂNG KÝ TÀI KHOẢN NGỌC RỒNG</h2>
        <form action="/register" method="POST">
            Tên tài khoản: <input type="text" name="username" required><br><br>
            Mật khẩu: <input type="password" name="password" required><br><br>
            <button type="submit">Đăng ký ngay</button>
        </form>
    `);
});

// Xử lý logic đăng ký
app.post('/register', (req, res) => {
    const { username, password } = req.body;

    // Kiểm tra tài khoản tồn tại (dựa trên cấu trúc file clean_basil.sql)
    db.query('SELECT * FROM account WHERE username = ?', [username], (err, results) => {
        if (results.length > 0) {
            return res.send('Tài khoản này đã tồn tại!');
        }

        // Chèn tài khoản mới (Các cột mặc định như is_admin, active lấy từ file SQL)
        const sql = 'INSERT INTO account (username, password) VALUES (?, ?)';
        db.query(sql, [username, password], (err, result) => {
            if (err) return res.send('Lỗi đăng ký: ' + err.message);
            res.send('Chúc mừng! Đăng ký thành công tài khoản: ' + username);
        });
    });
});

app.listen(80, () => {
    console.log('Web Reg đang chạy tại: http://localhost');
});