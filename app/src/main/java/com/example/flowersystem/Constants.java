package com.example.flowersystem;

import com.example.flowersystem.dto.CustomerDTO;

public class Constants {
    public static CustomerDTO LOGGED_IN_CUSTOMER;

    //Để hiện thị trên giao diện
    public static class OrderStatusString {
        public static final String CREATED = "Đã đặt hàng"; //lúc bấm đặt hàng
        public static final String SHIPPED = "Đã giao"; //lúc đơn hàng đã được giao
        public static final String CANCELLED = "Đã hủy"; //đã hủy đơn
        //2 cái dưới để làm cho phần prepaid
        public static final String WAITING_FOR_PAYMENT = "Chưa thanh toán"; //chọn thanh toán trước nhưng chưa thanh toán
        public static final String PREPAID_DONE = "Đã thanh toán"; //đã thanh toán trước
    }
    //Để gửi và nhận với backend
    public static class OrderStatusNumber {
        public static final String CREATED = "1"; //lúc bấm đặt hàng
        public static final String SHIPPED = "2"; //lúc đơn hàng đã được giao
        public static final String CANCELLED = "3"; //đã hủy đơn
        //2 cái dưới để làm cho phần prepaid
        public static final String WAITING_FOR_PAYMENT = "4"; //chọn thanh toán trước nhưng chưa thanh toán
        public static final String PREPAID_DONE = "5"; //đã thanh toán trước
    }
}
