#include <jni.h>
#include <string>
#include "credentials.cpp"

extern "C" jstring
Java_co_nimblehq_surveys_data_storages_providers_NativeLibProvider_getSecretKey(
        JNIEnv *env,
        jclass,
        jint id
) {
    return env->NewStringUTF(getData(id).c_str());
}