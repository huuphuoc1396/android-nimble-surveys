package co.nimblehq.surveys.data.storages.providers

object NativeLibProvider {
    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun getSecretKey(id: Int): String

    val clientId: String
        get() = getSecretKey(1)

    val clientSecret: String
        get() = getSecretKey(2)

    val sqliteEncryptedKey: String
        get() = getSecretKey(3)
}
