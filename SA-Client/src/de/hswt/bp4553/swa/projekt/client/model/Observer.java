package de.hswt.bp4553.swa.projekt.client.model;

/**
 * Der Beobachter des Observer patterns.
 * @author bp4553
 *
 * @param <T>
 */
public interface Observer<T> {

	/**
	 * Informiert den Beobachter darüber das sich das beobachtete Objekt geändert hat.
	 * @param item
	 */
	public void changed(T item);
	
}
