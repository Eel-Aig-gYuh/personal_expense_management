<h1>ỨNG DỤNG QUẢN LÝ CHI TIÊU CÁ NHÂN</h1>

## GIỚI THIỆU
- Đây là dự án nhóm thực hiện trong môn kiểm thử, để quản lý chi tiêu cá nhân cho người tiêu dùng. </br>
- Được phát triển vào khoảng Mar 12, 2025 và Apr 28, 2025. </br>
<p align="center">
  <img src="https://github.com/Eel-Aig-gYuh/personal_expense_management/blob/main/Others/Img/loginPage.png" width="250" height="250"/>
  <img src="https://github.com/Eel-Aig-gYuh/personal_expense_management/blob/main/Others/Img/budgetPage.png" width="250" height="250"/>
  <img src="https://github.com/Eel-Aig-gYuh/personal_expense_management/blob/main/Others/Img/homePage.png" width="250" height="250"/>
</p>

</br>

## MỤC LỤC
- [Giới thiệu](#giới-thiệu)
- [Tính năng](#tính-năng)
- [Kiến trúc hệ thống](#kiến-trúc-hệ-thống)
- [Cách cài đặt](#cách-cài-đặt)
- [Hướng dẫn sử dụng](#hướng-dẫn-sử-dụng)
- [Kiểm thử](#kiểm-thử)
- [Liên hệ](#liên-hệ)
- [Tác Giả](#tác-giả)

## TÍNH NĂNG
- Thêm và phân loại giao dịch.
- Thiết lập ngân sách.
- Bảo cáo tài chính.

## KIẾN TRÚC HỆ THỐNG 
- Tổng quan: theo kiến trúc MVC.
- Back-end: Java/MySQL. </br>
- Front-end: JavaFX. </br>
- Task Management: Git/Github.

## CÁCH CÀI ĐẶT

### 1. Chuẩn bị cơ sở dữ liệu </br>
1.1. Tải database bằng cách down file: **[this file](https://github.com/Eel-Aig-gYuh/personal_expense_management/tree/main/Database)** </br>
1.2. Vào SQL Workbench tạo schema mới và đặt tên là: personalexpensemanagementdb. </br>
1.3. Sau khi đã tạo schema export database vào trong schema: **personalexpensemanagementdb** vừa mới tạo.
### 2. Clone the repository
```bash
   git clone https://github.com/Eel-Aig-gYuh/personal_expense_management.git
```

## HƯỚNG DẪN SỬ DỤNG
- Chắc chắn bạn đã cài đặt Netbean và JDK phù hợp (Dự án hiện tại sử dụng Apache Netbeans IDE 25 và JDK 23).
```
PersonalExpenseManagement/
├── src/                   
│   ├── main/java/                               # sản phẩm
│   │   ├── com.ghee.config                      # cấu hình
│   │   ├── com.ghee.formatter                   # định dạng (ngày/tháng/năm, tiền VND, ...)
│   │   ├── com.ghee.personalexpensemanagement   # tầng controller
│   │   ├── com.ghee.pojo                        # đại diện cho table dưới csdl
│   │   ├── com.ghee.service                     # tầng service
│   │   ├── com.ghee.utils 
```
- Sau khi clone repository trỏ tới thư mục và **Run**.

## KIỂM THỬ
- Chi tiết kiểm thử trong file: **[this file](https://github.com/Eel-Aig-gYuh/personal_expense_management/blob/main/Others/Nhom4_QuanLyChiTieu.xlsx)**
```
PersonalExpenseManagement/
├── src/                   
│   ├── test/                        
│   │   ├── java/                               # test các chức năng.
│   │   ├── resources/                          # test case.
```


## LIÊN HỆ
<table width="100" align='left'>
    <tr>
        <td align='center' width="60">
            <a href="https://www.facebook.com/nhois031/"><img src="https://th.bing.com/th/id/OIP.K61w8tCEKaKN--vUwjeSSwHaHa?w=201&h=201&c=7&r=0&o=5&dpr=1.3&pid=1.7" width="60"></a>
        </td>
        <td align='center' width="60">
            <a href="https://www.instagram.com/nhois031/"><img src="https://cdn-icons-png.flaticon.com/512/1409/1409946.png"></a>
        </td>
        <td align='center' width="60">
            <a href="https://www.linkedin.com/in/huy-l%C3%AA-0871a92b8/"><img src="https://cdn-icons-png.flaticon.com/512/1409/1409945.png" width="60"></a>
        </td>
    </tr> 
</table>
</br>
</br>

## TÁC GIẢ
- **[Lê Gia Huy](https://github.com/Eel-Aig-gYuh)**  
  *Role*: developer/quản lý tiến độ, phát triển sản phẩm.

- **[hoaqhue](https://github.com/hoaqhue)**  
  *Role*: developer/unit test.

- **[NguyenThiMai2k4](https://github.com/NguyenThiMai2k4)** 
  *Role*: tester.
  
