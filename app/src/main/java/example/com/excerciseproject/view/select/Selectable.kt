package example.com.excerciseproject.view.select

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class Selectable<Item>(
    val item: Item,
    val checked: Boolean
)