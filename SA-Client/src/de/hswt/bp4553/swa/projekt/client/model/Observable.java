package de.hswt.bp4553.swa.projekt.client.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Der beobachtbare teil des Observer patterns.
 * @author bp4553
 *
 * @param <T>
 */
public class Observable<T> {

	private Collection<Observer<T>> observers = new ArrayList<>();
	
	/**
	 * Informiert die Beobachter das sich am beobachteten Objekt etwas geändert hat.
	 * @param item
	 */
	public void fireChange(T item){
		for(Observer<T> obs : observers){
			obs.changed(item);
		}
	}

	/**
	 * Fügt einen Beobachter hinzu.
	 * @param ob
	 */
	public void add(Observer<T> ob) {
		observers.add(ob);
	}
	
}
