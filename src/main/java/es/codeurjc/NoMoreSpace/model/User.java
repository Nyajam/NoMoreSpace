package es.codeurjc.NoMoreSpace.model;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class User {
	
	//@Autowired
	//private PasswordEncoder passwordEncoder;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String username;
	private String mail;
	private String password;
	private boolean bloqueado; //0 No bloqueado 1 Si bloqueado
	private boolean admin; //0 usuario normal, 1 administrador
	
	@OneToOne(cascade = CascadeType.ALL)
	private Pool pool;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Panel> panel = new ArrayList<Panel>();


	protected User() {}
	
	public User(String username, String mail, String password) {
		
		this.username = username;
		this.mail = mail;
		setPassword(password);
		this.bloqueado = false;
		this.admin = false;
		this.panel.add(new Panel(this,"Raiz"));
		this.pool=new Pool(this);
	}
	
	public User(String username, String mail, String password, boolean bloqueado, boolean admin) {
		
		this.username = username;
		this.mail = mail;
		setPassword(password);
		this.bloqueado = bloqueado;
		this.admin = admin;
		this.panel.add(new Panel(this, "Raiz"));
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
		//return passwordEncoder.encode(this.password).toString();
	}

	public void setPassword(String password) {
		this.password = encrypt(password);
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
	
	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
	public List<Panel> getPanel() { //Devuelve una lñista de panels con los panesl del usuario
		return this.panel;
	}
	public void setPanel(List<Panel> panel) { //Asigna una lista de Panels al usuario
		this.panel = panel;
	}

	public void addPanel(Panel panel) { //Introduce un solo panel en la lista de panels del usuario
		getPanel().add(panel);
		panel.setUser(this);
	}
	
	public void removePanel(Panel panel) { //Borra de la lista de panels del usuario un panel específico
		getPanel().remove(panel);
		panel.setUser(null);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", mail=" + mail + ", password=" + getPassword() + ", username=" + username
				+ ", bloqueado=" + bloqueado + ", admin=" + admin + ", panel=" + panel + ", pool=" + pool + "]";
	}

	public boolean comparePasswd(String pas)
	{
		return this.getPassword().equals(encrypt(pas));
	}
	
	public String encrypt(String value)
	{
		return value;
		//return new String()+value.hashCode();
		//return passwordEncoder.encode(value).toString();
	}
}
