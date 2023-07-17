package com.example.mvvmdemo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo.databinding.ItemTempBinding
import com.example.mvvmdemo.models.response.TempResponseModel
import com.example.mvvmdemo.utils.listeners.RecyclerViewItemListener
import com.example.mvvmdemo.view.viewholder.TempViewHolder
import com.example.mvvmdemo.view.view.activities.base.BaseActivity
import java.util.ArrayList

class TempAdapter(
    private val context: Context?,
    private val list: ArrayList<TempResponseModel>,
    private val listener: RecyclerViewItemListener
) : RecyclerView.Adapter<TempViewHolder>() {

    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder =
        TempViewHolder(ItemTempBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener = listener)

    override fun onBindViewHolder(holder: TempViewHolder, position: Int) =
        holder.bind(model = list[position])

    override fun getItemCount(): Int =
        list.size

}