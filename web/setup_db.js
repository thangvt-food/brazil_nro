const mysql = require('mysql2');

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1'
});

connection.connect((err) => {
    if (err) {
        console.error('Error connecting to MySQL:', err);
        console.log('Please make sure your MySQL password is correct in server.js');
        process.exit(1);
    }
    console.log('Connected to MySQL server.');

    connection.query("CREATE DATABASE IF NOT EXISTS basil", (err) => {
        if (err) {
            console.error('Error creating database:', err);
            process.exit(1);
        }
        console.log("Database 'basil' created or exists.");

        connection.changeUser({ database: 'basil' }, (err) => {
            if (err) {
                console.error('Error changing database:', err);
                process.exit(1);
            }

            const createTableQuery = `
            CREATE TABLE IF NOT EXISTS \`account\` (
              \`id\` int(11) NOT NULL AUTO_INCREMENT,
              \`username\` varchar(20) NOT NULL,
              \`password\` varchar(100) NOT NULL,
              \`create_time\` timestamp NULL DEFAULT current_timestamp(),
              \`update_time\` timestamp NULL DEFAULT current_timestamp(),
              \`ban\` smallint(6) NOT NULL DEFAULT 0,
              \`point_post\` int(11) NOT NULL DEFAULT 0,
              \`last_post\` int(11) NOT NULL DEFAULT 0,
              \`role\` int(11) NOT NULL DEFAULT -1,
              \`is_admin\` tinyint(1) NOT NULL DEFAULT 0,
              \`last_time_login\` timestamp NOT NULL DEFAULT '2002-05-07 07:00:00',
              \`last_time_logout\` timestamp NOT NULL DEFAULT '2002-05-07 07:00:00',
              \`ip_address\` varchar(50) DEFAULT NULL,
              \`active\` int(11) NOT NULL DEFAULT 0,
              \`thoi_vang\` int(11) NOT NULL DEFAULT 0,
              \`server_login\` int(11) NOT NULL DEFAULT -1,
              \`bd_player\` double DEFAULT 1,
              \`is_gift_box\` tinyint(1) DEFAULT 0,
              \`gift_time\` varchar(255) DEFAULT '0',
              \`reward\` longtext DEFAULT NULL,
              \`vnd\` int(11) NOT NULL DEFAULT 0,
              PRIMARY KEY (\`id\`),
              UNIQUE KEY \`username\` (\`username\`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
            `;

            connection.query(createTableQuery, (err) => {
                if (err) {
                    console.error('Error creating table:', err);
                    process.exit(1);
                }
                console.log("Table 'account' created successfully.");
                connection.end();
            });
        });
    });
});
