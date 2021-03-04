package es.codeurjc.NoMoreSpace;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	private String mail;
	private String password;
	private String username;
	private boolean bloqueado; //0 No bloqueado 1 Si bloqueado
	private boolean admin; //0 usuario normal, 1 administrador
	
	
	@OneToOne
	private Pool pool;
	@OneToMany( fetch = FetchType.EAGER, mappedBy = "panel", cascade = CascadeType.ALL)
	private List<Panel> panel = new ArrayList<Panel>();
	
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
	
	public User(String mail, String password, String username, boolean bloqueado, boolean admin, Pool pool,
			Panel p) {
		super();
		this.mail = mail;
		this.password = password;
		this.username = username;
		this.bloqueado = bloqueado;
		this.admin = admin;
		this.pool = pool;
		this.panel.add(p);
	}
	
	
	public long getUserId() {
		return id;
	}

	public void setUseId(long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
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
	public List<Panel> getPanel() {
		return panel;
	}
	public void setPanel(String name) {
		Panel p = new Panel(name);
		this.panel.add(p);
	}

	@Override
	public String toString() {
		return "User [user_id=" + id + ", mail=" + mail + ", password=" + password + ", username=" + username
				+ ", bloqueado=" + bloqueado + ", admin=" + admin + ", panel=" + panel + ", pool=" + pool + "]";
	}




}
