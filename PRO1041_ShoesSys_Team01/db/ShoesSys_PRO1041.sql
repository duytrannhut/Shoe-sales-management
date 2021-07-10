
CREATE DATABASE ShoesSys
GO
USE ShoesSys
GO

CREATE TABLE NhanVien
(
	MaNV NVARCHAR(20) NOT NULL,
	MatKhau NVARCHAR(20) NOT NULL,
	TenNV NVARCHAR(50) NOT NULL,
	SoDT NVARCHAR(15) NOT NULL,
	Email NVARCHAR(50) NOT NULL,
	GioiTinh BIT NOT NULL,
	NgaySinh DATE NOT NULL,
	VaiTro BIT NOT NULL,
	DiaChi NVARCHAR(200) NOT NULL,
	Hinh NVARCHAR(50) NOT NULL,
	PRIMARY KEY(MaNV)
)
GO
CREATE TABLE KhachHang
(
	MaKH INT IDENTITY(1,1) NOT NULL,
	TenKH NVARCHAR(50) NOT NULL,
	SoDT NVARCHAR(15) NOT NULL,
	Email NVARCHAR(50) NOT NULL,
	GioiTinh BIT NOT NULL,
	NgaySinh DATE NOT NULL,
	DiaChi NVARCHAR(200) NOT NULL,
	PRIMARY KEY(MaKH)
)
GO
CREATE TABLE NhaCungCap
(
	MaNCC INT IDENTITY(1,1) NOT NULL,
	TenNCC NVARCHAR(50) NOT NULL,
	SoDT NVARCHAR(15) NOT NULL,
	DiaChi NVARCHAR(200) NOT NULL,
	PRIMARY KEY(MaNCC)
)
GO
CREATE TABLE Loai
(
	MaLoai INT IDENTITY(1,1) NOT NULL,
	TenLoai NVARCHAR(50) NOT NULL,
	PRIMARY KEY(MaLoai)
)
GO
CREATE TABLE SanPham
(
	MaSP INT IDENTITY(1,1) NOT NULL,
	TenSP NVARCHAR(50) NOT NULL,
	Hang NVARCHAR(50) NOT NULL,
	GioiTinh BIT NOT NULL,
	Size INT NOT NULL,
	SoLuong INT NOT NULL,
	MoTa NVARCHAR(255) NOT NULL,
	DonGia FLOAT NOT NULL,
	MaNCC INT NOT NULL,
	Hinh NVARCHAR(50) NOT NULL,
	ChatLieu NVARCHAR(50) NOT NULL,
	MaLoai INT NOT NULL,
	PRIMARY KEY(MaSP),
	CONSTRAINT fk_sanpham_nhacungcap FOREIGN KEY(MaNCC) REFERENCES NhaCungCap(MaNCC),
	CONSTRAINT fk_sanham_Loai FOREIGN KEY(MaLoai) REFERENCES Loai(MaLoai)
)
GO
CREATE TABLE DonHang
(
	MaDH INT IDENTITY(1,1) NOT NULL,
	NgayDatHang DATE NOT NULL,
	MaNV NVARCHAR(20) NOT NULL,
	MaKH INT NOT NULL,
	PRIMARY KEY(MaDH),
	CONSTRAINT fk_donhang_nhanvien FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV),
	CONSTRAINT fk_donhang_khachhang FOREIGN KEY(MaKH) REFERENCES KhachHang(MaKH) ON DELETE CASCADE
)
GO
CREATE TABLE DonHangChiTiet
(
	MaDH INT NOT NULL,
	MaSP INT NOT NULL,
	DonGia FLOAT NOT NULL,
	SoLuong INT NOT NULL,
	PRIMARY KEY(MaDH, MaSP),
	CONSTRAINT fk_donhangchitiet_donhang FOREIGN KEY(MaDH) REFERENCES DonHang(MaDH) ON DELETE CASCADE,
	CONSTRAINT fk_donhangchitiet_sanpham FOREIGN KEY(MaSP) REFERENCES SanPham(MaSP)
)
GO
-- Report: ten sanpham - SoLuong
	CREATE PROC sp_SanPhamNam(@nambd DATE, @namkt DATE)
	AS BEGIN
	SELECT 
	    YEAR(NgayDatHang) Nam, 
		SUM(DonHangChiTiet.SoLuong) SoLuong
	FROM DonHang 
		JOIN DonHangChiTiet ON DonHang.MaDH = DonHangChiTiet.MaDH 
		JOIN SanPham ON DonHangChiTiet.MaSP = SanPham.MaSP 
	WHERE YEAR(NgayDatHang) BETWEEN YEAR(@nambd) AND YEAR(@namkt)
	GROUP BY YEAR(NgayDatHang)
	END
	GO

	CREATE PROC sp_SanPhamThang(@nambd DATE, @namkt DATE)
	AS BEGIN
	SELECT 
		TenSP, 
		SUM(DonHangChiTiet.SoLuong) SoLuong
	FROM DonHang 
		JOIN DonHangChiTiet ON DonHang.MaDH = DonHangChiTiet.MaDH 
		JOIN SanPham ON DonHangChiTiet.MaSP = SanPham.MaSP
	WHERE CONVERT(varchar(7), NgayDatHang, 126) BETWEEN CONVERT(varchar(7), @nambd, 126) AND CONVERT(varchar(7), @namkt, 126)
	GROUP BY TenSP
	END
	GO

