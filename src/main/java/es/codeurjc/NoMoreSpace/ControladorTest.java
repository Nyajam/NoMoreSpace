package es.codeurjc.NoMoreSpace;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorTest
{
	@GetMapping("/")
	public String root(Model model)
	{
		//model.addAttribute("name","Hola caracola");
		return "index";
	}
}
