package com.ghee.formatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giahu
 */
public class DatePickerUtils {

    /**
     * Định dạng ngày theo mẫu của Việt Nam dd/MM/yyyy.
     * @param date
     * @return 
     */
    public static String setVietnameseDateFormat(Date date) {
        if (date == null) {
            return "";
        }
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();
        return dateFormatter.format(localDate);
    }

    /**
     * Định dạng ngày theo mẫu của Việt Nam dd/MM/yyyy.
     * @param datePicker 
     */
    public static void setVietnameseDateFormat(DatePicker datePicker) {
        String pattern = "dd/MM/yyyy";
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                }
                return null;
            }
        });
    }

    /**
     * Cấu hình DatePicker để không cho phép chọn các ngày trước ngày hiện tại.
     *
     * @param datePicker DatePicker cần cấu hình
     */
    public static void disablePastDates(DatePicker datePicker) {
        // Lấy ngày hiện tại
        final LocalDate today = LocalDate.now();

        // Thiết lập dayCellFactory để vô hiệu hóa các ngày trước ngày hiện tại
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isBefore(today)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;"); // Màu xám nhạt
                        }
                    }
                };
            }
        });

        // Đặt giá trị mặc định là ngày hiện tại
        datePicker.setValue(today);
    }
    
    /**
     * Cấu hình DatePicker để không cho phép chọn các ngày sau ngày hiện tại.
     * @param datePicker 
     */
    public static void disableFutureDates(DatePicker datePicker) {
        // Lấy ngày hiện tại
        final LocalDate today = LocalDate.now();

        // Thiết lập dayCellFactory để vô hiệu hóa các ngày trước ngày hiện tại
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isAfter(today)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;"); // Màu xám nhạt
                        }
                    }
                };
            }
        });

        // Đặt giá trị mặc định là ngày hiện tại
        datePicker.setValue(today);
    }

    /**
     * Cấu hình DatePicker "Ngày kết thúc" để không cho phép chọn các ngày trước
     * "Ngày bắt đầu".
     *
     * @param startDatePicker DatePicker cho ngày bắt đầu
     * @param endDatePicker DatePicker cho ngày kết thúc
     */
    public static void restrictEndDate(DatePicker startDatePicker, DatePicker endDatePicker) {
        // Thiết lập dayCellFactory cho endDatePicker
        endDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate startDate = startDatePicker.getValue();
                        if (startDate != null && date.isBefore(startDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;"); // Màu xám nhạt
                        }
                    }
                };
            }
        });

        // Cập nhật endDatePicker khi startDatePicker thay đổi
        startDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate endDate = endDatePicker.getValue();
                if (endDate == null || endDate.isBefore(newValue)) {
                    endDatePicker.setValue(newValue); // Đặt ngày kết thúc ít nhất bằng ngày bắt đầu
                }
            }
        });

        // Đặt giá trị mặc định cho endDatePicker (nếu cần)
        if (startDatePicker.getValue() != null) {
            endDatePicker.setValue(startDatePicker.getValue());
        }
    }
}
