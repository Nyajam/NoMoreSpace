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
	
	//Crea un panel en otro especifico
	public boolean panelOnPanel(User user, String path, String name)
	{
		//getPanelByPath(user, path).getPanel().add(new Panel(user, name));
		Panel workdir = getPanelByPath(user, path);
		if(workdir==null)
			return false;
		for(int i=0;i<workdir.getPanel().size();i++)
			if(workdir.getPanel().get(i).getName().equals(name))
				return false;
		workdir.getPanel().add(new Panel(user, name));
		repo.save(user);
		return true;
	}
	
	//Borra un panel de panel especifico
	public int deletePanelOnPanel(User user,String path, String name)
	{
		Panel workdir;
		workdir=getPanelByPath(user, path);
		for(int i=0;i<workdir.getPanel().size();i++)
			if(workdir.getPanel().get(i).getName().equals(name))
			{
				if(workdir.getPanel().get(i).getPanel().isEmpty()&&workdir.getPanel().get(i).getFile().isEmpty())
				{
					workdir.removePanel(workdir.getPanel().get(i));
					return 0;
				}
				else
					return 1;
			}
		return -1;
	}
}
