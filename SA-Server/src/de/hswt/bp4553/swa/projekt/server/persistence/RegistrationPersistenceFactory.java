package de.hswt.bp4553.swa.projekt.server.persistence;

public class RegistrationPersistenceFactory {

    public enum RegistrationPersistenceType{
        Java, JNI
    }
    
    private static RegistrationPersistenceType type = RegistrationPersistenceType.Java;
    
    private RegistrationPersistenceFactory(){}
    
    public static RegistrationPersistence getRegistrationPersistence(){
        switch(type){
        case Java:
            return new JavaRegistrationPersistence();
        case JNI:
            return new JNIRegistrationPersistence();
        default:
            throw new RuntimeException("Not Implemented");
        }
    }
    
    public static void setRegistrationPersistenceType(RegistrationPersistenceType type){
        RegistrationPersistenceFactory.type = type;
    }
}
