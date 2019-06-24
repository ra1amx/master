package example.com.excerciseproject.presenter

import com.google.android.gms.maps.model.LatLng
import example.com.excerciseproject.model.Coordinate
import example.com.excerciseproject.model.MainInteractor
import example.com.excerciseproject.model.storage.Storage
import example.com.excerciseproject.view.order.ProcessOrderView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class ProcessOrderPresenter : BasePresenter<ProcessOrderView>() {

    private var interactor = MainInteractor()
    private var coordinate: LatLng? = null

    private var handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        view?.showProgress(false)
        throwable.printStackTrace()
    }

    override fun onAttach(view: ProcessOrderView) {
        super.onAttach(view)
        view.showButtonEnabled(false)
    }

    fun onClickMap(coordinate: LatLng) {
        this.coordinate = coordinate;
        view?.showCoordinate(coordinate)
    }

    fun onClickSend(name: String, phone: String, sendLocation: Boolean) {
        val works = Storage.workStorage.map.mapValues { entry -> entry.value.filter { it.checked } }
        val humanWorks = works.values.flatten().joinToString(",") { it.item.name }
        val coordinatesPayload = if (sendLocation && coordinate != null) "Координаты: ${coordinate?.latitude};${coordinate?.longitude}" else ""
        val payload = """
                Я хочу чтобы Вы сделали: $humanWorks
                Имя: $name
                Телефон: $phone
                $coordinatesPayload
            """.trimIndent()

        view?.showProgress(true)
        GlobalScope.launch(Dispatchers.Main + handler) {
            val coordinate = if (coordinate == null) null else Coordinate(coordinate!!.latitude, coordinate!!.longitude)
            val result = interactor.sendWebHook(
                name = name,
                title = humanWorks,
                phone = phone,
                comment = payload
            )
            view?.showProgress(false)
        }
        view?.openEmailApp(
            "Запрос на работу",
            payload,
            "eremin.i@bitrix24.ru"
        )
    }

    fun onChangePhone(phone: CharSequence) {
        view?.showButtonEnabled(phone.isNotBlank())
    }
}