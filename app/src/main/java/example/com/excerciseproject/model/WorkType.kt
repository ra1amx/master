package example.com.excerciseproject.model

import example.com.excerciseproject.R
import example.com.excerciseproject.Work

/**
 * @since 2019
 * @author Anton Vlasov - whalemare with jun Ivan Eremin
 */
enum class WorkType(val title: Int) {
    REPAIR(title = R.string.repair) {
        override fun getWorks(): List<Work> {
            return listOf(
                Work("Сломалась сантехника"),
                Work("Не показывает ТВ"),
                Work("Сломалась мебель"),
                Work("Нет электричества"),
                Work("Надо покрасить(забор/вернаду и т.д.)")
            )
        }
    },
    SERVICE(title = R.string.service) {
        override fun getWorks(): List<Work> {
            return listOf(
                Work("Постричь газон"),
                Work("Вскопать участок"),
                Work("Вывезти мусор"),
                Work("Почистить канализацию"),
                Work("Настроить антенну/подключить ТВ")
            )
        }
    },
    DELIVERY(title = R.string.item) {
        override fun getWorks(): List<Work> {
            return listOf(
                Work("Доставить воду"),
                Work("Привести порошок")
            )
        }
    };

    abstract fun getWorks(): List<Work>
}