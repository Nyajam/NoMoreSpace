package es.codeurjc.NoMoreSpace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorBBDD implements CommandLineRunner {

	@Autowired
	private UserRepository repo;
	private PanelRepository rep;
	
	@Override
	public void run(String [] args) throws Exception{
		
		Panel p = new Panel("carpeta 1");
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
		repo.save(new User("juan@hotmail.com","123654","juanijuanitoo",false,false,null,p));
		repo.saveAndFlush(new User());
		
		
		List<User> pepipepito = repo.findByUsername("juanijuanitoo");
		
			System.out.println(pepipepito);
			
			
			List<Panel> asd = pepipepito.get(0).getPanel();
			System.out.println("recuperado");
			System.out.println(asd);
		
			
		/*final User u = new User();
		u.setPanel("carpeta");
		List<Panel> p = new ArrayList(u.getPanel());
		p.get(0).setFile(new File("archivo", false));
		System.out.println(p);*/
		
	}
}
