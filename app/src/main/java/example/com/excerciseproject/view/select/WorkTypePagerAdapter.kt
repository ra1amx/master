package example.com.excerciseproject.view.select

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import example.com.excerciseproject.model.WorkType

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class WorkTypePagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return SelectFragment.makeInstance(WorkType.values()[position])
    }

    override fun getCount(): Int {
        return WorkType.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(WorkType.values()[position].title)
    }
}