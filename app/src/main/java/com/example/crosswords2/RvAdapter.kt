package com.example.crosswords2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crosswords2.tables.HarfKutusuModel

class RvAdapter(
    private val harfList: ArrayList<HarfKutusuModel>,
    private val context: Context
) : RecyclerView.Adapter<RvAdapter.HarfViewHolder>() {
    var arrayofIds= ArrayList<Int>()
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RvAdapter.HarfViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent, false
        )
        return HarfViewHolder(itemView)
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener{
        fun onClick(position: Int, model: HarfKutusuModel,recItem:TextView)
    }

    override fun onBindViewHolder(holder: RvAdapter.HarfViewHolder, position: Int) {
        holder.harfTextView.id= View.generateViewId()
        arrayofIds.add(holder.harfTextView.id)

        if(harfList.get(position).harf=="X"){
            holder.harfTextView.visibility=View.INVISIBLE
        }else{

            holder.harfTextView.text = harfList.get(position).harf
            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, harfList.get(position),holder.harfTextView )
                }
            }
        }

    }

    fun getArrayOfIds():ArrayList<Int>{
        return arrayofIds
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our size of our list
        return harfList.size
    }

    class HarfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val harfTextView: TextView = itemView.findViewById(R.id.harfTw)
    }
}