package de.hswt.bp4553.swa.projekt.client.model;

import java.util.ArrayList;
import java.util.Collection;

import de.hswt.bp4553.swa.projekt.model.Registration;

public class RegistrationModel {
	
	private Collection<Registration> registrations = new ArrayList<>();
	private Observable<Collection<Registration>> regObserver = new Observable<>();
	
	public void setRegistrations(Collection<Registration> registrations){
		this.registrations = registrations;
		regObserver.fireChange(registrations);
	}

	public Collection<Registration> getRegistrations() {
		return registrations;
	}
	
	public void addRegistrationObserver(Observer<Collection<Registration>> ob){
		regObserver.add(ob);
	}
}
