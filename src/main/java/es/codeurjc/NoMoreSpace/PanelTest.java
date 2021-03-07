package es.codeurjc.NoMoreSpace;

import java.util.LinkedList;
import java.util.List;

public class PanelTest
{
	private List<PanelTest> panel;
	private String name;
	
	public PanelTest(String name)
	{
		this.name=name;
		panel=new LinkedList();
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<PanelTest> getPanels()
	{
		return panel;
	}
}
