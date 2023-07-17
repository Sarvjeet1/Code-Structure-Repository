package com.example.mvvmdemo.view.viewholder.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo.databinding.ItemTempBinding
import com.example.mvvmdemo.utils.listeners.RecyclerViewItemListener

/**
 * All the viewHolders are using this BaseViewHolder class as parent class
 *
 * @param binding
 * @param listener
 */
abstract class BaseViewHolder(binding: ItemTempBinding, private val listener: RecyclerViewItemListener) :
    RecyclerView.ViewHolder(binding.root) {
    protected val TAG: String = javaClass.simpleName

}