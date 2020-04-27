package com.example.todos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rv_lists

abstract class ItemActivity : AppCompatActivity() {
    open fun refreshList(){}
}

class MainActivity : ItemActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler =DBHandler(this)

        rv_lists.layoutManager = LinearLayoutManager(this)

        b_list.setOnClickListener{
            val intent = Intent(this,ItemsActivity::class.java)
            startActivity(intent)
        }

        b_categories.setOnClickListener {
            val intent = Intent(this,CategoriesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    override fun refreshList() {
        rv_lists.adapter = ItemsAdapter(this,dbHandler.getChekedItems(0))
    }


}
