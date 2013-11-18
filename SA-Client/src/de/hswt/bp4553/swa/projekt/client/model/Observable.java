package de.hswt.bp4553.swa.projekt.client.model;

import java.util.ArrayList;
import java.util.Collection;

public class Observable<T> {

	private Collection<Observer<T>> observers = new ArrayList<>();
	
	public void fireChange(T item){
		for(Observer<T> obs : observers){
			obs.changed(item);
		}
	}

	public void add(Observer<T> ob) {
		observers.add(ob);
	}
	
}
