package com.example.todos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_items.*

class CategoriesActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setSupportActionBar(categories_toolbar)

        rv_categories.layoutManager = LinearLayoutManager(this)
        dbHandler =DBHandler(this)
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList(){
        rv_categories.adapter =CategoriesAdapter(this,dbHandler.getCategories())
    }
}
