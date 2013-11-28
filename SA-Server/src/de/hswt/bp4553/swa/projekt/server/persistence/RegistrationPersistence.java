package de.hswt.bp4553.swa.projekt.server.persistence;

import java.io.IOException;
import java.util.Collection;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Dieses interface stellt ein Objekt dar das Registrierungen persistent Speichern, und wieder laden kann.
 * JavaRegistrationPersistence, und JNIREgistrationPersistence implementieren dieses interface.
 * @author bp4553
 *
 */
public interface RegistrationPersistence {

	/**
	 * Laed alle derzeit gespeicherten Registrierungen.
	 * @return
	 * @throws IOException
	 */
    public abstract Collection<Registration> getAll() throws IOException;

    /**
     * Speichert eine Registrierung persistent ab.
     * @param reg
     * @throws IOException
     */
    public abstract void insert(Registration reg) throws IOException;

}