package com.example.crosswords2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crosswords2.util.HarfKutusuModel

class RvAdapter(
    private val harfList: ArrayList<HarfKutusuModel>,
    private val context: Context
) : RecyclerView.Adapter<RvAdapter.CourseViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RvAdapter.CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent, false
        )
        return CourseViewHolder(itemView)
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener{
        fun onClick(position: Int, model: HarfKutusuModel)
    }

    override fun onBindViewHolder(holder: RvAdapter.CourseViewHolder, position: Int) {
        holder.harfTextView.text = harfList.get(position).harf
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, harfList.get(position) )
            }
        }
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our size of our list
        return harfList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val harfTextView: TextView = itemView.findViewById(R.id.harfTw)
    }
}