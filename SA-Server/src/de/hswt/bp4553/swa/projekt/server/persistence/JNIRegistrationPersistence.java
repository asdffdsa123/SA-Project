package de.hswt.bp4553.swa.projekt.server.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Deklariert die Methoden von RegistrationPersistence als native, und laedt die c++ shared library.
 * @author bp4553
 *
 */
public class JNIRegistrationPersistence implements RegistrationPersistence{
	
	static{
		System.load(new File("libSA-Projekt.so").getAbsolutePath());
	}

    @Override
    public native Collection<Registration> getAll() throws IOException;

    @Override
    public native void insert(Registration reg) throws IOException;

}
