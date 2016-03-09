#include "myutil.h"

JNIEXPORT jstring Java_yzx_study_frame2_jnif_JNI_test(JNIEnv *env, jclass jc) {
    jstring jstr = yzx::cstr_to_jstr(env, "fuck");
    return jstr;
}