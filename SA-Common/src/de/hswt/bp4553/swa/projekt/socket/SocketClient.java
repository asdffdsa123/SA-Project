package de.hswt.bp4553.swa.projekt.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Throwables;

import de.hswt.bp4553.swa.projekt.model.Registration;

public class SocketClient {
	
	private final ServerConfig config;
	
	public SocketClient(ServerConfig config){
		this.config = config;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Registration> register(Registration reg) throws IOException{
		try(Socket socket = openSocket();
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
			out.writeObject("register");
			out.writeObject(reg);
			Collection<Registration> registrations = (Collection<Registration>) in.readObject();
			String resp = (String) in.readObject();
			if(!"Ok".equals(resp)){
				throw new RuntimeException("Server response: " + resp);
			}
			return registrations;
		}catch (ClassNotFoundException e1) {
			throw Throwables.propagate(e1);
		}
	}

    @SuppressWarnings("unchecked")
    public Collection<Registration> groupRegister(List<String> lines) throws IOException {
        try(Socket socket = openSocket();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
            out.writeObject("registerGroup");
            out.writeObject(lines);
            Collection<Registration> registrations = (Collection<Registration>) in.readObject();
            String resp = (String) in.readObject();
            if(!"Ok".equals(resp)){
                throw new RuntimeException("Server response: " + resp);
            }
            return registrations;
        }
        catch (ClassNotFoundException e) {
            throw Throwables.propagate(e);
        }
    }
    
    private Socket openSocket() throws UnknownHostException, IOException{
        return new Socket(config.getHost(), config.getSocketPort());
    }

}