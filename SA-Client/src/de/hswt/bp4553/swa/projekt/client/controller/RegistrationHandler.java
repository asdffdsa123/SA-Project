package de.hswt.bp4553.swa.projekt.client.controller;

import java.io.File;

import de.hswt.bp4553.swa.projekt.model.Registration;

public interface RegistrationHandler {
	
	void add(Registration reg);
	void groupAdd(File file);

}
