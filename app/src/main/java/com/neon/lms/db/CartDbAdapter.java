package com.neon.lms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.neon.lms.model.CartModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CartDbAdapter extends BaseDatabaseAdapter {


    public CartDbAdapter(Context c) {
        super(c);
    }


    public void insUpdate(String id, String name, String img, String thumbImage,
                          String des, String type, String price, String category, String createdAt, String updatedAt) {
        ContentValues cv;

        cv = new ContentValues();
        cv.put(KEY_ID, id);
        cv.put(KEY_NAME, name);
        cv.put(KEY_IMGURL, img);
        cv.put(KEY_THUMBIMAGE, thumbImage);
        cv.put(KEY_DESCRIPTION, des);
        cv.put(KEY_TYPE, type);
        cv.put(KEY_PRICE, price);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_CREATEDAT, createdAt);
        cv.put(KEY_UPDATEDAT, updatedAt);


        long rowCount = ourDatabase.update(TABLE_CART, cv, KEY_ID + " = '" + id + "'", null);
        if (rowCount == 0)
            ourDatabase.insert(TABLE_CART, null, cv);


    }


    public boolean checkIsExist(String id) {
        int count;
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_CART + " WHERE " + KEY_ID + "=?", new String[]{String.valueOf(id)});
        count = cursor.getCount();
        cursor.close();
        return count == 1 ? true : false;
    }


    public int getCount() {
        int count;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }


    public void deleteItem(String vid) {
        ourDatabase.execSQL("DELETE FROM " + TABLE_CART + " WHERE " + KEY_ID + "='" + vid + "'");
    }

    public String getTotalPrice() {
        String totalPrice;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        double price = 0.0;
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            price = price + Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
            cursor.moveToNext();
        }
        totalPrice = String.valueOf(price);
        cursor.close();
        return totalPrice;

    }


    /*get favourite item */
    public ArrayList<CartModel> getCartItem() {
        ArrayList<CartModel> arrayList = new ArrayList<>();
        CartModel CartModel;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CartModel = new CartModel();


                CartModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                CartModel.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                CartModel.setCourse_image(cursor.getString(cursor.getColumnIndex(KEY_IMGURL)));
                CartModel.setImage(cursor.getString(cursor.getColumnIndex(KEY_IMGURL)));
                CartModel.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                CartModel.setPrice(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
                CartModel.setCreated_at(cursor.getString(cursor.getColumnIndex(KEY_CREATEDAT)));
                CartModel.setUpdated_at(cursor.getString(cursor.getColumnIndex(KEY_UPDATEDAT)));
                arrayList.add(CartModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }


    public JSONArray getCartData() {
        JSONArray array = new JSONArray();
        JSONObject object;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            try {
                object = new JSONObject();
                object.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
                object.put("type", cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
                object.put("price", cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
                array.put(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }

        return array;

    }
}
