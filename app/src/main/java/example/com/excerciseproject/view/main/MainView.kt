package example.com.excerciseproject.view.main

import example.com.excerciseproject.Work

/**
 * @since 2019
 * @author Anton Vlasov - whalemare with jun Ivan Eremin
 */
interface MainView {
    fun showWork(works: List<Work>)
}