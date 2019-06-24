package ru.whalemare.cells.cell

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.whalemare.cells.ViewHolder
import java.lang.reflect.ParameterizedType

/**
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
abstract class CellDelegate<T : Any>(val layoutRes: Int) {

    /**
     * Called to determine the correspondences between the data-object and the cell
     */
    abstract fun isViewType(value: Any): Boolean

    abstract fun bind(holder: ViewHolder, item: T)

    /**
     * @param parent parent for ViewHolder
     * @param viewType that will be used for describe ViewHolder.
     * Optional and can be [RecyclerView.NO_POSITION] if do not needed.
     */
    fun viewHolder(parent: ViewGroup, viewType: Int = RecyclerView.NO_POSITION): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return makeViewHolder(itemView, viewType)
    }

    protected open fun makeViewHolder(view: View, viewType: Int): ViewHolder {
        return ViewHolder(view, viewType)
    }

    /**
     * This method will be called when [RecyclerView.Adapter.onViewDetachedFromWindow] called with position != [RecyclerView.NO_POSITION]
     * You can save this needed data or clear view
     */
    open fun unbind(holder: ViewHolder) {

    }

    fun typeToString(): String {
        var genericSuperClass = javaClass.genericSuperclass

        var parametrizedType: ParameterizedType? = null
        while (parametrizedType == null) {
            if (genericSuperClass is ParameterizedType) {
                parametrizedType = genericSuperClass
            } else {
                genericSuperClass = (genericSuperClass as Class<*>).genericSuperclass
            }
        }

        val name = parametrizedType.actualTypeArguments[0]
        return name.toString()
    }

}