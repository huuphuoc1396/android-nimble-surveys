package co.nimblehq.surveys.data.storages.providers

object NativeLibProvider {

    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun getApiKey(id: Int): String

    val clientId: String
        get() = getApiKey(1)

    val clientSecret: String
        get() = getApiKey(2)
}
