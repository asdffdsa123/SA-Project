package de.hswt.bp4553.swa.projekt.client.model;

import java.util.ArrayList;
import java.util.Collection;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Das Model des Clienten.
 * @author bp4553
 *
 */
public class RegistrationModel {
	
	private Collection<Registration> registrations = new ArrayList<>();
	private Observable<Collection<Registration>> regObserver = new Observable<>();
	
	/**
	 * Setzt die aktuelle Menge der Registrierungen, und informiert alle Beobachter.
	 * @param registrations
	 */
	public void setRegistrations(Collection<Registration> registrations){
		this.registrations = registrations;
		regObserver.fireChange(registrations);
	}

	public Collection<Registration> getRegistrations() {
		return registrations;
	}
	
	/**
	 * Fügt einen Beobachter für die Registrierungen hinzu.
	 * @param ob
	 */
	public void addRegistrationObserver(Observer<Collection<Registration>> ob){
		regObserver.add(ob);
	}
}
