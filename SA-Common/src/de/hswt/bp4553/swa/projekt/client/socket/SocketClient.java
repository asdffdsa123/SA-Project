package de.hswt.bp4553.swa.projekt.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Throwables;

import de.hswt.bp4553.swa.projekt.client.RegistrationRemoteClient;
import de.hswt.bp4553.swa.projekt.model.Registration;
import de.hswt.bp4553.swa.projekt.server.ServerConfig;

/**
 * Dieser Client implementiert das RegistrationRemoteClient interface mithilfe von Sockets.
 * @author bp4553
 *
 */
public class SocketClient implements RegistrationRemoteClient{
	
	private final ServerConfig config;
	
	public SocketClient(ServerConfig config){
		this.config = config;
	}
	
	public SocketClient(){
		this(ServerConfig.getInstance());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Registration> register(Registration reg) throws IOException{
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try{
			socket = openSocket();
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
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
		}finally{
			if(socket != null){
				socket.close();
			}
		}
	}

	@Override
    @SuppressWarnings("unchecked")
    public Collection<Registration> groupRegister(List<String> lines) throws IOException {
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try{
			socket = openSocket();
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
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
        }finally{
			if(socket != null){
				socket.close();
			}
		}
    }
	
	/**
	 * Fragt alle Registrierten Personen vom Server ab.
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Registration> getAll() throws IOException{
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try{
			socket = openSocket();
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
            out.writeObject("getAll");
            Collection<Registration> registrations = (Collection<Registration>) in.readObject();
            String resp = (String) in.readObject();
            if(!"Ok".equals(resp)){
                throw new RuntimeException("Server response: " + resp);
            }
            return registrations;
        }
        catch (ClassNotFoundException e) {
            throw Throwables.propagate(e);
        }finally{
			if(socket != null){
				socket.close();
			}
		}
	}
    
    private Socket openSocket() throws UnknownHostException, IOException{
        return new Socket(config.getHost(), config.getSocketPort());
    }

}