--Report: Nam - SoLuong
CREATE PROC sp_DonHangNam(@nambd DATE, @namkt DATE)
AS BEGIN
SELECT 
YEAR(NgayDatHang) nam, 
COUNT(MaDH) SoLuong 
FROM DonHang
WHERE YEAR(NgayDatHang) BETWEEN YEAR(@nambd) AND YEAR(@namkt)
GROUP BY YEAR(NgayDatHang)
END
GO
CREATE PROC sp_DonHangThang(@nambd DATE, @namkt DATE)
AS BEGIN
SELECT
YEAR(NgayDatHang) Nam,
MONTH(NgayDatHang) Thang, 
COUNT(MaDH) SoLuong 
FROM DonHang
WHERE CONVERT(varchar(7), NgayDatHang, 126) BETWEEN CONVERT(varchar(7), @nambd, 126) AND CONVERT(varchar(7), @namkt, 126)
GROUP BY MONTH(NgayDatHang), YEAR(NgayDatHang)
END
GO
--Report: Nam - DoanhThu
CREATE PROC sp_DoanhThuNam(@nambd DATE, @namkt DATE)
AS BEGIN
SELECT 
YEAR(NgayDatHang) nam, 
SUM(SoLuong*DonGia) DoanhThu
FROM DonHang JOIN DonHangChiTiet ON DonHang.MaDH = DonHangChiTiet.MaDH
WHERE YEAR(NgayDatHang) BETWEEN YEAR(@nambd) AND YEAR(@namkt)
GROUP BY YEAR(NgayDatHang)
END
GO

CREATE PROC sp_DoanhThuThang(@nambd DATE, @namkt DATE)
AS BEGIN
SELECT 
YEAR(NgayDatHang) Nam,
MONTH(NgayDatHang) Thang, 
SUM(SoLuong*DonGia) DoanhThu
FROM DonHang JOIN DonHangChiTiet ON DonHang.MaDH = DonHangChiTiet.MaDH
WHERE CONVERT(varchar(7), NgayDatHang, 126) BETWEEN CONVERT(varchar(7), @nambd, 126) AND CONVERT(varchar(7), @namkt, 126)
GROUP BY MONTH(NgayDatHang), YEAR(NgayDatHang)
END
GO



