package es.codeurjc.NoMoreSpace;

public class FileTest
{
	private String name;
	private int id;
	private int size;
	
	public FileTest(String n, int i)
	{
		name=n;
		id=i;
		size=i*1000;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getSize()
	{
		char letra[]= {'K','M','G','T'};
		double tmp=size;
		int i=0;
		while(tmp>1024.0)
		{
			tmp=size/1024.0;
			i++;
		}
		return tmp+" "+letra[i]+"B";
	}
}
