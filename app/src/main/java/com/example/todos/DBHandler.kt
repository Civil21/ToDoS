package com.example.todos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

}
