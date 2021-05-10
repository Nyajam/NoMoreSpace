package es.codeurjc.NoMoreSpace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Propiedades
{
	@Value("${serviciointerno}")
	private String serviciointerno;
	
	public String getServiciointerno()
	{
		return this.serviciointerno;
	}
	
	public void setServiciointerno(String input)
	{
		this.serviciointerno=input;
	}
}
