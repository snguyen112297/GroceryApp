package com.example.groceryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryapp.models.Product

class DBHelper (context: Context): SQLiteOpenHelper(context, DATA_NAME, null, DATABASE_VERSION){

    companion object{
        const val DATA_NAME = "cartDB"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cart"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_QUANTITY = "quantity"

        const val createTable = "create table $TABLE_NAME ($COLUMN_ID CHAR(250), $COLUMN_NAME CHAR(250), $COLUMN_PRICE DECIMAL, $COLUMN_IMAGE CHAR(2500), $COLUMN_QUANTITY INTEGER)"
        const val dropTable = "drop table $TABLE_NAME"
    }

    override fun onCreate(database: SQLiteDatabase?){
        database?.execSQL(createTable)
        Log.d("TAG", "OnCreate")
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL(dropTable)
        onCreate(database)
    }

    fun addProduct(product: Product){
        var database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, product._id)
        contentValues.put(COLUMN_NAME, product.productName)
        contentValues.put(COLUMN_PRICE, product.price)
        contentValues.put(COLUMN_IMAGE, product.image)
        contentValues.put(COLUMN_QUANTITY, 1)
        database.insert(TABLE_NAME, null, contentValues)
    }

    fun updateProduct(product: Product, mode: Int){
        var database = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs: Array<String> = arrayOf(product._id)
        val contentValues = ContentValues()
        var quantity = getProductQuantity(product._id)
        if (mode == 1) {
            contentValues.put(COLUMN_QUANTITY, quantity + 1)
            database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
        } else {
            if (quantity > 1) {
                contentValues.put(COLUMN_QUANTITY, quantity - 1)
                database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
            } else {
                deleteProduct(product._id)
            }
        }
    }

    fun deleteProduct(id: String){
        var database = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id.toString())
        database.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getProduct():ArrayList<Product>{
        var productList: ArrayList<Product> = ArrayList()
        var database = writableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PRICE,
            COLUMN_IMAGE,
            COLUMN_QUANTITY,
        )
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()){
            do{
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                productList.add(Product(quantity, "", true, 1, "", id, 1, 1, name, image, "", price, 0.0, 1))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

    fun getTotal(): Double{
        var productList = this.getProduct()
        var total = 0.0
        for (i in 0 until productList.size){
            total += productList[i].price*productList[i].quantity
        }
        return total
    }

    fun getSize(): Int{
        return getProduct().size
    }

    fun getNumberOfItems(): Int{
        var total = 0
        var productList = this.getProduct()
        for (i in 0 until productList.size){
            total += productList[i].quantity
        }
        return total
    }

    fun getProductQuantity(id: String): Int{
        /*
        var productList = this.getProduct()
        for (i in 0 until productList.size){
            if (productList[i]._id == id)
                return productList[i].quantity
        }
        */
        var database = writableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_QUANTITY,
        )
        var cursor = database.query(TABLE_NAME, columns, "$COLUMN_ID = ?", arrayOf(id), null, null, COLUMN_ID)
        if (cursor != null && cursor.moveToFirst()){
            var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
            return quantity
        }
        cursor.close()
        return 0
    }

}