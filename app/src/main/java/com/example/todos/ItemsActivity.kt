package com.example.todos

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rv_lists

class ItemsActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(items_toolbar)
        title = "Список завдань"
        dbHandler =DBHandler(this)

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
            dialog.setNegativeButton("Скасувати"){_: DialogInterface, _: Int ->

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

    class ItemsAdapter(val activity: AppCompatActivity, val list :MutableList<Item>):
        RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

        class ViewHolder(v : View) :RecyclerView.ViewHolder(v){
            val text :TextView = v.findViewById(R.id.tv_item_text)
            val isChecked :CheckBox = v.findViewById(R.id.cb_item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_item,parent,false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = list[position].text
        }

    }
}
