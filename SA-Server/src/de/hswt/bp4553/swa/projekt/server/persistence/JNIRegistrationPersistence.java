package de.hswt.bp4553.swa.projekt.server.persistence;

import java.io.IOException;
import java.util.Collection;

import de.hswt.bp4553.swa.projekt.model.Registration;

public class JNIRegistrationPersistence implements RegistrationPersistence{

    @Override
    public native Collection<Registration> getAll() throws IOException;

    @Override
    public native void insert(Registration reg) throws IOException;

}
