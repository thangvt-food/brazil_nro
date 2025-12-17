const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');
const path = require('path');

const app = express();
app.use(cors());
const port = 3000;

// Database connection configuration
// ADAPT THESE SETTINGS TO YOUR LOCAL DATABASE!!!
const dbConfig = {
    host: 'localhost',
    user: 'root',
    password: '1', // Đổi thành mật khẩu MySQL của bạn nếu có (thường là '' hoặc '123456')
    database: 'basil', // Tên database lấy từ file clean_basil.sql
    // authPlugins: { mysql_clear_password: () => () => Buffer.from('') } // Thử bỏ comment dòng này nếu gặp lỗi AUTH_SWITCH_PLUGIN_ERROR
};

let connection;

function handleDisconnect() {
    connection = mysql.createConnection(dbConfig);

    connection.connect(function (err) {
        if (err) {
            console.error('Error connecting to database:', err);
            setTimeout(handleDisconnect, 2000);
        } else {
            console.log('Connected to database!');
        }
    });

    connection.on('error', function (err) {
        if (err.code === 'PROTOCOL_CONNECTION_LOST') {
            handleDisconnect();
        } else {
            throw err;
        }
    });
}

handleDisconnect();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, 'public')));

app.post('/api/register', (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({ success: false, message: 'Vui lòng nhập đầy đủ tài khoản và mật khẩu!' });
    }

    if (username.length < 4 || username.length > 20) {
        return res.status(400).json({ success: false, message: 'Tài khoản phải từ 4 đến 20 ký tự!' });
    }

    // Check if username contains special characters (basic alphanum check)
    if (!/^[a-zA-Z0-9]+$/.test(username)) {
        return res.status(400).json({ success: false, message: 'Tài khoản không được chứa ký tự đặc biệt!' });
    }

    const query = 'INSERT INTO account (username, password) VALUES (?, ?)';

    connection.query(query, [username, password], (err, result) => {
        if (err) {
            if (err.code === 'ER_DUP_ENTRY') {
                return res.json({ success: false, message: 'Tài khoản đã tồn tại!' });
            }
            console.error(err);
            return res.status(500).json({ success: false, message: 'Lỗi server, vui lòng thử lại sau.' });
        }
        res.json({ success: true, message: 'Đăng ký thành công!' });
    });
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