insert into NhanVien
 values  (N'NV001', N'nhanvien1', N'Đoàn Nam Công',N'0969394937',N'namcong@email.com',1,'1967-02-01',0,N'2 Phan Thúc Hoạch,Tân Phú,TPHCM',N'hinh1.jpg'),
		 (N'Ql002', N'matkhau2', N'Lê Anh Thư',N'0347167767',N'thule12@email.com',0,'1988-02-10',1,N'999 Lê Trọng Tấn,Tân Phú,TPHCM',N'hinh2.jpg'),
		 (N'NV003', N'matkhaumoi', N'Nguyễn Hoàng Tiến',N'0123765894',N'tienem2@email.com',1,'1997-02-11',0,N'567 Đỗ Bí,Phú Nhuận,TPHCM',N'hinh3.jpg'),
		 (N'NV004', N'nhanvien2', N'Võ Thị Tuyết',N'0988450089',N'tuyetcute@email.com',0,'1989-02-06',0,N'1234 Lê Thái Tổ,Tân Bình,TPHCM',N'hinh4.jpg'),
		 (N'NV005', N'bigcityboy', N'Phạm Phú Nam',N'0376098723',N'namboy123@email.com',1,'1999-02-11',0,N'9 Lãnh Binh Thăng,Tân Phú,TPHCM',N'hinh5.jpg'),
		 (N'Ql006', N'badgirl', N'Lý Nhã Ân',N'0987456456',N'anquanli@email.com',0,'1995-01-09',1,N'2 Phan Thúc Hoạch,Tân Phú,TPHCM',N'hinh6.jpg'),
		 (N'NV007', N'nam123', N'Trần Đại Nam',N'0334567234',N'namdai899@email.com',1,'1982-10-01',0,N'345 Nguyễn Sơn,Tân Phú,TPHCM',N'hinh7.jpg'),
		 (N'NV008', N'hoilamchi', N'Văn Viết Thế',N'0378945634',N'thenao45@email.com',1,'1999-10-02',0,N'12 Tân Sơn Nhì,Tân Phú,TPHCM',N'hinh8.jpg'),
		 (N'QL009', N'matkhauvip', N'Phạm Công Tính',N'0782345098',N'tinhql3@email.com',1,'1976-05-01',1,N'45 Thạch Lam,Thủ Đức,TPHCM',N'hinh9.jpg'),
		 (N'NV0010', N'phuongcute', N'Nhữ Bích Phượng',N'0377747757',N'phuongxihdep@email.com',0,'1982-10-08',0,N'3 Tân Hương,Tân Phú,TPHCM',N'hinh10.jpg'),
		 (N'ps11121', N'123456', N'Nguyễn Quốc Khánh', N'385091407', N'NguyenQuocKhanh16032k@gmail.com', 1	,'2000-03-16',1,N'41/2b To Ký', N'hinh1.jpg'),
		 (N'PS11474', N'123456',N'Mai Văn Phong',N'385091407',N'NguyenQuocKhanh16032k@gmail.com',1,'2000-03-16',1,N'41/2b To Ký', 'hinh1.jpg'),
		 (N'ps11518', N'123456', N'Đinh Văn Tâm', N'782345098', N'tamdvps11518@fpt.edu.vn', 1,'1976-05-01',1,N'45 Thích Lam,Tân Phú,TPHCM','hinh1.jpg'),
		 (N'PS11604', N'123456',N'Trần Nhựt Duy', N'0123456789', N'ssdas',1,'2000-02-03',1,N'sdasdsadsa','hinh1.jpg'),
		 (N'PS11609', N'123456', N'Nguyễn Huy Hoàng', N'0934544583', N'Nguyenhoang1151995@gmail.com',1,'1995-05-11',1,N'368 Tôn Đản Quận 4','hinh1.jpg')
