package ru.whalemare.cells

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.whalemare.cells.cell.CellDelegate
import ru.whalemare.cells.ext.SimpleCallback
import ru.whalemare.cells.ext.cells


/**
 * @since 2018
 * @author Anton Vlasov - whalemare
 */
open class CellAdapter(
    private val cells: MutableList<CellDelegate<Any>> = mutableListOf(),
    protected val items: MutableList<Any> = mutableListOf()
) : RecyclerView.Adapter<ViewHolder>() {

    constructor(cell: CellDelegate<*>) : this(cells(cell))

    var isEnabledDiffUtil: Boolean = true

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        val viewType = cells.indexOfFirst { it.isViewType(item) }
        if (viewType < 0) {
            throw UnsupportedOperationException("You need pass delegate cell for class ${items[position].javaClass}" +
                    ", but exists only ${cells.map { it.typeToString() }}")
        }
        return viewType
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            cells[holder.viewType].unbind(holder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = cells[viewType]
        return cell.viewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        getDelegate(item).bind(holder, item)
    }

    protected open fun getDelegate(item: Any): CellDelegate<Any> {
        return cells.first { it.isViewType(item) }
    }

    open fun addItem(element: Any) {
        items.add(element)
        notifyItemInserted(items.size)
    }

    open fun addItems(list: List<Any>) {
        val rangeStart = items.size
        items.addAll(list)
        notifyItemRangeInserted(rangeStart, list.size)
    }

    open fun setItems(list: List<Any>) {
        if (isEnabledDiffUtil) {
            val diffUtils = DiffUtil.calculateDiff(SimpleCallback(items, list))
            diffUtils.dispatchUpdatesTo(this)
        }

        items.clear()
        items.addAll(list)

        if (!isEnabledDiffUtil) {
            notifyItemRangeChanged(0, items.size)
        }
    }

    open fun clear(list: List<Any>) {
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * Move item [from] position [to] position
     */
    open fun move(from: Int, to: Int) {
        val value = items.removeAt(from)
        items.add(to, value)
        notifyItemMoved(from, to)
    }

    open fun getItemByPosition(layoutPosition: Int): Any {
        return items[layoutPosition]
    }

    override fun getItemCount(): Int = items.size

    fun findIndexByItem(item: Any): Int {
        return items.indexOfFirst { it == item }
    }

    companion object {
        const val KEY_CELL_VIEWTYPE = "key_cell_viewtype"
    }
}