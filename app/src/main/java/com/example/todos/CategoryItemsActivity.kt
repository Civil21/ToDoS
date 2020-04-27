package com.example.todos

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.activity_category_items.*
import kotlinx.android.synthetic.main.activity_items.*

class CategoryItemsActivity : ItemActivity() {

    lateinit var dbHandler : DBHandler
    var CategoryId : Long = -1
    var CategoryName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_items)
        setSupportActionBar(category_toolbar)
        CategoryName = intent.getStringExtra("intent_category_name")
        title = CategoryName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHandler =DBHandler(this)
        CategoryId = intent.getLongExtra("intent_category_id",-1)

        rv_category_lists.layoutManager = LinearLayoutManager(this)

        fab_category_item.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Додати нове завдання")
            val view = layoutInflater.inflate(R.layout.item_dialog,null)
            val et_text =view.findViewById<EditText>(R.id.et_item_text)
            val et_category =view.findViewById<EditText>(R.id.et_item_category)
            et_category.setText(CategoryName)
            dialog.setView(view)
            dialog.setPositiveButton("Додати") { _: DialogInterface, _: Int->
                if(et_text.text.isNotEmpty()){
                    val item = Item()
                    item.text = et_text.text.toString()
                    val category = dbHandler.findByNameCategory(et_category.text.toString().toLowerCase())
                    item.category_id = category.id
                    dbHandler.addItem(item)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Скасувати"){
                    _: DialogInterface, _: Int ->
            }
            dialog.show()
        }
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    override fun refreshList(){
        rv_category_lists.adapter =ItemsAdapter(this,dbHandler.getCategoryItems(CategoryId))
    }
}
