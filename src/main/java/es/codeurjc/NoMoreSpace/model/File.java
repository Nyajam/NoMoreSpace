package es.codeurjc.NoMoreSpace.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class File {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String filename;
	private boolean compartido;
	
	@ManyToMany
	private List<Block> blocks;
	@ManyToOne
	private Pool pool;
	@ManyToOne 
	private Panel panel;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public File() {}

	public File(String filename, boolean compartido) {
		this.filename = filename;
		this.compartido = compartido;
		this.blocks=new ArrayList();
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

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}
	
	public void addBlock(Block block) {
		getBlocks().add(block);
		block.addFile(this);
	}
	
	public void removeBlock(Block block) {
		getBlocks().remove(block);
		if (block.getFiles().contains(this)) {
			block.removeFile(this);
		}
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
	@Override
	public String toString() {
		return "File [id=" + id + ", filename=" + filename + ", compartido=" + compartido + ", blocks=" + blocks
				+ ", panel=" + panel + ", pool=" + pool + "]";
	}
	
}
