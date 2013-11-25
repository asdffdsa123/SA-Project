package de.hswt.bp4553.swa.projekt.client;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.hswt.bp4553.swa.projekt.model.Registration;

public interface RegistrationRemoteClient {
	
	Collection<Registration> register(Registration reg) throws IOException;
	
	Collection<Registration> groupRegister(List<String> lines) throws IOException;

}
