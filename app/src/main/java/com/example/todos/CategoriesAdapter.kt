package com.example.todos

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.models.Category
import kotlinx.android.synthetic.main.rv_category.view.*

class CategoriesAdapter(val activity : CategoriesActivity, val list :MutableList<Category>):
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_category,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = list[position].name
        holder.tv_count.text = DBHandler(activity).getCategoryItems(list[position].id).size.toString()+"("+DBHandler(activity).getCategoryItems(list[position].id).count{item -> item.isCompleted }+")"
        holder.tv_name.setOnClickListener {
            val intent = Intent(activity,CategoryItemsActivity::class.java)
            intent.putExtra("intent_category_id",list[position].id)
            intent.putExtra("intent_category_name",list[position].name)
            activity.startActivity(intent)
        }
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val tv_name : TextView = v.tv_name
        val tv_count : TextView = v.tv_count
    }
}