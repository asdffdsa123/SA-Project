package de.hswt.bp4553.swa.projekt.server.persistence;

import java.io.IOException;
import java.util.Collection;

import de.hswt.bp4553.swa.projekt.model.Registration;

public interface RegistrationPersistence {

    public abstract Collection<Registration> getAll() throws IOException;

    public abstract void insert(Registration reg) throws IOException;

}