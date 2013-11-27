#include "de_hswt_bp4553_swa_projekt_server_persistence_JNIRegistrationPersistence.h"
#include <fstream>
#include <vector>
#include <sstream>

#define REG_FILE "c_registrations.csv"

std::vector<std::string> split(const std::string &s, char delim) {
    std::vector<std::string> elems;
    std::stringstream ss(s);
    std::string item;
    while (std::getline(ss, item, delim)) {
        elems.push_back(item);
    }
    return elems;
}

const char* getAsString(JNIEnv * env, jobject thiz, const char* name, const char* type){
    jclass clazz = env->FindClass("de/hswt/bp4553/swa/projekt/model/Registration");
    jmethodID meth = env->GetMethodID(clazz, name, type);
    jobject obj = env->CallObjectMethod(thiz, meth);
    jclass objClass = env->FindClass("java/lang/Object");
    jmethodID toStringMethod = env->GetMethodID(objClass, "toString", "()Ljava/lang/String;");
    jstring string = static_cast<jstring>(env->CallObjectMethod(obj, toStringMethod));     
    return env->GetStringUTFChars(string, 0);
}

std::vector<jobject> convertToCpp(JNIEnv * env, jobject collection){
    std::vector<jobject> result;
    jclass clazz = env->FindClass("java/util/Collection");
    jclass iteratorClazz = env->FindClass("java/util/Iterator");
    jmethodID iteratorMethod = env->GetMethodID(clazz, "iterator", "()Ljava/util/Iterator;");
    jobject iterator = env->CallObjectMethod(collection, iteratorMethod);
    jmethodID nextMethod = env->GetMethodID(iteratorClazz, "next", "()Ljava/lang/Object;");
    jmethodID hasNextMethod = env->GetMethodID(iteratorClazz, "hasNext", "()Z");
    while(env->CallBooleanMethod(iterator, hasNextMethod)){
        result.push_back(env->CallObjectMethod(iterator, nextMethod));
    }
    return result;
}

jobject convertToJava(JNIEnv * env, std::vector<jobject> &registrations){
    jclass collectionClass = env->FindClass("java/util/ArrayList");
    jmethodID insertMethod = env->GetMethodID(collectionClass, "add", "(Ljava/lang/Object;)Z");
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
    jclass regClass = env->FindClass("de/hswt/bp4553/swa/projekt/model/Registration");
    jclass dateClazz = env->FindClass("java/util/Date");
    jclass fakultyClazz = env->FindClass("de/hswt/bp4553/swa/projekt/model/Fakulty");
    jclass genderClazz = env->FindClass("de/hswt/bp4553/swa/projekt/model/Gender");
    jmethodID constructor = env->GetMethodID(regClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Lde/hswt/bp4553/swa/projekt/model/Fakulty;Lde/hswt/bp4553/swa/projekt/model/Gender;)V");
    jmethodID dateValueOf = env->GetStaticMethodID(dateClazz, "parse", "(Ljava/lang/String;)J");
    jmethodID dateConstructor = env->GetMethodID(dateClazz, "<init>", "()V");
    jmethodID fakultyValueOf = env->GetStaticMethodID(fakultyClazz, "valueOf", "(Ljava/lang/String;)Lde/hswt/bp4553/swa/projekt/model/Fakulty;");
    jmethodID genderValueOf = env->GetStaticMethodID(genderClazz, "valueOf", "(Ljava/lang/String;)Lde/hswt/bp4553/swa/projekt/model/Gender;");
    for(std::string line; std::getline(file, line); ){
        std::vector<std::string> tokens = split(line, ',');
        jobject firstname = env->NewStringUTF(tokens[0].c_str());
        jobject lastname = env->NewStringUTF(tokens[1].c_str());
        jobject birthday = env->NewObject(dateClazz, dateConstructor, env->CallStaticLongMethod(dateClazz, dateValueOf, env->NewStringUTF(tokens[2].c_str())));
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
        file << getAsString(env, reg, "getFirstname", "()Ljava/lang/String;") << ", ";
        file << getAsString(env, reg, "getLastname", "()Ljava/lang/String;") << ", ";
        file << getAsString(env, reg, "getBirthday", "()Ljava/util/Date;") << ", ";
        file << getAsString(env, reg, "getFakulty", "()Lde/hswt/bp4553/swa/projekt/model/Fakulty;") << ", ";
        file << getAsString(env, reg, "getGender", "()Lde/hswt/bp4553/swa/projekt/model/Gender;");
        file << std::endl;
    }
}
