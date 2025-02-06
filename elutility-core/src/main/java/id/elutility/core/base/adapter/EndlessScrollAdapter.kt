package id.elutility.core.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import id.elutility.core.databinding.ItemLoadingBinding

abstract class EndlessScrollAdapter<T : Any, VB : ViewBinding>(private val loadMoreThreshold: Int = 5) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<T>()
    private var isLoading = false
    private var hasMoreData = true
    private var onLoadMoreListener: (() -> Unit) = {}

    abstract fun createBinding(parent: ViewGroup): VB
    abstract fun bindItem(binding: VB, item: T, position: Int)

    // DiffUtil
    abstract fun areItemsSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsSame(oldItem: T, newItem: T): Boolean

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> BaseAdapter.BaseViewHolder(createBinding(parent))
            VIEW_TYPE_LOADING -> LoadingViewHolder(createLoadingBinding(parent))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseAdapter.BaseViewHolder<*> -> {
                @Suppress("UNCHECKED_CAST")
                bindItem(holder.binding as VB, items[position], position)
            }
            is LoadingViewHolder -> {
                holder.binding.progressBar.isIndeterminate = true
            }
        }

        if (!isLoading && hasMoreData && position >= itemCount - loadMoreThreshold) {
            isLoading = true
            onLoadMoreListener.invoke()
        }
    }

    override fun getItemCount(): Int = items.size + if (shouldShowLoading()) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) VIEW_TYPE_ITEM else VIEW_TYPE_LOADING
    }

    private fun shouldShowLoading(): Boolean {
        return isLoading && hasMoreData
    }

    fun setOnLoadMoreListener(listener: () -> Unit) {
        onLoadMoreListener = listener
    }

    fun setLoading(loading: Boolean) {
        val previousLoading = isLoading
        isLoading = loading

        if (previousLoading != loading) {
            if (loading) {
                notifyItemInserted(itemCount - 1)
            } else {
                notifyItemRemoved(itemCount)
            }
        }
    }

    fun setHasMoreData(hasMore: Boolean) {
        hasMoreData = hasMore
        if (!hasMore) {
            setLoading(false)
        }
    }

    fun setItems(newItems: List<T>) {
        val diffUtil = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return items.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsSame(items[oldItemPosition], newItems[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsSame(items[oldItemPosition], newItems[newItemPosition])
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items.apply {
            clear()
            addAll(newItems)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItems(newItems: List<T>) {
        val startPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

    private fun createLoadingBinding(parent: ViewGroup): ItemLoadingBinding {
        return ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    class LoadingViewHolder(val binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root)
}