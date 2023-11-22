#include <jni.h>
#include <string>

std::string getData(int x) {
    std::string app_secret;

    // The first key that you want to protect against decompile
    if (x == 1)
        // client_id
        app_secret = "6GbE8dhoz519l2N_F99StqoOs6Tcmm1rXgda4q__rIw";

    // The second key that you want to protect against decompile
    if (x == 2)
        // client_secret
        app_secret = "_ayfIm7BeUAhx2W1OUqi20fwO3uNxfo1QstyKlFCgHw";

    // The number of parameters to be protected can be increased.

    return app_secret;
}

extern "C" jstring
Java_co_nimblehq_surveys_data_services_providers_ApiServiceProvider_getApiKey(
        JNIEnv *env,
        jclass,
        jint id
) {
    std::string app_secret;
    app_secret = getData(id);
    return env->NewStringUTF(app_secret.c_str());
}