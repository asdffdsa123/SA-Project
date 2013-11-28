package de.hswt.bp4553.swa.projekt.server.persistence;

import de.hswt.bp4553.swa.projekt.server.ServerConfig;

/**
 * Diese Faktory gibt auf Basis der Konfiguration ein Objekt zurück das RegistrationPersistence implementiert.
 * @author bp4553
 *
 */
public class RegistrationPersistenceFactory {
    private RegistrationPersistenceFactory(){}
    
    public static RegistrationPersistence getRegistrationPersistence(){
        switch(ServerConfig.getInstance().getRegistrationPersistenceType()){
        case Java:
            return new JavaRegistrationPersistence();
        case JNI:
            return new JNIRegistrationPersistence();
        default:
            throw new RuntimeException("Not Implemented");
        }
    }
}
