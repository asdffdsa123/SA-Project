package de.hswt.bp4553.swa.projekt.model;

import java.io.Serializable;
import java.util.Date;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

/**
 * Stellt die Registierung einer Person bei einem Wettkampf dar.
 * @author bp4553
 *
 */
public final class Registration 
	implements Serializable
{
	
	private static final long serialVersionUID = 2L;
	
	private String firstname;
	private String lastname;
	private Date birthday;
	private Fakulty fakulty;
	private Gender gender;

	public Registration(String firstname, String lastname, Date birthday,
			Fakulty fakulty, Gender gender) {
		super();
		setFirstname(firstname);
		setLastname(lastname);
		setBirthday(birthday);
		setFakulty(fakulty);
		setGender(gender);
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public Fakulty getFakulty() {
		return fakulty;
	}

	public Gender getGender() {
		return gender;
	}

	private void setFirstname(String vorname) {
		checkArgument(!isNullOrEmpty(vorname));
		this.firstname = vorname;
	}

	private void setLastname(String nachname) {
		checkArgument(!isNullOrEmpty(nachname));
		this.lastname = nachname;
	}

	private void setBirthday(Date geburtstag) {
		checkNotNull(geburtstag);
		this.birthday = geburtstag;
	}

	private void setFakulty(Fakulty fakulty) {
		checkNotNull(fakulty);
		this.fakulty = fakulty;
	}

	private void setGender(Gender gender) {
		checkNotNull(gender);
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Registration [vorname=" + firstname + ", nachname=" + lastname
				+ ", geburtstag=" + birthday + ", fakultaet=" + fakulty
				+ ", gender=" + gender + "]";
	}

}
