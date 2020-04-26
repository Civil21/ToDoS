package com.example.todos

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.activity_items.*

class ItemsActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(items_toolbar)
        title = "Список завдань"
        dbHandler =DBHandler(this)

        rv_lists.layoutManager = LinearLayoutManager(this)

        fab_item.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Додати нове завдання")
            val view = layoutInflater.inflate(R.layout.item_dialog,null)
            val text =view.findViewById<EditText>(R.id.et_item_text)
            dialog.setView(view)
            dialog.setPositiveButton("Додати") { _: DialogInterface, _: Int->
                if(text.text.isNotEmpty()){
                    val item =Item()
                    item.text = text.text.toString()
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

    private fun refreshList(){
        rv_lists.adapter =ItemsAdapter(this,dbHandler.getItems())
    }


}
