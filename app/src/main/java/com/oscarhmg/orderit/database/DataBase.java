package com.oscarhmg.orderit.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.oscarhmg.orderit.model.Order;
import com.oscarhmg.orderit.utils.Queries;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class DataBase extends SQLiteAssetHelper {
    private final static String DB_NAME ="OrderItDB.db";
    private final static int DB_VERSION = 1;




    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Get Cart wit orders
     */
    public List<Order> getCarts(){
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();


        String[] querySelect = {"ProductID","ProductName","Quantity","Price","Discount"};
        String sqlTable = "OrderDetail";
        queryBuilder.setTables(sqlTable);

        Cursor cursor = queryBuilder.query(database, querySelect, null, null, null, null, null);
        final List<Order> resultSet = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                resultSet.add(new Order(cursor.getString(cursor.getColumnIndex("ProductID")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))));
            }while(cursor.moveToNext());
        }
        return resultSet;
    }

    /**
     * Add order to cart
     * @param order
     */
    public void addToCart(Order order){
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format(Queries.INSERT_CART_QUERY,
                order.getProductID(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());

        database.execSQL(query);

    }

    /**
     * Delete pending orders from local DB
     */
    public void cleanCart(){
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format(Queries.DELETE_CART_QUERY);
        database.execSQL(query);

    }
}
