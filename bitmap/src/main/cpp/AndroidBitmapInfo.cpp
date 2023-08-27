//  Copyright 2023 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

#include <jni.h>
#include <android/bitmap.h>

#define jni_throw(env, type, message) env->ThrowNew(env->FindClass(type), message), nullptr

extern "C" JNIEXPORT jobject JNICALL
Java_xyz_tynn_hoppa_bitmap_AndroidBitmapInfo_of(
        JNIEnv *env,
        jclass cls,
        jobject bitmap) {
    if (!bitmap) return jni_throw(env, "java/lang/NullPointerException", "bitmap");
    AndroidBitmapInfo info;
    switch (AndroidBitmap_getInfo(env, bitmap, &info)) {
        case ANDROID_BITMAP_RESULT_SUCCESS:
            break;
        case ANDROID_BITMAP_RESULT_JNI_EXCEPTION:
            return nullptr;
        default:
            return jni_throw(env, "java/lang/IllegalStateException", "could not get info");
    }
    jlong width = info.width, height = info.height, stride = info.stride;
    jmethodID init = env->GetMethodID(cls, "<init>", "(IIII)V");
    return env->NewObject(cls, init, (jint) width, (jint) height, (jint) stride, info.format);
}
