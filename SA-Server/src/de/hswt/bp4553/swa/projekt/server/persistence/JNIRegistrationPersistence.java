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
	
	private static final String NATIVE_LIB_NAME = "libSA-Projekt.so";

	static{
		System.load(new File(NATIVE_LIB_NAME).getAbsolutePath());
	}
	
	//Die Datei in die gespeichert werden soll wird von Java nach cpp uebergeben,
	private final String filename;

    public JNIRegistrationPersistence(String filename) {
		super();
		this.filename = filename;
	}

	@Override
    public Collection<Registration> getAll() throws IOException{
    	return getAll(filename);
    }
    
    private native Collection<Registration> getAll(String fname) throws IOException;

    @Override
    public void insert(Registration reg) throws IOException{
    	insert(reg, filename);
    }
    
    private native void insert(Registration reg, String fname) throws IOException;

}
