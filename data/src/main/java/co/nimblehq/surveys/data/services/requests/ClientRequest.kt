package co.nimblehq.surveys.data.services.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class ClientRequest(
    @property:Json(name = "client_id")
    var clientId: String = "6GbE8dhoz519l2N_F99StqoOs6Tcmm1rXgda4q__rIw",
    @property:Json(name = "client_secret")
    var clientSecret: String = "_ayfIm7BeUAhx2W1OUqi20fwO3uNxfo1QstyKlFCgHw"
)