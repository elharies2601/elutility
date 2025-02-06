package id.elutility.core.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : Any, VB : ViewBinding>(
    private var items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {

    abstract fun createBinding(parent: ViewGroup): VB
    abstract fun bindItem(binding: VB, item: T, position: Int)

    // DiffUtil
    abstract fun areItemsSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsSame(oldItem: T, newItem: T): Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        return BaseViewHolder(createBinding(parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bindItem(holder.binding, items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newList: List<T>) {
        val diffUtil = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return items.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsSame(items[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsSame(items[oldItemPosition], newList[newItemPosition])
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items.apply {
            clear()
            addAll(newList)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItems(newItems: List<T>) {
        val oldSize = items.size
        items.addAll(newItems)
        notifyItemRangeChanged(oldSize, newItems.size)
    }

    fun getItem(position: Int): T = items[position]

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}