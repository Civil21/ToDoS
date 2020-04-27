package com.example.todos

import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.activity_items.*

class ItemsActivity : ItemActivity() {

    lateinit var dbHandler: DBHandler
    var items : MutableList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(items_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dbHandler =DBHandler(this)
        items = dbHandler.getItems()
        title = "Список Завдань "+items.size+"("+items.count{item -> item.isCompleted}+")"

        rv_lists.layoutManager = LinearLayoutManager(this)

        fab_item.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Додати нове завдання")
            val view = layoutInflater.inflate(R.layout.item_dialog,null)
            val et_text =view.findViewById<EditText>(R.id.et_item_text)
            val et_category =view.findViewById<EditText>(R.id.et_item_category)
            dialog.setView(view)
            dialog.setPositiveButton("Додати") { _: DialogInterface, _: Int->
                if(et_text.text.isNotEmpty()){
                    val item =Item()
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
       rv_lists.adapter =ItemsAdapter(this,dbHandler.getItems())
    }


}
