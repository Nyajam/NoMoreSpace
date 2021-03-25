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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



//Panel se usa como carpeta
@Entity
public class Panel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@ManyToOne
	private User user;

	@OneToMany(mappedBy="panel", cascade = CascadeType.ALL)
	private List<File> file = new ArrayList<File>();
	
	//Relacion yo me tengo a mi mismo para el arbol de directorios
	@OneToMany(mappedBy="panel", cascade = CascadeType.ALL)
	private List<Panel> panel = new ArrayList<Panel>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Panel() {}
	
	public Panel(User user, String name) {//Usar este constructor para que nunca haya un panel sin usuario
		this.user = user;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<File> getFile() {
		return this.file;
	}
	
	public void setFile(List<File> file) {
		this.file = file;
	}
	
	public void addFile(File file) {
		getFile().add(file);
		file.setPanel(this);
	}
	
	public void removeFile(File file) {
		getFile().remove(file);
		file.setPanel(null);
	}

	public List<Panel> getPanel() {
		return panel;
	}

	public void setPanel(List<Panel> panel) {
		this.panel = panel;
	}

	@Override
	public String toString() {
		return "Panel [id=" + id + ", name=" + name + ", user=" + user.getId() + ", " + user.getUsername() + ", panel=" + panel + ", file=" + file + "]";
	}
	
	
}
