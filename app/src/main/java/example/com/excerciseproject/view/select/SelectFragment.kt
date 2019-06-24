package example.com.excerciseproject.view.select

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import example.com.excerciseproject.R
import example.com.excerciseproject.Work
import example.com.excerciseproject.presenter.SelectPresenter
import example.com.excerciseproject.model.WorkType
import example.com.excerciseproject.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_selector.view.*
import ru.whalemare.cells.CellAdapter
import ru.whalemare.cells.ext.cells

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class SelectFragment : BaseFragment(), SelectView {

    override val layout: Int = R.layout.fragment_selector

    private val presenter = SelectPresenter()

    private val cellAdapter = CellAdapter(cells(
        CellSelect({ element ->
            presenter.onCheckedElement(element)
        })
    ))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            presenter.onAttach(this)
            presenter.onAttachWorkType(getWorkType(arguments!!))
        }

        cellAdapter.isEnabledDiffUtil = false
        view.recycler.layoutManager = LinearLayoutManager(view.context)
        view.recycler.adapter = cellAdapter
    }

    override fun showWorks(works: List<Selectable<Work>>) {
        cellAdapter.setItems(works)
    }

    companion object {
        private const val KEY_WORK_TYPE = "KEY_WORK_TYPE"

        fun makeInstance(workType: WorkType): SelectFragment {
            val fragment = SelectFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_WORK_TYPE, workType)
            fragment.arguments = bundle
            return fragment
        }

        fun getWorkType(bundle: Bundle): WorkType {
            return bundle.getSerializable(KEY_WORK_TYPE) as WorkType
        }
    }


}