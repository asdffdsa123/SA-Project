package de.hswt.bp4553.swa.projekt.client.controller;

import java.io.File;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Interface für den RegistrationController.
 * Die View ruft auf diesem interface Methoden auf, und ist somit vom Controller entkoppelt.
 * @author bp4553
 *
 */
public interface RegistrationHandler {
	
	/**
	 * Wird aufgerufen, wenn der Hinzufuegen Button gedrückt wurde.
	 * @param reg Die eingegebene Registrierung
	 */
	void add(Registration reg);
	
	/**
	 * Wird aufgerufen, wenn der Gruppe Hinzufuegen Button gedrückt wurde, und eine Datei ausgewählt wurde.
	 * @param file Die ausgewählte Datei
	 */
	void groupAdd(File file);

}
