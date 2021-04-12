package es.codeurjc.NoMoreSpace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.NoMoreSpace.model.*;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class FileDependencies
{
	@Autowired
	private UserRepository repo;
	
	//Carga de un fichero
	public boolean uploadFile(User user, MultipartFile newFile, Panel panel)
	{
		File fil =new File(newFile.getOriginalFilename(),false);
		user.getPool().addFile(fil);
		panel.addFile(fil);
		repo.save(user);
		return true;
	}
	
	
}
