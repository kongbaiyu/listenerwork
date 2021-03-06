/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_qianyou_nat_Listener */

#ifndef _Included_com_qianyou_nat_Listener
#define _Included_com_qianyou_nat_Listener
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_init
  (JNIEnv *, jclass);

/*
 * Class:     com_qianyou_nat_Listener
 * Method:    send
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_send
  (JNIEnv *, jclass, jstring);
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    senddx
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_senddx
  (JNIEnv *env, jclass obj, jstring jdata, jstring jtitle);

/*
 * Class:     com_qianyou_nat_Listener
 * Method:    setEWM
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_setEWM
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_qianyou_nat_Listener
 * Method:    setID
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_setID
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_qianyou_nat_Listener
 * Method:    login
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_login
  (JNIEnv *, jclass, jstring, jstring);
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    login
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_checkdx
  (JNIEnv *, jclass, jstring, jstring);
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    sendJson
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_qianyou_nat_Listener_sendJson
  (JNIEnv *, jclass, jstring, jstring);
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    sendHtml
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_sendHtml
  (JNIEnv *, jclass, jstring);
  /*
 * Class:     com_qianyou_nat_Listener
 * Method:    sendYZHtml
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_sendYZHtml
  (JNIEnv *, jclass, jstring);
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    getIpAddress
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_getIpAddress
  (JNIEnv *, jclass, jstring);
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    destory
 * Signature: ()V;
 */
JNIEXPORT void JNICALL Java_com_qianyou_nat_Listener_destory
  (JNIEnv *, jclass);
  

#ifdef __cplusplus
}
#endif
#endif
