package ru.whalemare.cells

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import ru.whalemare.cells.cell.CellDelegate

/**
 * @since 2018
 * @author Anton Vlasov - whalemare
 */
class CellView<T : Any> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun setCell(cell: CellDelegate<T>, data: T) {
        val holder = cell.viewHolder(this, 1)
        removeAllViews()
        addView(holder.itemView)
        cell.bind(holder, data)
    }

}