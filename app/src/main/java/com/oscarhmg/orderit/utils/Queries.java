package com.oscarhmg.orderit.utils;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class Queries {

    public final static String INSERT_CART_QUERY = "INSERT INTO OrderDetail(ProductID,ProductName,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s');";
    public final static String DELETE_CART_QUERY = "DELETE FROM OrderDetail";
}
