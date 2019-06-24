package example.com.excerciseproject.model.storage

import example.com.excerciseproject.Work
import example.com.excerciseproject.model.WorkType
import example.com.excerciseproject.view.select.Selectable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class WorkStorage {
    var map = mutableMapOf<WorkType, List<Selectable<Work>>>()
}