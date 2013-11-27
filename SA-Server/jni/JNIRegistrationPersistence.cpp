#include "de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence.h"
#include <fstream>
#include <vector>
#include <sstream>

#define REG_FILE "c_registrations.bin"

jbyteArray serialize(JNIEnv * env, jobject obj){
    jclass objStreamClass = env->FindClass("java/io/ObjectOutputStream");
    jclass byteStreamClass = env->FindClass("java/io/ByteArrayOutputStream");
    jobject bytestream = env->NewObject(byteStreamClass, env->GetMethodID(byteStreamClass, "<init>", "()V"));
    jobject objStream = env->NewObject(objStreamClass, env->GetMethodID(objStreamClass, "<init>", "(Ljava/io/OutputStream;)V"), bytestream);
    env->CallVoidMethod(objStream, env->GetMethodID(objStreamClass, "writeObject", "(Ljava/lang/Object;)V"), obj);
    env->CallVoidMethod(objStream, env->GetMethodID(objStreamClass, "close", "()V"));
    return (jbyteArray) env->CallObjectMethod(bytestream, env->GetMethodID(byteStreamClass, "toByteArray", "()[B"));
}

jobject deserialize(JNIEnv * env, jbyteArray byteArray){
    jclass objStreamClass = env->FindClass("java/io/ObjectInputStream");
    jclass byteStreamClass = env->FindClass("java/io/ByteArrayInputStream");
    jobject bytestream = env->NewObject(byteStreamClass, env->GetMethodID(byteStreamClass, "<init>", "([B)V"), byteArray);
    jobject objStream = env->NewObject(objStreamClass, env->GetMethodID(objStreamClass, "<init>", "(Ljava/io/InputStream;)V"), bytestream);
    return env->CallObjectMethod(objStream, env->GetMethodID(objStreamClass, "readObject", "()Ljava/lang/Object;"));
}
void write(JNIEnv * env, jobject collection){
    std::ofstream file(REG_FILE, std::ios::binary | std::ios::out | std::ios::trunc);
    jbyteArray bytearray = serialize(env, collection);
    jbyte* bytes = env->GetByteArrayElements(bytearray, 0);
    file.write((const char*)bytes, env->GetArrayLength(bytearray));
    env->ReleaseByteArrayElements(bytearray, bytes, 0);
}

JNIEXPORT jobject JNICALL Java_de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence_getAll(JNIEnv * env, jobject thiz){
    std::ifstream file(REG_FILE, std::ios::binary);
    if(!file){
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        return env->NewObject(arrayListClass, env->GetMethodID(arrayListClass, "<init>", "()V"));
    }
    file.seekg(0, std::ifstream::end);
    int size = file.tellg();
    file.seekg(0, std::ifstream::beg);
    printf("%d\r\n", size);
    fflush(stdout);
    jbyteArray byteArray = env->NewByteArray(size);
    jbyte* bytes = env->GetByteArrayElements(byteArray, 0);
    file.read((char *)bytes, size);
    env->ReleaseByteArrayElements(byteArray, bytes, 0);
    return deserialize(env, byteArray);
}

JNIEXPORT void JNICALL Java_de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence_insert(JNIEnv * env, jobject thiz, jobject registration) {
    jobject collection = Java_de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence_getAll(env, thiz);
    jclass collectionClass = env->FindClass("java/util/Collection");
    env->CallBooleanMethod(collection, env->GetMethodID(collectionClass, "add", "(Ljava/lang/Object;)Z"), registration);
    write(env, collection);
}
