package com.example.todos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_category_items.*
import kotlinx.android.synthetic.main.activity_items.*

class CategoryItemsActivity : AppCompatActivity() {

    lateinit var dbHandler : DBHandler
    var CategoryId : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_items)
        dbHandler =DBHandler(this)
        CategoryId = intent.getLongExtra("intent_category_id",-1)

        rv_category_lists.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList(){
        rv_category_lists.adapter =ItemsAdapter(this,dbHandler.getCategoryItems(CategoryId))
    }
}
