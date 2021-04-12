package es.codeurjc.NoMoreSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import es.codeurjc.NoMoreSpace.repository.PanelRepository;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Controller
public class ControladorBBDD implements CommandLineRunner {

	@Autowired
	private UserRepository repo;
	
	@Override
	public void run(String [] args) throws Exception{
		
		//Para probar cualquier cosa aquí
		
		//Descomentar para introducir en la bbdd
		/*User user = new User("admin", "admin@alumnos.urjc.es","123456",false,true);

		repo.save(user);*/
		
		
		/*Panel p = new Panel("carpeta 2");
		
		Panel q = new Panel("carpeta 2");
		Panel r = new Panel("carpeta 3");
		System.out.println(p);
		System.out.println(q);
		System.out.println(r);
		List<Panel> pan = List.of(p,q,r);
		System.out.println(pan);
		

		
		repo.save(new User());
		repo.save(new User("pepito@hotmail.com","123456","pepipepito"));
		repo.save(new User("juan@hotmail.com","123654","juanijuanito",false,true));
		repo.save(new User("juan@hotmail.com","123654","juanijuanito",false,false,null,rep.save(new Panel("carpeta 1"))));
		
		rep.save(p);
		User usuario = new User("pepe@hotmail.com","123654","pepe",false,false,null,p);
		usuario.setPanel(p.getName());
		
		repo.save(usuario).getPanel();
		
		Optional<User> user = repo.findById((long) 5);
		
		List<Panel> carpeta = user.get().getPanel();
		System.out.println(user);
		System.out.println(carpeta);
		System.out.println(user.get().getUsername());
		
		List<User> pepipepito = repo.findByUsername("juanijuanito");
		
			System.out.println(pepipepito);
			
			
			List<Panel> asd = new ArrayList();
			asd = pepipepito.get(1).getPanel();
			System.out.println("recuperado");
			System.out.println(asd);
		
			
		final User u = new User();
		u.setPanel("carpeta");
		List<Panel> p = new ArrayList(u.getPanel());
		p.get(0).setFile(new File("archivo", false));
		System.out.println(p);
		*/
	}
}
