package co.nimblehq.surveys.data.services.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class ClientRequest(
    @property:Json(name = "client_id")
    var clientId: String = getApiKey(1),
    @property:Json(name = "client_secret")
    var clientSecret: String = getApiKey(2)
) {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        @JvmStatic
        private external fun getApiKey(id: Int): String
    }
}