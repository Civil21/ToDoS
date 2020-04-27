package com.example.todos

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.models.Category
import com.example.todos.models.Item
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
        holder.iv_delete.setOnClickListener{
            activity.dbHandler.deleteCategory(list[position].id)
            activity.refreshList()
        }
    }


    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val tv_name : TextView = v.tv_name
        val tv_count : TextView = v.tv_count
        val iv_delete :ImageView = v.iv_delete
    }
}