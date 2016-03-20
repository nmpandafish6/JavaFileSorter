import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Master {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int n = JOptionPane.showConfirmDialog(
	            null,
	            "Would you like to do a deep sort?",
	            "An Inane Question",
	            JOptionPane.YES_NO_OPTION);
		boolean deepSort = n == JOptionPane.YES_OPTION;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		File directory;
		ArrayList<String> destinations = new ArrayList<>();
		if(returnVal == JFileChooser.APPROVE_OPTION){
			directory = fc.getSelectedFile();
			sortDirectoryBySuffix(directory, directory, destinations, deepSort);
			cleanEmptyDirectories(directory);
		}
	}
	
	public static void sortDirectoryBySuffix(File directory, File root, ArrayList<String> destinations, boolean deep) throws IOException{
		File[] files = directory.listFiles();
		System.out.println(directory);
		for(int i = 0; i < files.length; i++){
			String name;
			name = files[i].getName();
			String suffix = name.substring(1+name.lastIndexOf('.'));
			if(suffix.equals(name)){
				suffix = "No Extension";
			}
			File tempDestination = new File(root.getAbsolutePath() + "\\" + suffix);
			if(destinations.indexOf(suffix) == -1){
				destinations.add(suffix);
				tempDestination.mkdir();
			}
			Path source = files[i].toPath();
			Path destination = new File(tempDestination.getAbsoluteFile() + "\\" + name).toPath();
			if(!files[i].isDirectory()){
				Files.move(source,
						destination,
						StandardCopyOption.REPLACE_EXISTING);
			}else if(deep){
				sortDirectoryBySuffix(files[i], root, destinations, deep);
			}
		}
	}

	public static void cleanEmptyDirectories(File directory){
		if(directory.isDirectory()){
			File[] directories = directory.listFiles();
			for(int i = 0; i < directories.length; i++){
				if(directories[i].isDirectory()){
					if(!directories[i].delete()){
						cleanEmptyDirectories(directories[i]);
					}
					i--;
				}
			}
		}
	}
}
