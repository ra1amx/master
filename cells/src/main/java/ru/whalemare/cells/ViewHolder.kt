package ru.whalemare.cells

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class ViewHolder(
    override val containerView: View,
    val viewType: Int
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    @Deprecated("Use kotlin-extensions")
    val viewFinder = ViewFinder(itemView)
    val resources = itemView.resources
    val context = itemView.context

    /**
     * @see ViewFinder.find
     */
    @Deprecated("Use kotlin-extensions", ReplaceWith("containerView.id"))
    fun <V : View> find(@IdRes id: Int) = viewFinder.find<V>(id)

    /**
     * @param id of your [TextView]
     * @param text for setting to founded [TextView]
     */
    @Deprecated("Use kotlin-extensions", ReplaceWith("containerView.id"))
    fun setText(@IdRes id: Int, text: String) {
        find<TextView>(id).text = text
    }

    /**
     * @param id of your [TextView]
     * @param text for setting to founded [TextView]
     */
    @Deprecated("Use kotlin-extensions")
    fun setText(@IdRes id: Int, @StringRes textRes: Int) {
        find<TextView>(id).text = resources.getString(textRes)
    }

    /**
     * Set image to your [ImageView]
     * @param id of your [ImageView]
     * @param drawableRes id of your drawable
     */
    fun setImage(@IdRes id: Int, @DrawableRes drawableRes: Int) {
        find<ImageView>(id).setImageResource(drawableRes)
    }

    fun onClick(@IdRes id: Int, onClickListener: (() -> Unit)? = null) {
        if (onClickListener == null) {
            find<View>(id).setOnClickListener(null)
        } else {
            find<View>(id).setOnClickListener {
                onClickListener.invoke()
            }
        }
    }

    fun setEnabled(@IdRes id: Int, enabled: Boolean) {
        find<View>(id).isEnabled = enabled
    }
}