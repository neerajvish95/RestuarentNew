package restuarent.sirumalayil.app.in.sqlDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import restuarent.sirumalayil.app.in.model.Cart;


public class DatabaseHandler extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "foodCart";


    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table food_cart (id integer primary key autoincrement, " +
                "item text, price integer, count integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists food_cart");
    }

    public void insertItems(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("item", cart.getName());
        values.put("price", cart.getPrice());
        values.put("count", cart.getCount());

        db.insert("food_cart",null,values);
    }

    public boolean updateItem(String name, int count, int price){
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "update food_cart set count ='"+count+"', price = '"+price+"'where item= '"+name+"'";
        db.execSQL(update);
        return true;
    }

    public List<Cart> getCartItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Cart> listCart =new ArrayList<Cart>();

        String selectQuery = "select * from food_cart";
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor != null){
           if (cursor.moveToFirst()){
               while (!cursor.isAfterLast()){
                   try{

                       Cart cart = new Cart();
                       cart.setId(cursor.getInt(cursor.getColumnIndex("id")));
                       cart.setCount(cursor.getInt(cursor.getColumnIndex("count")));
                       cart.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                       cart.setName(cursor.getString(cursor.getColumnIndex("item")));

                       listCart.add(cart);
                       cursor.moveToNext();

                   }catch (Exception e){e.printStackTrace();}
               }
           }
        }
        assert cursor != null;
        cursor.close();
        return listCart;
    }

    public String getItemName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select item from food_cart where item='"+name+"'";
        Cursor cursor = db.rawQuery(query,null);
        String itemName = "";
        if (cursor != null){
            while (cursor.moveToNext()){
                itemName = cursor.getString(cursor.getColumnIndex("item"));
            }
        }

        cursor.close();
        return itemName;
    }

    public int getItemCount(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select count from food_cart where item='"+name+"'";
        Cursor cursor = db.rawQuery(query,null);
        int itemCount = 0;
        if (cursor != null){
            while (cursor.moveToNext()){
                itemCount = cursor.getInt(cursor.getColumnIndex("count"));
            }
        }

        cursor.close();
        return itemCount;
    }

    public int getItemPrice(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select price from food_cart where item='"+name+"'";
        Cursor cursor = db.rawQuery(query,null);
        int itemPrice = 0;
        if (cursor != null){
            while (cursor.moveToNext()){
                itemPrice = cursor.getInt(cursor.getColumnIndex("price"));
            }
        }

        cursor.close();
        return itemPrice;
    }

    public boolean deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "delete from food_cart where item='"+name+"'";
        db.execSQL(deleteQuery);
        db.close();
        return true;
    }

    public int getTotalPrice(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalPrice = 0;
        String query = "select sum(price) from food_cart";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            cursor.getInt(0);
        }
        totalPrice = cursor.getInt(0);

        return totalPrice;
    }

    public int getTotalCount(){

        SQLiteDatabase db = this.getReadableDatabase();
        int totalCount = 0;
        String query = "select sum(count) from food_cart";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            cursor.getInt(0);
        }
        totalCount = cursor.getInt(0);

        return totalCount;
    }

    public boolean deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "delete from food_cart";
        db.execSQL(deleteQuery);
        db.close();
        return true;
    }
}
