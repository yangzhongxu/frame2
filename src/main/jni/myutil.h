#include "jni.h"

#ifndef __myutil_h__
#define  __myutil_h__


namespace yzx {

    jstring get_apk_signature(JNIEnv *, jobject);

    jstring get_jstring(JNIEnv *, char *);

    const char *jstr_to_cstr(JNIEnv *, jstring);

    jclass get_jclass(JNIEnv *, const char *);

}


#endif