package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//Panel se usa como carpeta
@Entity
public class Panel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long panel_id;
	
	private String name;
	
	
	
	
	//Relacion yo me tengo a mi mismo para el arbol de directorios
	@OneToMany(mappedBy="panel")
	private List<Panel> panel;
	
	
	@OneToMany
	private List<File> file;
	@ManyToOne
	private User user;
	
	protected Panel() {}
	
	public Panel(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Panel [panel_id=" + panel_id + ", nombre=" + name + "]";
	}
	
	
}
