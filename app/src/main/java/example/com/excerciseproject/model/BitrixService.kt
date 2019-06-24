package example.com.excerciseproject.model

import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
interface BitrixService {

    @POST("crm.lead.add")
    fun sendWebHook(@QueryMap fields: Map<String, String>): Deferred<WebHookResponse>

}