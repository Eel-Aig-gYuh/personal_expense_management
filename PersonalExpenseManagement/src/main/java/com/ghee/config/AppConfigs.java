/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.config;

/**
 *
 * @author giahu
 */
public class AppConfigs {
    
    // ^ và $ đảm bảo kiểm tra toàn bộ chuỗi
    // .+ là kiểm tra không rỗng.
    public static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*(),.?\":{}|<>]).+$";
    public static int LENGHT_OF_ACCOUNT = 7;
    
    public static String ERROR_NOT_ENOUGH_INFORMATION = "Vui lòng điền đầy đủ thông tin !";
    public static String ERROR_EMAIL_PATTERN = "Vui lòng điền email hợp lệ thiếu @ !";
    
    public static String ERROR_LENGHT_OF_ACCOUNT = "Độ dài không hợp lệ, vui lòng nhập lại username và password phải trên 7 ký tự !";
    public static String ERROR_PASS_PATTERN = "Lỗi mật khẩu không đủ mạnh, phải có ít nhất 1 ký tự hoa, 1 ký tự thường, 1 số và 1 ký tự đặc biệt !";
    public static String ERROR_PASS_AND_CONFIRM = "Lỗi mật khẩu không trùng khớp, vui lòng nhập lại !";
    
}
