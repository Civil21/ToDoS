package com.example.todos

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.rv_item.view.*
import java.util.*

class ItemsAdapter(val activity : ItemActivity, val list: MutableList<Item>):

    RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_text.text = list[position].text
        holder.cb_item.isChecked = list[position].isCompleted
        holder.tv_category.text = DBHandler(activity).findByIdCategory(list[position].category_id)?.name
        if(list[position].isCompleted){
            holder.tv_time.text = "Завершено "+list[position].comletedAt
        }else{
            holder.tv_time.text = "Створено "+list[position].createdAt
        }
        holder.cb_item.setOnClickListener {
            list[position].isCompleted = !list[position].isCompleted
            if (list[position].isCompleted) {
                list[position].comletedAt = Calendar.getInstance().getTime().toString()
            } else {
                list[position].comletedAt = ""
            }
            DBHandler(activity).updateItem(list[position])
            activity.refreshList()
        }
        holder.tv_text.setOnClickListener{
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Оновлення")
            val view = activity.layoutInflater.inflate(R.layout.item_dialog,null)
            val et_text =view.findViewById<EditText>(R.id.et_item_text)
            val et_category =view.findViewById<EditText>(R.id.et_item_category)
            et_text.setText(list[position].text)
            et_category.setText(DBHandler(activity).findByIdCategory(list[position].category_id)?.name)
            dialog.setView(view)
            dialog.setPositiveButton("Оновити") { _: DialogInterface, _: Int->
                if(et_text.text.isNotEmpty()){
                    val item = list[position]
                    item.text = et_text.text.toString()
                    val category = DBHandler(activity).findByNameCategory(et_category.text.toString().toLowerCase())
                    item.category_id = category.id
                    DBHandler(activity).updateItem(item)
                    activity.refreshList()
                }
            }
            dialog.setNegativeButton("Відмінити"){
                    _: DialogInterface, _: Int ->
            }
            dialog.show()
        }
        holder.iv_delete.setOnClickListener{
            DBHandler(activity).deleteItem(list[position].id)
            activity.refreshList()
        }
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val tv_text : TextView = v.tv_item_text
        val cb_item :CheckBox = v.cb_item
        val tv_category :TextView =v.tv_item_cagtegory
        val tv_time :TextView = v.tv_item_time
        val iv_delete :ImageView = v.iv_delete
    }
}

