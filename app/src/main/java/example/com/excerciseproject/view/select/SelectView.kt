package example.com.excerciseproject.view.select

import example.com.excerciseproject.Work

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
interface SelectView {
    fun showWorks(works: List<Selectable<Work>>)
}