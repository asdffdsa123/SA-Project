package de.hswt.bp4553.swa.projekt.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Das RMI interface, welches die Services des Servers offenlegt.
 * @author bp4553
 *
 */
public interface RegistrationService extends Remote{
	
	/**
	 * Registriert eine einzelne Person beim Server.
	 * @param reg
	 * @return
	 * @throws RemoteException
	 */
	public Collection<Registration> add(Registration reg) throws RemoteException;
	
	/**
	 * Registriert eine Guppe beim Server. Es wird eine CSV übergeben in der die Personen zeilenweise aufgelistet sind.
	 * @param lines Die Zeilen einer CSV Datei
	 * @return
	 * @throws RemoteException
	 */
	public Collection<Registration> addGroup(List<String> lines) throws RemoteException;

}
