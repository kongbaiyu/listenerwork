
#include <jni.h>
#include "com_qianyou_nat_Listener.h"
#include "common.h"
#include <android/log.h>
#define  LOG_TAG    "main"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

JavaVM *jvm;
jclass gcls=NULL;

void log(string str)
{
	
	thread t([&]{
		do
		{
			int status;  
			JNIEnv *_env;  
			jboolean isAttached = JNI_FALSE;  
			status = jvm->GetEnv((void **) &_env, JNI_VERSION_1_6);	
			if(status < 0) {  
				status = jvm->AttachCurrentThread(&_env, NULL); 			
				if(status < 0) break;  
				isAttached = JNI_TRUE;  
			}  
			auto mid = _env->GetStaticMethodID(gcls,"log","(Ljava/lang/String;)V");
			_env->CallStaticVoidMethod(gcls,mid,_env->NewStringUTF(str.c_str()));
			if(isAttached) jvm->DetachCurrentThread(); 
		}while(0);
	});
	t.join();
}

/*
 * Class:     com_qianyou_nat_Listener
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_init
  (JNIEnv *env, jclass obj)
{
    LOGD("init");
	jclass cls = env->FindClass("com/qianyou/nat/Listener");
	gcls=(jclass)env->NewGlobalRef(cls);
	int ret=init([]{//onClose
		do
		{
			int status;  
			JNIEnv *_env;  
			jboolean isAttached = JNI_FALSE;  
			status = jvm->GetEnv((void **) &_env, JNI_VERSION_1_6);	
            LOGD("init status:%d",status);
			if(status < 0) {  
				status = jvm->AttachCurrentThread(&_env, NULL); 
                LOGD("init status:%d",status);                  
				if(status < 0) break;  
				isAttached = JNI_TRUE;  
			}  
			auto mid = _env->GetStaticMethodID(gcls,"onClose","()V");
			_env->CallStaticVoidMethod(gcls,mid);
			if(isAttached) jvm->DetachCurrentThread(); 
		}while(0);
	});
	LOGD("ret=%d",ret);
	return (jint)ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    send
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_send
  (JNIEnv *env, jclass obj, jstring jstr)
{
	jboolean isCopy=false;
	const char *str=env->GetStringUTFChars(jstr, &isCopy);
	jint ret=(jint)send(str);
	env->ReleaseStringUTFChars(jstr, str);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    senddx
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_senddx
  (JNIEnv *env, jclass obj, jstring jdata, jstring jtitle)
{
	jboolean isCopy=false;
	const char *data=env->GetStringUTFChars(jdata, &isCopy);
	isCopy=false;
	const char *title=env->GetStringUTFChars(jtitle, &isCopy);
	jint ret=(jint)senddx(data,title);
	env->ReleaseStringUTFChars(jdata, data);
    env->ReleaseStringUTFChars(jtitle, title);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    setEWM
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_setEWM
  (JNIEnv *env, jclass obj, jstring jimgpath)
{
	jboolean isCopy=false;
	const char *path=env->GetStringUTFChars(jimgpath, &isCopy);
	jint ret=(jint)setEWM(path);
	env->ReleaseStringUTFChars(jimgpath, path);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    setID
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_setID
  (JNIEnv *env, jclass obj,jstring jid)
{
	jboolean isCopy=false;
	const char *id=env->GetStringUTFChars(jid, &isCopy);
	jint ret=(jint)setID(id);
	env->ReleaseStringUTFChars(jid, id);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    login
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_login
  (JNIEnv *env, jclass obj, jstring jaccount, jstring jpassword)
{
	jboolean isCopy=false;
	const char *account=env->GetStringUTFChars(jaccount, &isCopy);
	isCopy=false;
	const char *password=env->GetStringUTFChars(jpassword, &isCopy);
	jint ret=(jint)login(account,password);
	env->ReleaseStringUTFChars(jaccount, account);
    env->ReleaseStringUTFChars(jpassword, password);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    checkdx
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_checkdx
  (JNIEnv *env, jclass obj, jstring jdata, jstring jtitle)
{
	jboolean isCopy=false;
	const char *data=env->GetStringUTFChars(jdata, &isCopy);
	isCopy=false;
	const char *title=env->GetStringUTFChars(jtitle, &isCopy);
	jint ret=(jint)checkdx(data,title);
	env->ReleaseStringUTFChars(jdata, data);
    env->ReleaseStringUTFChars(jtitle, title);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    getIpAddress
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_getIpAddress
(JNIEnv *env, jclass obj, jstring jaddress)
{
	jboolean isCopy = false;
	const char *address = env->GetStringUTFChars(jaddress, &isCopy);
	isCopy = false;
	jint ret = (jint)getIpAddress(address);
	env->ReleaseStringUTFChars(jaddress, address);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    sendJson
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_qianyou_nat_Listener_sendJson
  (JNIEnv *env, jclass obj, jstring jjson, jstring jract)
{
    
	jboolean isCopy=false;
	const char *json=env->GetStringUTFChars(jjson, &isCopy);
	isCopy=false;
	const char *ract=env->GetStringUTFChars(jract, &isCopy);
	string ret=sendJson(json,ract);
    
    
	env->ReleaseStringUTFChars(jjson, json);
    env->ReleaseStringUTFChars(jract, ract);
    
	return env->NewStringUTF(ret.c_str());
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    sendHtml
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_sendHtml
  (JNIEnv *env, jclass obj, jstring jstr)
{
	jboolean isCopy=false;
	const char *str=env->GetStringUTFChars(jstr, &isCopy);
	jint ret=(jint)sendhtml(str);
	env->ReleaseStringUTFChars(jstr, str);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    sendYZHtml
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_qianyou_nat_Listener_sendYZHtml
(JNIEnv *env, jclass obj, jstring jstr)
{
	jboolean isCopy = false;
	const char *str = env->GetStringUTFChars(jstr, &isCopy);
	jint ret = (jint)sendyzhtml(str);
	env->ReleaseStringUTFChars(jstr, str);
	return ret;
}
/*
 * Class:     com_qianyou_nat_Listener
 * Method:    destory
 * Signature: ();
 */
JNIEXPORT void JNICALL Java_com_qianyou_nat_Listener_destory
  (JNIEnv *env, jclass obj)
{
	destory();
}
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved){  
    JNIEnv *env;  
    jvm = vm;  
	LOGD("load jvm");
    if (jvm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) 
        return -1;  
//    if (registerNatives(env) != JNI_TRUE)
//        return -1;  
    return JNI_VERSION_1_6;  
}