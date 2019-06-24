package example.com.excerciseproject.view.select

import example.com.excerciseproject.R
import example.com.excerciseproject.Work
import kotlinx.android.synthetic.main.cell_select.view.*
import ru.whalemare.cells.ViewHolder
import ru.whalemare.cells.cell.CellDelegate

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class CellSelect(
    private val onCheckedListener: (element: Selectable<Work>) -> Unit
): CellDelegate<Selectable<Work>>(R.layout.cell_select) {
    override fun isViewType(value: Any): Boolean {
        return (value as? Selectable<Work>) != null
    }

    override fun bind(holder: ViewHolder, item: Selectable<Work>) {
        holder.itemView.checkbox.apply {
            setOnCheckedChangeListener(null)
            text = item.item.name
            isChecked = item.checked
            setOnCheckedChangeListener { buttonView, isChecked ->
                onCheckedListener(item.copy(checked = isChecked))
            }
        }
    }
}