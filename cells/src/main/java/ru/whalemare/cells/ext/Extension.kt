package ru.whalemare.cells.ext

import ru.whalemare.cells.cell.CellDelegate

/**
 * @since 2018
 * @author Anton Vlasov - whalemare
 */

/**
 * Proxy for [mutableListOf] with casting
 */
fun <T : CellDelegate<*>> cells(vararg elements: T): MutableList<CellDelegate<Any>> {
    return elements.toMutableList() as MutableList<CellDelegate<Any>>
}