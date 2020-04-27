package com.example.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.rv_item.view.*
import java.util.*

class ItemsAdapter(val activity : AppCompatActivity, val list: MutableList<Item>):

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
                holder.tv_time.text = "Завершено "+list[position].comletedAt
            } else {
                list[position].comletedAt = ""
                holder.tv_time.text = "Створено "+list[position].createdAt
            }
            holder.cb_item.isChecked = list[position].isCompleted
            DBHandler(activity).updateItem(list[position])
        }
        holder.iv_delete.setOnClickListener{
            DBHandler(activity).deleteItem(list[position].id)
            list.removeAt(position)
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

