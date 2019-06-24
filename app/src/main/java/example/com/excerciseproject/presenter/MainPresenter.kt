package example.com.excerciseproject.presenter

import example.com.excerciseproject.model.MainInteractor
import example.com.excerciseproject.model.WorkType
import example.com.excerciseproject.view.main.MainView

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class MainPresenter(private val interactor: MainInteractor) {

    private var view: MainView? = null

    fun attachView(view: MainView) {
        this.view = view
    }

    fun onCheckedType(workType: WorkType) {
        view?.showWork(workType.getWorks())
    }
}