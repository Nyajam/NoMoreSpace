package es.codeurjc.NoMoreSpace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.codeurjc.NoMoreSpace.model.Panel;
import es.codeurjc.NoMoreSpace.model.User;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class PanelDependencies
{
	@Autowired
	private UserRepository repo;
	
	//Retorna el panel del path especificado de un usuario <-- Cambiar a Panel
	public Panel getPanelByPath(User usuario, String path)
	{
		Panel workdir;
		workdir=usuario.getPanel().get(0);
		String[] dir;
		dir=path.split("/");
		for(int i=0;i<dir.length;i++)
		{
			for(int j=0;j<workdir.getPanel().size();j++)
			{
				//workdir.getName().equals(dir[i]);
				//Si el workdir es el panel
				if(workdir.getPanel().get(j).getName().equals(dir[i]))
				{
					//El encontrado pasa a ser el nuevo workdir y sale del bucle de recorrido del panel
					workdir=workdir.getPanel().get(j);
					break;
				}
			}
		}
		if(workdir.getName().equals(dir[dir.length-1]))
			return workdir;
		else
			return null;
	}
}
