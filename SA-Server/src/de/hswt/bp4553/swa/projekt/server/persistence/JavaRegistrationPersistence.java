package de.hswt.bp4553.swa.projekt.server.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import com.google.common.base.Throwables;

import de.hswt.bp4553.swa.projekt.model.Registration;

/**
 * Implementiert das RegistrationPersistence interface mithilfe von Java Serialisierung.
 * @author bp4553
 *
 */
public class JavaRegistrationPersistence implements RegistrationPersistence {
    
    private static final Logger log = Logger.getLogger(JavaRegistrationPersistence.class.getName());
	
	private static final File REGISTRATIONS_FILE = new File("registrations.bin");

	private synchronized void writeAll(Collection<Registration> regs) throws IOException{
		try(ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(REGISTRATIONS_FILE))){
			fout.writeObject(regs);
		}
	}

	@Override
    @SuppressWarnings("unchecked")
	public synchronized Collection<Registration> getAll() throws IOException{
		if(!REGISTRATIONS_FILE.exists()){
			return new ArrayList<>();
		}
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(REGISTRATIONS_FILE))){
			return (Collection<Registration>) in.readObject();
		} catch (ClassNotFoundException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
    public synchronized void insert(Registration reg) throws IOException{
		Collection<Registration> regs = getAll();
		regs.add(reg);
		writeAll(regs);
		log.info("Registration added: "+reg);
	}
}
