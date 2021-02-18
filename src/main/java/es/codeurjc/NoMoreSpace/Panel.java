package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Panel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long panel_id;
	
	
	
	@OneToMany(mappedBy="panel")
	private List<File> file;
	@OneToOne(mappedBy="panel")
	private User user;
}
