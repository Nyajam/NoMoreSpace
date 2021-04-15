package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.InFiles;

@SpringBootApplication
public class ServicioInternoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServicioInternoApplication.class, args);
		getFiles();
	}

	public static void getFiles() throws IOException{
		System.out.println("Empezar");
		String serverFolder = "C:\\Users\\j.lamparero\\git\\NMSFiles";
		ServerSocket server = new ServerSocket(442);
		while (true) {
			Socket socket = server.accept();
			
			try {
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				ObjectOutputStream oos = new ObjectOutputStream(out);
				
				System.out.println("Antes de leer boolean");
				int modo = ois.readInt();//definimos protocolo, si recibe true guarda archivo si recibe false lo envía
				System.out.println("True recibido");
				int id = ois.readInt();
				System.out.println("Id recibido");
				if(modo==1) {//Guardado de archivo
					System.out.println("File recibido");
					Path path = Paths.get(serverFolder, ""+id);
					Files.write(path, in.readAllBytes());
					System.out.println("File guardado");
				}else if(modo==2){//Descarga de archivo
					Path path = Paths.get(serverFolder, ""+id);
					Files.copy(path, oos);
				}else {//Borrado de archivo
					Path path = Paths.get(serverFolder, ""+id);
					Files.delete(path);
				}
				ois.close();
				out.close();
				in.close();
				socket.close();
			}catch (IOException e){
				System.out.println("Fallo en la conexion.");
			}
			server.close();
		}
	}

}