GO
insert into KhachHang(TenKH,SoDT,Email,GioiTinh,NgaySinh,DiaChi)
 values  (N'Mai Van Phong',N'0969394987',N'phong2011@gmail.com',1,'1967-02-01',N'33 Trần Hưng Đạo, Quận 5, TPHCM'),
		 (N'Nguyễn Huy Hoàng',N'0347167767',N'hoangcute@email.com',1,'1999-11-01',N'621 Lê Trọng tấn, Quận Tân Phú, TPHCM'),
		 (N'Đoàn Công Thành',N'0876345123',N'thanh098@email.com',1,'1976-02-10',N'11 Phổ Quang, Quận Tân Bình, TPHCM'),
		 (N'Nguyễn Linh Chi',N'0988600089',N'linhchi20@email.com',0,'1996-08-01',N'123 Lãnh Binh Thăng, Quận 11, TPHCM'),
		 (N'Trần Thị Thùy Trang',N'0989658141',N'trangtrang@email.com',0,'1989-05-03',N'987 Lê Khôi, Quận 3, TPHCM'),
		 (N'Nguyễn Hoàng Hiếu',N'0376339779',N'hieubadboy@email.com',1,'1997-02-01',N'908 Tôn Đản, Quận 4, TPHCM'),
		 (N'Trần Trung Tính',N'0949336776',N'trantrungtinh23@email.com',1,'2000-12-01',N'1123 Trần Thị Cờ, Quận 12, TPHCM'),
		 (N'Tạ Thị Thanh Nga',N'0905678987',N'Ngaxinhdep12@email.com',0,'1989-08-02',N'898 Minh Tâm, Quận Phú Nhuận, TPHCM'),
		 (N'Trần Vũ Mai Trâm',N'0282345123',N'tramtran@email.com',0,'1995-04-05',N'98 Lê Lâm, Quận 8, TPHCM'),
		 (N'Nhữ Thị Bích Loan',N'0167098423',N'loanloan@email.com',0,'1967-07-05',N'33 Cao Thanh Bình, Quận 1, TPHCM')
GO
insert into NhaCungCap(TenNCC,SoDT,DiaChi)
 values	 (N'Công Ty Thành Viên',N'0987123456',N'33 Cao Thanh Bình, Quận 1, TPHCM'),
		 (N'Công Ty Hoàng Minh',N'0909657342',N'666 Trần Đình Trọng, Quận 3, TPHCM'),
		 (N'Công Ty Thương Mại',N'0347123789',N'678 Cao Lầu, Quận Thủ Đức, TPHCM'),
		 (N'Công Ty Giày Adidas',N'0988600019',N'1 Lê Đại hành, Quận 6, TPHCM'),
		 (N'Công Ty Trang Phục',N'0367767767',N'1123 Hồng Bàng, Quận 11, TPHCM')
GO
insert into Loai(TenLoai)
 values	 (N'Adidas'),
		 (N'Nike'),
		 (N'Jordan'),
		 (N'Puma'),
		 (N'Giày da')
GO
insert into SanPham(TenSP,Hang,GioiTinh,Size,SoLuong,Mota,DonGia,MaNCC,Hinh,ChatLieu,MaLoai)
 values  (N'Adidas Sneaker Grey',N'Adidas',1,40,100,N'Giày Sneaker adidas Phổ Thông',1500000,4,'adidas_nam_run1.jpg','Vãi',1),
		 (N'Nike Sports ABC',N'Nike',0,36,211,N'Giày Sneaker Thể Thao',2500000,1,'adidas_nam_run2.jpg','Vãi',2),
		 (N'Adidas Running AZ',N'Adidas',1,41,159,N'Giày Sneaker Chạy Bộ',3000000,4,'adidas_nam_run3.jpg','Vãi',1),
		 (N'Puma Sneaker Blue',N'Puma',1,38,330,N'Giày Sneaker Puma Phổ Thông',1450000,2,'adidas_nam_run4.jpg','Vãi',4),
		 (N'Adidas Sports Katarina',N'Adidas',0,36,250,N'Giày Sneakeer Adidas Thể Thao',4500000,4,'adidas_nam_sneaker1.jpg','Vãi',1),
		 (N'Puma Sports MCK',N'Puma',1,40,500,N'Giày Sneakeer Puma Thể Thao',800000,4,'adidas_nam_sneaker2.jpg','Vãi',4),
		 (N'Giày Da Bò',N'Giày Da',1,42,110,N'Giày Da Thanh Lịch',6000000,5,'adidas_nam_sneaker3.jpg','Da',5),
		 (N'Jordan 3 Retro',N'Jordan',1,38,240,N'Giày Sneakeer Thể Thao',15000000,4,'adidas_nam_sneaker4.jpg','Da & Vãi',3),
		 (N'Adidas Sneaker PS',N'Adidas',0,37,500,N'Giày Sneakeer Thể Thao',2450000,4,'adidas_nam_sports1.jpg','Vãi',1),
		 (N'Nike Sneaker GLK',N'Nike',1,43,600,N'Giày Sneakeer nike Thể Thao',7620000,4,'adidas_nam_sports2.jpg','Vãi',2)
