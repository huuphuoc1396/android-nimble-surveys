#include <jni.h>
#include <string>
#include "credentials.cpp"

extern "C" jstring
Java_co_nimblehq_surveys_data_storages_providers_NativeLibProvider_getApiKey(
        JNIEnv *env,
        jclass,
        jint id
) {
    std::string app_secret;
    app_secret = getData(id);
    return env->NewStringUTF(app_secret.c_str());
}