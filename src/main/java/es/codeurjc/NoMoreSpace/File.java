package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class File {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long file_id;
	
	private String filename;
	private boolean compartido;
	
	@ManyToMany
	private List<Block> blocks;
	@ManyToOne
	private Panel panel;
	@ManyToOne
	private Pool pool;
	
	protected File() {}

	public File(String filename, boolean compartido) {
		super();
		this.filename = filename;
		this.compartido = compartido;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isCompartido() {
		return compartido;
	}

	public void setCompartido(boolean compartido) {
		this.compartido = compartido;
	}

	@Override
	public String toString() {
		return "File [file_id=" + file_id + ", filename=" + filename + ", compartido=" + compartido + "]";
	}
	
	
}
