package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long user_id;
	
	private String mail;
	private String password;
	private String username;
	private boolean bloqueado; //0 No bloqueado 1 Si bloqueado
	private boolean admin; //0 usuario normal, 1 administrador
	
	
	@OneToOne
	private Pool pool;
	@OneToMany
	private List<Panel> panel;
	
	protected User() {}
	
	public User(String mail, String password, String username) {
		this.mail = mail;
		this.password = password;
		this.username = username;
		this.bloqueado = false;
		this.admin = false;
	}
	
	public User(String mail, String password, String username, boolean bloqueado, boolean admin) {
		this.mail = mail;
		this.password = password;
		this.username = username;
		this.bloqueado = bloqueado;
		this.admin = admin;
	}
	
	
	
	public String getMail() {
		return mail;
	}

	public User(String mail, String password, String username, boolean bloqueado, boolean admin, Pool pool,
			List<Panel> panel) {
		super();
		this.mail = mail;
		this.password = password;
		this.username = username;
		this.bloqueado = bloqueado;
		this.admin = admin;
		this.pool = pool;
		this.panel = panel;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", mail=" + mail + ", password=" + password + ", username=" + username
				+ ", bloqueado=" + bloqueado + ", admin=" + admin + ", pool=" + pool + "]";
	}




}