GO
insert into DonHang(NgayDatHang,MaNV,MaKH)
 values  ('2020-11-20','NV001',1),
		 ('2019-05-01','NV004',4),
		 ('2020-08-14','NV001',2),
		 ('2018-02-16','NV005',1),
		 ('2019-01-05','NV004',8),
		 ('2020-04-12','NV008',3),
		 ('2020-10-28','NV0010',2),
		 ('2017-01-20','NV001',1),
		 ('2018-06-01','NV004',4),
		 ('2019-02-14','NV001',2),
		 ('2020-06-16','NV005',1),
		 ('2019-11-05','NV004',8),
		 ('2020-12-12','NV008',3),
		 ('2020-12-28','NV0010',2),
		 ('2020-10-20','NV001',1),
		 ('2019-09-01','NV004',4),
		 ('2020-01-14','NV001',2),
		 ('2018-04-16','NV005',1),
		 ('2019-03-05','NV004',8),
		 ('2020-01-12','NV008',3),
		 ('2020-06-28','NV0010',2),
		 ('2020-01-20','NV001',1),
		 ('2019-03-01','NV004',4),
		 ('2020-07-14','NV001',2),
		 ('2018-06-16','NV005',1),
		 ('2019-04-05','NV004',8),
		 ('2020-12-12','NV008',3),
		 ('2020-10-28','NV0010',2),
		 ('2020-11-20','NV001',1),
		 ('2019-05-01','NV004',4),
		 ('2020-01-14','NV001',2),
		 ('2018-03-16','NV005',1),
		 ('2019-02-05','NV004',8),
		 ('2020-05-12','NV008',3),
		 ('2020-09-28','NV0010',2)
GO
insert into DonHangChiTiet(MaDH,MaSP,DonGia,SoLuong)
 values	   (1,2,5000000,2),
		   (2,1,6000000,4),
		   (3,5,9000000,2),
		   (4,9,4900000,2),
		   (5,8,22860000,3),
		   (6,3,6000000,2),
		   (7,2,5000000,2),
		   (8,1,6000000,4),
		   (9,5,9000000,2),
		   (10,9,4900000,2),
		   (11,8,22860000,3),
		   (12,3,6000000,2),
		   (13,2,5000000,2),
		   (14,1,6000000,4),
		   (15,5,9000000,2),
		   (16,9,4900000,2),
		   (17,8,22860000,3),
		   (18,3,6000000,2),
		   (19,2,5000000,2),
		   (20,1,6000000,4),
		   (21,5,9000000,2),
		   (22,9,4900000,2),
		   (23,8,22860000,3),
		   (24,3,6000000,2),
		   (25,2,5000000,2),
		   (26,1,6000000,4),
		   (27,5,9000000,2),
		   (28,9,4900000,2),
		   (29,8,22860000,3),
		   (30,3,6000000,2),
		   (31,2,5000000,2),
		   (32,1,6000000,4),
		   (33,5,9000000,2),
		   (34,9,4900000,2),
		   (35,8,22860000,3)