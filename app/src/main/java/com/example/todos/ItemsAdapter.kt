package com.example.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.models.Item
import kotlinx.android.synthetic.main.rv_item.view.*

class ItemsAdapter(val activity : ItemsActivity, val list :MutableList<Item>):
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_text.text = list[position].text
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val tv_text : TextView = v.tv_item_text
    }
}