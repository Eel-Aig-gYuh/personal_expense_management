/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.utils;

import com.ghee.pojo.Users;

/**
 *
 * @author giahu
 */
public class ManageUser {
    private static Users currentUser;
    
    public static void setCurrentUser(Users user){
        currentUser = user;
    }
    
    public static Users getCurrentUser(){
        return currentUser;
    }
    
    public static boolean isLogin(){
        return currentUser != null;
    }
}
