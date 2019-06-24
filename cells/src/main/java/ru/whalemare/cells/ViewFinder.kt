package ru.whalemare.cells

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes

/**
 * @since 2018
 * @author Anton Vlasov - whalemare
 */
class ViewFinder(private val root: View) {

    private val cachedViews = SparseArray<View>()

    /**
     * Use this method for found view on [ViewHolder]
     * It is return cached views if it exists and otherwise calling on root view [View.findViewById]
     */
    fun <V : View> find(@IdRes id: Int): V {
        val cached = cachedViews[id]
        if (cached != null) return cached as V

        val view = root.findViewById<V>(id)
        cachedViews.put(id, view)
        return view as V
    }

}