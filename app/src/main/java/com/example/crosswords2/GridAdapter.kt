package com.example.crosswords2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.crosswords2.util.harfKutusuModel

class GridAdapter(context: Context, bulmacaHarfleriArrayList: ArrayList<harfKutusuModel>) :
    ArrayAdapter<harfKutusuModel>(context, 0, bulmacaHarfleriArrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        }

        val harfModel: harfKutusuModel? = getItem(position)
        val harfTextView = listitemView!!.findViewById<TextView>(R.id.harfTw)

        harfTextView.setText(harfModel?.getBirHarf())
        return listitemView
    }
}