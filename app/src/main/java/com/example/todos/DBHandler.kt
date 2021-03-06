package com.example.todos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todos.models.Category
import com.example.todos.models.Item
import kotlin.collections.ArrayList

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){

    val createItemsTable =
        "CREATE TABLE $TABLE_ITEMS (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_TEXT varchar," +
                "$COL_CATEGORY_ID integer," +
                "$COL_IS_COMPLETED integer,"+"" +
                "$COL_COMPLETED_AT datetime);"

    val createCategoriesTable =
        "CREATE TABLE $TABLE_CATEGORIES (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_NAME varchar);"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createItemsTable)
        db.execSQL(createCategoriesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropItemsTable = "DROP TABLE $TABLE_ITEMS"
        val dropCategories = "DROP TABLE $TABLE_CATEGORIES"
        db.execSQL(dropItemsTable)
        db.execSQL(dropCategories)
        db.execSQL(createItemsTable)
        db.execSQL(createCategoriesTable)
    }

    fun getItems() :MutableList<Item>{
        val result : MutableList<Item> =ArrayList()
        val db =readableDatabase
        val queryResult =db.rawQuery("SELECT * FROM $TABLE_ITEMS ORDER BY $COL_IS_COMPLETED ASC",null)
        if(queryResult.moveToFirst()){
            do{
                val item =Item()
                item.id =queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.text=queryResult.getString(queryResult.getColumnIndex(COL_TEXT))
                item.category_id =queryResult.getLong(queryResult.getColumnIndex(COL_CATEGORY_ID))
                item.createdAt =queryResult.getString(queryResult.getColumnIndex(COL_CREATED_AT))
                item.isCompleted = queryResult.getInt(queryResult.getColumnIndex(COL_IS_COMPLETED))==1
                item.comletedAt = queryResult.getString(queryResult.getColumnIndex(COL_CREATED_AT))
                result.add(item)
            }while(queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun getCategoryItems(categoryId: Long): MutableList<Item> {
        val result : MutableList<Item> =ArrayList()
        val db =readableDatabase
        val queryResult =db.rawQuery("SELECT * FROM $TABLE_ITEMS WHERE $COL_CATEGORY_ID = $categoryId ORDER BY $COL_IS_COMPLETED ASC",null)
        if(queryResult.moveToFirst()){
            do{
                val item =Item()
                item.id =queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.text=queryResult.getString(queryResult.getColumnIndex(COL_TEXT))
                item.category_id =queryResult.getLong(queryResult.getColumnIndex(COL_CATEGORY_ID))
                item.createdAt =queryResult.getString(queryResult.getColumnIndex(COL_CREATED_AT))
                item.isCompleted = queryResult.getInt(queryResult.getColumnIndex(COL_IS_COMPLETED))==1
                item.comletedAt = queryResult.getString(queryResult.getColumnIndex(COL_CREATED_AT))
                result.add(item)
            }while(queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun getChekedItems(isCompleted: Int): MutableList<Item> {
        val result : MutableList<Item> =ArrayList()
        val db =readableDatabase
        val queryResult =db.rawQuery("SELECT * FROM $TABLE_ITEMS WHERE $COL_IS_COMPLETED = $isCompleted ORDER BY $COL_IS_COMPLETED ASC",null)
        if(queryResult.moveToFirst()){
            do{
                val item =Item()
                item.id =queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.text=queryResult.getString(queryResult.getColumnIndex(COL_TEXT))
                item.category_id =queryResult.getLong(queryResult.getColumnIndex(COL_CATEGORY_ID))
                item.createdAt =queryResult.getString(queryResult.getColumnIndex(COL_CREATED_AT))
                item.isCompleted = queryResult.getInt(queryResult.getColumnIndex(COL_IS_COMPLETED))==1
                item.comletedAt = queryResult.getString(queryResult.getColumnIndex(COL_CREATED_AT))
                result.add(item)
            }while(queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun addItem(item :Item): Boolean {
        var db =writableDatabase
        var cv = ContentValues()
        cv.put(COL_TEXT,item.text)
        cv.put(COL_IS_COMPLETED,item.isCompleted)
        cv.put(COL_CATEGORY_ID,item.category_id)

        val result = db.insert(TABLE_ITEMS,null,cv)
        return result!=(-1).toLong()
    }

    fun updateItem(item :Item){
        var db =writableDatabase
        var cv = ContentValues()
        cv.put(COL_TEXT,item.text)
        cv.put(COL_IS_COMPLETED,item.isCompleted)
        cv.put(COL_CATEGORY_ID,item.category_id)
        cv.put(COL_COMPLETED_AT,item.comletedAt)
        db.update(TABLE_ITEMS, cv, "$COL_ID=?", arrayOf(item.id.toString()))
        val items = getItems()
    }
    fun deleteItem(itemId:Long){
        val db = writableDatabase
        db.delete(TABLE_ITEMS,"$COL_ID=?", arrayOf(itemId.toString()))
    }

    fun getCategories() :MutableList<Category>{
        val result : MutableList<Category> =ArrayList()
        val db =readableDatabase
        val queryResult =db.rawQuery("SELECT * FROM $TABLE_CATEGORIES",null)
        if(queryResult.moveToFirst()){
            do{
                val category = Category()
                category.id =queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                category.name=queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                result.add(category)
            }while(queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun addCategory(category: Category): Boolean {
        var db =writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME,category.name)

        val result = db.insert(TABLE_CATEGORIES,null,cv)
        return result!=(-1).toLong()
    }

    fun deleteCategory(categoryId: Long){
        val db = writableDatabase
        db.delete(TABLE_ITEMS,"$COL_CATEGORY_ID=?", arrayOf(categoryId.toString()))
        db.delete(TABLE_CATEGORIES,"$COL_ID=?", arrayOf(categoryId.toString()))
    }

    fun findByNameCategory(name: String): Category {
        val db =readableDatabase
        var queryResult = db.rawQuery("SELECT * FROM $TABLE_CATEGORIES WHERE $COL_NAME = ?",
            arrayOf(name)
        )
        val category = Category()
        category.name = name
        if(queryResult.moveToFirst()) {
            category.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
        }else{
            addCategory(category)
            queryResult = db.rawQuery("SELECT * FROM $TABLE_CATEGORIES WHERE $COL_NAME = ?",
                arrayOf(name)
            )
            queryResult.moveToFirst()
            category.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
        }
        return category
    }
    fun findByIdCategory(Id: Long): Category? {
        val db =readableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_CATEGORIES WHERE $COL_ID = $Id",null)
        if(queryResult.moveToFirst()) {
            val category = Category()
            category.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
            category.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
            return category
        }else{
            return null
        }
    }



}
