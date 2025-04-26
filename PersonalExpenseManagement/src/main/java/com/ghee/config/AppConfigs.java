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
    
    // ================ LOGIN / REGISTER
    
    // ^ và $ đảm bảo kiểm tra toàn bộ chuỗi
    // .+ là kiểm tra không rỗng.
    public static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*(),.?\":{}|<>/\\[]]).+$";
    public static String PATTERN_STRING = "^[\\\\p{L}]+$";
    public static String PATTERN_SPECIAL_CHAR = ".*[!@#$%^&*(),.?\":{}|\\[\\]<>/\\\\].*";
    public static String PATTERN_NUMBER = ".*\\d+.*";
    public static String PATTERN_SPACE = ".*\\s+.*";
    public static String PATTERN_EMAIL = "^(?=.*[@.]).+$";
    
    public static int LENGHT_OF_ACCOUNT = 7;
    public static int LENGHT_OF_LASTNAME = 50;
    public static int LENGHT_OF_FIRSTNAME = 10;
    
    public static String NULL_USERNAME = "Vui lòng điền tên tài khoản !";
    public static String NULL_PASSWORD = "Vui lòng điền mật khẩu !";
    
    public static String ERROR_HAS_SPACE_USERNAME = "Tài khoản không chứa khoảng trắng!";
    public static String ERROR_HAS_SPACE_PASSWORD = "Mật khẩu không chứa khoảng trắng!";
    
    public static String NULL_FIRSTNAME = "Vui lòng điền tên của bạn !";
    public static String NULL_LASTNAME = "Vui lòng điền họ và chữ lót của bạn !";
    
    public static String ERROR_LASTNAME_PATTERN_CHAR = "Họ và chữ lót không được chứa ký tự đặc biệt!";
    public static String ERROR_LASTNAME_PATTERN_NUMBER = "Họ và chữ lót không được chứa số!";
    public static String ERROR_LASTNAME_OUT_OF_LENGHT = "Họ và chữ lót không được dài quá " + LENGHT_OF_LASTNAME + " ký tự!";
    
    public static String ERROR_FIRSTNAME_PATTERN_CHAR = "Tên không được chứa ký tự đặc biệt!";
    public static String ERROR_FIRSTNAME_PATTERN_NUMBER = "Tên không được chứa số!";
    public static String ERROR_FIRSTNAME_OUT_OF_LENGHT = "Tên không được dài quá " + LENGHT_OF_FIRSTNAME + " ký tự!";
    
    public static String ERROR_NOT_ENOUGH_INFORMATION = "Vui lòng điền đầy đủ thông tin !";
    public static String ERROR_EMAIL_PATTERN = "Email không hợp lệ";
    
    public static String ERROR_LENGHT_OF_USERNAME = "Độ dài không hợp lệ! \n Vui lòng nhập lại tên tài khoản phải trên 7 ký tự !";
    public static String ERROR_LENGHT_OF_PASSWORD = "Độ dài không hợp lệ! \n Vui lòng nhập lại mật khẩu phải trên 7 ký tự !";
    
    public static String ERROR_PASS_PATTERN = "Lỗi mật khẩu không đủ mạnh!\n Phải có ít nhất 1 ký tự hoa, 1 ký tự thường, 1 số và 1 ký tự đặc biệt !";
    public static String ERROR_PASS_AND_CONFIRM = "Lỗi mật khẩu không trùng khớp, vui lòng nhập lại !";
    
    // ================ BUDGET
    public static int MIN_TARGET = 100000;
    public static int MAX_TARGET = 100000000;
    
    public static String ERROR_CATEGORY_IS_NULL = "Vui lòng chọn một danh mục cho ngân sách !";
    public static String ERROR_TARGET_IS_NEGATIVE = "Vui lòng điền mục tiêu ngân sách không âm !";
    public static String ERROR_TARGET_LESS_THAN_MIN = String.format("Vui lòng điền mục tiêu ngân sách không NHỎ hơn %d", MIN_TARGET);
    public static String ERROR_TARGET_LESS_THAN_MAX = String.format("Vui lòng điền mục tiêu ngân sách không VƯỢT hơn %d", MAX_TARGET);
    
    public static String ERROR_DATE_IS_NOT_CORRECT = "Vui lòng điền ngày phù hợp !";
    
    // ================ TRANSACTION
    public static String ERROR_AMOUNT = "Vui lòng điền số tiền hợp lệ !";
    public static String ERROR_LENGHT_DESCRIPTION = "Vui lòng nhập miêu tả có độ dài nhỏ hơn 255 ký tự !";
    
    // ================ CATEGORY
    public static String ERROR_INVALID_TYPE = "Vui lòng chọn loại ngân sách hợp lệ, thu hoặc chi.";
    
    // ================ NO INFORMATION
    public static String NO_DATA_TRANSACTION = "Danh sách giao dịch hiện đang trống, vui lòng thêm giao dịch !";
    
    // ================ DATABASE
    public static String ERROR_DATABASE = "Lỗi kết nối database";
}
