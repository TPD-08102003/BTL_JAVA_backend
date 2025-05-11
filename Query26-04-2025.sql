-- Tạo database
CREATE DATABASE SPSenDBFinal;




-- Bảng accounts (Tài khoản)
CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    account_name VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (LOWER(role) IN ('admin', 'customer', 'employee')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Bảng customers (Khách hàng)
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    account_id INT UNIQUE, -- Liên kết với accounts
    firstname VARCHAR(200) NOT NULL,
    lastname VARCHAR(200) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(200),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- Bảng employees (Nhân viên)
CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    account_id INT UNIQUE, -- Liên kết với accounts
    firstname VARCHAR(200) NOT NULL,
    lastname VARCHAR(200) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(200),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- Bảng suppliers (Nhà cung cấp)
CREATE TABLE suppliers (
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(200) NOT NULL,
    address VARCHAR(200),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE
);

-- Bảng categories (Loại sản phẩm)
CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(200) UNIQUE NOT NULL,
    description VARCHAR(200)
);

-- Bảng products (Sản phẩm từ sen)
CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    category_id INT NOT NULL,
    supplier_id INT NOT NULL,
    description VARCHAR(200),
    quantity INT DEFAULT 0 CHECK (quantity >= 0),
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE CASCADE
);

-- Bảng orders (Đơn hàng)
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount NUMERIC(10,2) NOT NULL CHECK (total_amount >= 0),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- Bảng order_items (Chi tiết đơn hàng)
CREATE TABLE order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- Bảng images (Ảnh sản phẩm)
CREATE TABLE images (
    image_id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    image_path TEXT NOT NULL,
    uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
select * from cart
CREATE TABLE cart (
    cart_id BIGINT PRIMARY KEY,
    account_id INTEGER NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
-- Dữ liệu mẫu cho hệ thống quản lý sản phẩm từ sen
select * from accounts
-- accounts
INSERT INTO accounts (account_name, password, email, role) VALUES
('Tô Phước Đầy', '123456789', 'phuocdayt@gmail.com', 'admin'),
('Nguyen Van A', 'adminpass1', 'nguyenvana.admin@example.com', 'admin'),
('Tran Thi B', 'adminpass2', 'tranthib.admin@example.com', 'admin'),
('Le Van C', 'custpass1', 'levanc.customer@example.com', 'customer'),
('Pham Thi D', 'custpass2', 'phamthid.customer@example.com', 'customer'),
('Hoang Van E', 'custpass3', 'hoangvane.customer@example.com', 'customer'),
('Nguyen Thi F', 'custpass4', 'nguyenthif.customer@example.com', 'customer'),
('Tran Van G', 'custpass5', 'tranvang.customer@example.com', 'customer'),
('Le Thi H', 'custpass6', 'lethih.customer@example.com', 'customer'),
('Pham Van I', 'custpass7', 'phamvani.customer@example.com', 'customer'),
('Hoang Thi J', 'custpass8', 'hoangthij.customer@example.com', 'customer'),
('Do Van K', 'custpass9', 'dovank.customer@example.com', 'customer'),
('Bui Thi L', 'custpass10', 'buithil.customer@example.com', 'customer'),
('Nguyen Van M', 'emppass1', 'nguyenvanm.employee@example.com', 'employee'),
('Tran Thi N', 'emppass2', 'tranthin.employee@example.com', 'employee'),
('Le Van O', 'emppass3', 'levano.employee@example.com', 'employee'),
('Pham Thi P', 'emppass4', 'phamthip.employee@example.com', 'employee'),
('Hoang Van Q', 'emppass5', 'hoangvanq.employee@example.com', 'employee'),
('Nguyen Thi R', 'emppass6', 'nguyenthir.employee@example.com', 'employee'),
('Tran Van S', 'emppass7', 'tranvans.employee@example.com', 'employee'),
('Le Thi T', 'emppass8', 'lethit.employee@example.com', 'employee');

-- customers
-- Dữ liệu bảng customers
INSERT INTO customers (account_id, firstname, lastname, phone, email, address) VALUES
(3, 'Le', 'Van C', '0912345678', 'levanc.customer@example.com', '123 Main Street, Hanoi'),
(4, 'Pham', 'Thi D', '0912345679', 'phamthid.customer@example.com', '456 Market Road, Hanoi'),
(5, 'Hoang', 'Van E', '0912345680', 'hoangvane.customer@example.com', '789 Park Lane, Hanoi'),
(6, 'Nguyen', 'Thi F', '0912345681', 'nguyenthif.customer@example.com', '101 Central Blvd, Hanoi'),
(7, 'Tran', 'Van G', '0912345682', 'tranvang.customer@example.com', '202 Riverside Drive, Hanoi'),
(8, 'Le', 'Thi H', '0912345683', 'lethih.customer@example.com', '303 Lakeview Street, Hanoi'),
(9, 'Pham', 'Van I', '0912345684', 'phamvani.customer@example.com', '404 Greenhill Avenue, Hanoi'),
(10, 'Hoang', 'Thi J', '0912345685', 'hoangthij.customer@example.com', '505 Sunset Blvd, Hanoi'),
(11, 'Do', 'Van K', '0912345686', 'dovank.customer@example.com', '606 Birchwood Lane, Hanoi'),
(12, 'Bui', 'Thi L', '0912345687', 'buithil.customer@example.com', '707 Maple Street, Hanoi');
Select *from employees;
-- employees
-- Dữ liệu bảng employees
INSERT INTO employees (account_id, firstname, lastname, phone, email, address) VALUES
(13, 'Nguyen', 'Van M', '0923456789', 'nguyenvanm.employee@example.com', '8 Highstreet, Hanoi'),
(14, 'Tran', 'Thi N', '0923456790', 'tranthin.employee@example.com', '9 Elmwood Drive, Hanoi'),
(15, 'Le', 'Van O', '0923456791', 'levano.employee@example.com', '10 Oak Avenue, Hanoi'),
(16, 'Pham', 'Thi P', '0923456792', 'phamthip.employee@example.com', '11 Pine Road, Hanoi'),
(17, 'Hoang', 'Van Q', '0923456793', 'hoangvanq.employee@example.com', '12 Cedar Drive, Hanoi'),
(18, 'Nguyen', 'Thi R', '0923456794', 'nguyenthir.employee@example.com', '13 Birch Road, Hanoi'),
(19, 'Tran', 'Van S', '0923456795', 'tranvans.employee@example.com', '14 Maple Street, Hanoi'),
(20, 'Le', 'Thi T', '0923456796', 'lethit.employee@example.com', '15 River Lane, Hanoi');

-- suppliers
INSERT INTO suppliers (supplier_name, address, phone, email) VALUES
('Sen Đồng Tháp', 'Đồng Tháp', '0911000001', 'supplier1@sen.vn'),
('Sen Huế', 'Huế', '0911000002', 'supplier2@sen.vn'),
('Sen Tây Hồ', 'Hà Nội', '0911000003', 'supplier3@sen.vn'),
('Sen Hương Quê', 'Nghệ An', '0911000004', 'supplier4@sen.vn'),
('Sen Quê Tôi', 'Nam Định', '0911000005', 'supplier5@sen.vn');

-- categories
INSERT INTO categories (category_name, description) VALUES
('Trà sen', 'Các loại trà được ướp hoặc pha chế từ hoa sen'),
('Hạt sen', 'Hạt sen tươi, khô, và các sản phẩm liên quan'),
('Mứt sen', 'Mứt ngọt làm từ hạt sen, thường dùng dịp lễ Tết'),
('Sữa sen', 'Sữa làm từ hạt sen nguyên chất'),
('Bánh sen', 'Bánh trung thu, bánh ngọt có nhân từ sen'),
('Rượu sen', 'Rượu sen thượng hạng'),
('Mỹ phẩm từ sen', 'Mỹ phẩm thiên nhiên chiết xuất từ hoa sen');


ALTER TABLE orders ADD COLUMN status VARCHAR(20) DEFAULT 'Pending';
UPDATE orders o
SET total_amount = (
    SELECT COALESCE(SUM(oi.price * oi.quantity), 0)
    FROM order_items oi
    WHERE oi.order_id = o.order_id
)
WHERE o.order_id IN (SELECT order_id FROM order_items);
-- Kiểm tra dữ liệu sau khi cập nhật
SELECT * FROM customers;
SELECT * FROM order_items;
select * from cart;

-- orders
INSERT INTO orders (customer_id, total_amount) VALUES
(3, 0),
(4, 0),
(5, 0),
(6, 0),
(7, 0),
(8,0),
(9,0);

select * from orders
select * from products
-- order_items
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(6, 31, 2, 120000),
(7, 32, 1, 80000),
(8, 33, 3, 95000),
(9, 34, 1, 65000),
(9, 35, 2, 45000),
(8, 36, 1, 70000),
(7, 25, 1, 150000),
(6, 26, 4, 30000),
(8, 30, 2, 50000),
(9, 28, 1, 220000);


select * from products
INSERT INTO images (product_id, image_path) VALUES
(25, 'D:/CacSanPhamSen/TraHoaSen/Trahoasen2.jpg'),
(26, 'D:/CacSanPhamSen/HatSen/Hatsen4.jpg'),
(27, 'D:/CacSanPhamSen/MutSen/Mutsen3.jpg'),
(28, 'D:/CacSanPhamSen/NhangSen/Nhangsen2.jpg'),
(29, 'D:/CacSanPhamSen/SuaSen/Suasen2.jpg'),
(30, 'D:/CacSanPhamSen/TamSen/Tamsen2.jpg'),
(31, 'D:/CacSanPhamSen/BotSen/Botsen2.jpg'),
(32, 'D:/CacSanPhamSen/CuSen/Cusen2.jpg'),
(33, 'D:/CacSanPhamSen/TinhDauSen/Tinhdausen2.jpg'),
(34, 'D:/CacSanPhamSen/RuouSen/Ruousen2.jpg'),
(35, 'D:/CacSanPhamSen/DoThuCongMyNghe/Dothucongmynghe2.jpg'),
(36, 'D:/CacSanPhamSen/LaSenKho/Lasenkho2.jpg');


select * from suppliers
INSERT INTO products (product_name, price, category_id, supplier_id, description, quantity) VALUES
('Sữa Sen', 150000.00, 4, 1, 'Sản phẩm sữa sen chất lượng cao, an toàn.', 50),
('Hạt Sen', 120000.00, 2, 1, 'Sản phẩm hạt sen 100% tự nhiên.', 30),
('Trà Hoa Sen', 200000.00, 1, 1, 'Sản phẩm trà hoa sen mang đến hương vị từ thiên nhiên.', 20),
('Nhang Sen', 100000.00, 7, 2, 'Sản phẩm nhang sen chất lượng cao.', 40),
('Tâm Sen', 180000.00, 2, 1, 'Sản phẩm tâm sen mang lại sức khỏe cho người dùng.', 25),
('Bột Sen', 130000.00, 2, 3, 'Bột sen một sản phẩm mới, làm từ sen.', 60),
('Củ Sen', 110000.00, 5, 4, 'Sản phẩm củ sen là một thực phẩm chất lượng cao.', 70),
('Tinh Dầu Sen', 250000.00, 7, 5, 'Sản phẩm tinh dầu sen dược chiết xuất từ sen.', 15),
('Mứt Sen', 220000.00, 3,5, 'Mứt sen được làm từ những hạt sen được tuyển chọn.', 35),
('Rượu Sen', 300000.00, 6, 4, 'Rượu sen chất lượng cao.', 10),
('Đồ Thủ Công Mỹ Nghệ', 400000.00, 7, 3, 'Sản phẩm thủ công mỹ nghệ làm từ sen chất lượng cao, bền đẹp.', 5),
('Lá Sen Khô', 140000.00, 7, 2, 'Sản phẩm lá sen được tuyển chọn.', 45);


