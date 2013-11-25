#include "de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence.h"
#include <fstream>
#include <vector>
#include <sstream>

#define REG_FILE "c_registrations.bin"

std::vector<std::string> split(const std::string &s, char delim) {
    std::vector<std::string> elems;
    std::stringstream ss(s);
    std::string item;
    while (std::getline(ss, item, delim)) {
        elems.push_back(item);
    }
    return elems;
}

jstring callObjectGetter(JNIEnv * env, jobject thiz, const char* name){
    jclass clazz = env->FindClass("de.hswt.bp4553.swa.projekt.model.Registration");
    jmethodID meth = env->GetMethodID(clazz, name, "()java.lang.String;");
    jobject obj = env->CallObjectMethod(thiz, meth);
    jclass objClass = env->FindClass("java.lang.Object");
    jmethodID toStringMethod = env->GetMethodID(objClass, "toString", "()java.lang.String;");
    return static_cast<jstring>(env->CallObjectMethod(obj, toStringMethod));
}

std::vector<jobject> convertToCpp(JNIEnv * env, jobject collection){
    std::vector<jobject> result;
    jclass clazz = env->FindClass("java.util.Collection");
    jclass iteratorClazz = env->FindClass("java.util.Iterator");
    jmethodID iteratorMethod = env->GetMethodID(clazz, "iterator", "()java.util.Iterator;");
    jobject iterator = env->CallObjectMethod(collection, iteratorMethod);
    jmethodID nextMethod = env->GetMethodID(iteratorClazz, "next", "()java.lang.Object;");
    jmethodID hasNextMethod = env->GetMethodID(iteratorClazz, "hasNext", "()Z");
    while(env->CallBooleanMethod(iterator, hasNextMethod)){
        result.push_back(env->CallObjectMethod(iterator, nextMethod));
    }
    return result;
}

jobject convertToJava(JNIEnv * env, std::vector<jobject> &registrations){
    jclass collectionClass = env->FindClass("java.util.ArrayList");
    jmethodID insertMethod = env->GetMethodID(collectionClass, "add", "()Z");
    jmethodID constructor = env->GetMethodID(collectionClass, "<init>", "()V");
    jobject result = env->NewObject(collectionClass, constructor);
    for(std::vector<jobject>::iterator it = registrations.begin(); it != registrations.end(); ++it) {
        env->CallBooleanMethod(result, insertMethod, *it);
    }
    return result;
}

JNIEXPORT jobject JNICALL Java_de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence_getAll(JNIEnv * env, jobject thiz){
    std::vector<jobject> result;
    std::ifstream file;
    file.open(REG_FILE);
    jclass regClass = env->FindClass("de.hswt.bp4553.swa.projekt.model.Registration");
    jclass dateClazz = env->FindClass("java.util.Date");
    jclass fakultyClazz = env->FindClass("de.hswt.bp4553.swa.projekt.model.Fakulty");
    jclass genderClazz = env->FindClass("de.hswt.bp4553.swa.projekt.model.Gender");
    jmethodID constructor = env->GetMethodID(regClass, "<init>", "()V");
    jmethodID dateValueOf = env->GetStaticMethodID(dateClazz, "valueOf", "(java.lang.String;)java.util.Date;");
    jmethodID fakultyValueOf = env->GetStaticMethodID(fakultyClazz, "valueOf", "(java.lang.String;)de.hswt.bp4553.swa.projekt.model.Fakulty");
    jmethodID genderValueOf = env->GetStaticMethodID(genderClazz, "valueOf", "(java.lang.String;)de.hswt.bp4553.swa.projekt.model.Gender");
    for(std::string line; std::getline(file, line); ){
        std::vector<std::string> tokens = split(line, ',');
        jobject firstname = env->NewStringUTF(tokens[0].c_str());
        jobject lastname = env->NewStringUTF(tokens[1].c_str());
        jobject birthday = env->CallStaticObjectMethod(dateClazz, dateValueOf, env->NewStringUTF(tokens[2].c_str()));
        jobject fakulty = env->CallStaticObjectMethod(fakultyClazz, fakultyValueOf, env->NewStringUTF(tokens[3].c_str()));
        jobject gender = env->CallStaticObjectMethod(genderClazz, genderValueOf, env->NewStringUTF(tokens[4].c_str()));
        result.push_back(env->NewObject(regClass, constructor, firstname, lastname, birthday, fakulty, gender));
    }
    return convertToJava(env, result);
}

JNIEXPORT void JNICALL Java_de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence_insert(JNIEnv * env, jobject thiz, jobject registration) {
    jobject collection = Java_de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence_getAll(env, thiz);
    std::vector<jobject> registrations = convertToCpp(env, collection);
    registrations.push_back(registration);
    std::ofstream file;
    file.open(REG_FILE);
    for(std::vector<jobject>::iterator it = registrations.begin(); it != registrations.end(); ++it){
        jobject reg = *it;
        file << callObjectGetter(env, reg, "getFirstname") << ", ";
        file << callObjectGetter(env, reg, "getLastname") << ", ";
        file << callObjectGetter(env, reg, "getBirthday") << ", ";
        file << callObjectGetter(env, reg, "getFakulty") << ", ";
        file << callObjectGetter(env, reg, "getGender");
        file << std::endl;
    }
}
