/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

/**
 *
 * @author giahu
 */
public class CloudinaryConfig {
    private static Cloudinary cloudinary;

    // Phương thức khởi tạo Cloudinary (singleton pattern)
    public static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            // Tải file .env
            Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .load();

            // Lấy các giá trị từ file .env
            String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
            String apiKey = dotenv.get("CLOUDINARY_API_KEY");
            String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

            // Kiểm tra nếu thiếu thông tin
            if (cloudName == null || apiKey == null || apiSecret == null) {
                throw new IllegalStateException("Thiếu thông tin Cloudinary trong file .env!");
            }

            // Khởi tạo Cloudinary
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
            ));
        }
        return cloudinary;
    }
}
