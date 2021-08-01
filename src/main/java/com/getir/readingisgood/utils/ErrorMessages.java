package com.getir.readingisgood.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public static final String AUTHENTICATION_ERROR = "Authentication information is wrong";
    public static final String AUTHENTICATION_NOT_FOUND = "Authentication not found";
    public static final String USER_NOT_FOUND = "Cannot find user";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_STOCK_ERROR = "Book stock is not enough";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String ORDER_ALREADY_CANCELLED = "Order already cancelled";
    public static final String USERNAME_OR_EMAIL_EXIST = "Username or Email already used";
}
