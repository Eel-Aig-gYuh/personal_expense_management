# PERSONAL EXPENSE MANAGEMENT APPLICATION

## 📌 INTRODUCTION
- This is a group project for the **Software Testing** course, developed to help users manage their personal expenses.
- Development period: **March 12, 2025 – April 28, 2025**.

<p align="center">
  <img src="https://raw.githubusercontent.com/Eel-Aig-gYuh/personal_expense_management/main/Others/Img/loginPage.png" width="250" height="250"/>
  <img src="https://raw.githubusercontent.com/Eel-Aig-gYuh/personal_expense_management/main/Others/Img/budgetPage.png" width="250" height="250"/>
  <img src="https://raw.githubusercontent.com/Eel-Aig-gYuh/personal_expense_management/main/Others/Img/homePage.png" width="250" height="250"/>
</p>

---

## 📚 TABLE OF CONTENTS
- [Introduction](#-introduction)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Installation Guide](#-installation-guide)
- [Usage Guide](#-usage-guide)
- [Testing](#-testing)
- [Contact](#-contact)
- [Authors](#-authors)

---

## ✅ FEATURES
- Add and categorize transactions.
- Set monthly budgets.
- Generate financial reports.

---

## 🧩 SYSTEM ARCHITECTURE
- Architecture: **MVC Pattern**.
- **Backend**: Java with MySQL.  
- **Frontend**: JavaFX.  
- **Task Management**: Git & GitHub.

---

## 💻 INSTALLATION GUIDE

### 1. Prepare the database
1.1. Download the database from this folder: [Database](https://github.com/Eel-Aig-gYuh/personal_expense_management/tree/main/Database)  
1.2. Open SQL Workbench and create a schema named: `personalexpensemanagementdb`  
1.3. Import the database SQL dump into the created schema.

### 2. Clone the repository
```
git clone https://github.com/Eel-Aig-gYuh/personal_expense_management.git
```

## 🚀 USAGE GUIDE
- Make sure you have installed a compatible version of **Apache NetBeans IDE** and **JDK**.  
  This project was developed using **Apache NetBeans IDE 25** and **JDK 23**.
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
- After cloning the repository, open the project in NetBeans, navigate to the source directory, and click **Run** to start the application.

---

## 🧪 TESTING
- Detailed test cases and execution results are provided in the following file:  
  📄 **[this file](https://github.com/Eel-Aig-gYuh/personal_expense_management/blob/main/Others/Nhom4_QuanLyChiTieu.xlsx)**
```
PersonalExpenseManagement/
├── src/                   
│   ├── test/                        
│   │   ├── java/                               # test các chức năng.
│   │   ├── resources/                          # test case.
```

---

## 📬 CONTACT
<table width="100" align='left'>
    <tr>
        <td align='center' width="60">
            <a href="https://www.facebook.com/nhois031/"><img src="https://th.bing.com/th/id/OIP.K61w8tCEKaKN--vUwjeSSwHaHa?w=201&h=201&c=7&r=0&o=5&dpr=1.3&pid=1.7" width="60"></a>
        </td>
        <td align='center' width="60">
            <a href="https://www.instagram.com/nhois031/"><img src="https://cdn-icons-png.flaticon.com/512/1409/1409946.png" width="60"></a>
        </td>
        <td align='center' width="60">
            <a href="https://www.linkedin.com/in/huy-l%C3%AA-0871a92b8/"><img src="https://cdn-icons-png.flaticon.com/512/1409/1409945.png" width="60"></a>
        </td>
    </tr> 
</table>

---

## 👥 AUTHORS

- **[Lê Gia Huy](https://github.com/Eel-Aig-gYuh)**  
  *Role*: Developer / Project Manager / Core Implementation

- **[hoaqhue](https://github.com/hoaqhue)**  
  *Role*: Developer / Unit Testing

- **[NguyenThiMai2k4](https://github.com/NguyenThiMai2k4)**  
  *Role*: Tester / Manual Testing
