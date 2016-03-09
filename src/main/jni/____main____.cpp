#include "myutil.h"
#include "____main____.h"

JNIEXPORT jstring Java_yzx_study_frame2_jnif_JNI_test(JNIEnv *env, jclass jc) {
    jstring jstr = yzx::get_jstring(env, "fuck");
    return jstr;
}
