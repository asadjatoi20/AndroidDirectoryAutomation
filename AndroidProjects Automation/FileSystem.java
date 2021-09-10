import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*; 
import java.io.*;  
import java.nio.file.Files;
import java.nio.channels.FileChannel;
import java.nio.file.StandardCopyOption;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
import java.nio.file.*; 
import java.io.IOException; 
import java.awt.image.*;
import  javax.imageio.*;

class FileSystem{
	JFrame frame;
	JLabel pathLabel, findLabel, replaceLabel, imageLabel, fbLabel, appLabel; // labels
	JTextField path,  find, replace, image, fbId, appName; // fields
	JButton browse; // button that will search for files
	JButton start; // button to execute the program	
	
	File fileSelectedViaChooser;
	File fileDirectory;

	private String findWord, replaceWord;
	FileSystem(){
		frame = new JFrame("File System"); // title of the Frame passed
		frame.setSize(600,530);  ///Frame Size 600width and 550Height
		frame.setLayout(null); //No built-in layout being used
		frame.setLocationRelativeTo(null); //Frame must be at centre position
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On Closing the Frame program shall terminate

		pathLabel = new JLabel("Path");  //Creating a Label for Path
		pathLabel.setFont(new Font("Serif", Font.BOLD, 20)); // setting Font Style and Size for Label
		pathLabel.setBounds(80,50,50,30); // Giving Label a location in Frame
		frame.add(pathLabel); // adding Label in Frame

		path = new JTextField(); // initialized textField here
		path.setToolTipText("Add Path of the directory here");  // adding a tool tip when cursor is placed o field
		path.setFont(new Font("Serif", Font.PLAIN, 18)); // setting fonts
		path.setBounds(165,50,350,32); // adding position of field in the Frame
		frame.add(path); // adding Field in the frame

		findLabel = new JLabel("Find");  //Creating a Label for Find
		findLabel.setFont(new Font("Serif", Font.BOLD, 20)); // setting Font Style and Size for Label
		findLabel.setBounds(80,90,50,30); // Giving Label a location in Frame
		frame.add(findLabel); // adding Label in Frame


		find = new JTextField(); // initialized textField here
		find.setToolTipText("Word to find");  // adding a tool tip when cursor is placed o field
		find.setFont(new Font("Serif", Font.PLAIN, 18)); // setting fonts
		find.setBounds(165,90,350,32); // adding position of field in the Frame
		frame.add(find); // adding Field in the frame


		replaceLabel = new JLabel("Replace");  //Creating a Label for Replace
		replaceLabel.setFont(new Font("Serif", Font.BOLD, 20)); // setting Font Style and Size for Label
		replaceLabel.setBounds(80,130,150,30); // Giving Label a location in Frame
		frame.add(replaceLabel); // adding Label in Frame


		replace = new JTextField(); // initialized textField here
		replace.setToolTipText("Word to Replace");  // adding a tool tip when cursor is placed o field
		replace.setFont(new Font("Serif", Font.PLAIN, 18)); // setting fonts
		replace.setBounds(165,130,350,32); // adding position of field in the Frame
		frame.add(replace); // adding Field in the frame

		
		imageLabel = new JLabel("Image");  //Creating a Label for Image
		imageLabel.setFont(new Font("Serif", Font.BOLD, 20)); // setting Font Style and Size for Label
		imageLabel.setBounds(80,170,150,30); // Giving Label a location in Frame
		frame.add(imageLabel); // adding Label in Frame

		image = new JTextField(); // initialized textField here
		image.setToolTipText("File Name will be displayed here");  // adding a tool tip when cursor is placed o field
		image.setFont(new Font("Serif", Font.PLAIN, 12)); // setting fonts
		image.setBounds(165,170,260,32); // adding position of field in the Frame
		image.setEditable(false);
		frame.add(image); // adding Field in the frame

		browse = new JButton("Browse"); // button that will browse the image file to be copied
		browse.setBounds(435,170,80,32);
		browse.addActionListener(new ActionListener(){ // adding action when browse button is clicked
			public void actionPerformed(ActionEvent e) {
				// It Should Open FileChooser
				JFileChooser fc=new JFileChooser(); // object created    
    			FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()); // only image files to be displayed
    			//FileFilter
				//Attaching Filter to JFileChooser object
				fc.addChoosableFileFilter(imageFilter); //filtration of files added
				fc.setFileFilter(imageFilter); // filtration of files added

    			int i=fc.showOpenDialog(frame); //    Open Browse Directory in frmae
    			if(i==JFileChooser.APPROVE_OPTION){    // if a file is approved
			         fileSelectedViaChooser=fc.getSelectedFile();    //  the selected file added to File object f
			        String filepath=fileSelectedViaChooser.getPath();    // path of the Image File copied into String filepath
			        image.setText(filepath);
		     	}
		     	else{

		     	}

         	}
		});
		frame.add(browse);


		fbLabel = new JLabel("FB Id");  //Creating a Label for FB ID
		fbLabel.setFont(new Font("Serif", Font.BOLD, 20)); // setting Font Style and Size for Label
		fbLabel.setBounds(80,210,150,30); // Giving Label a location in Frame
		frame.add(fbLabel); // adding Label in Frame


		fbId = new JTextField(); // initialized textField here
		fbId.setToolTipText("FB Id to save in a File");  // adding a tool tip when cursor is placed on field
		fbId.setFont(new Font("Serif", Font.PLAIN, 18)); // setting fonts
		fbId.setBounds(165,210,350,32); // adding position of field in the Frame
		frame.add(fbId); // adding Field in the frame

		appLabel = new JLabel("App Name");  //Creating a Label for App Name
		appLabel.setFont(new Font("Serif", Font.BOLD, 20)); // setting Font Style and Size for Label
		appLabel.setBounds(75,250,150,30); // Giving Label a location in Frame
		frame.add(appLabel); // adding Label in Frame


		appName = new JTextField(); // initialized textField here
		appName.setToolTipText("App Name to save in a File");  // adding a tool tip when cursor is placed on field
		appName.setFont(new Font("Serif", Font.PLAIN, 18)); // setting fonts
		appName.setBounds(165,250,350,32); // adding position of field in the Frame
		frame.add(appName); // adding Field in the frame
		// Radio buttons are created to ease operations i-e which particular operations you want to perform
		JRadioButton op1=new JRadioButton("FindReplace");
		JRadioButton op2=new JRadioButton("ImageCopy");
		JRadioButton op3=new JRadioButton("FB ID");
		JRadioButton op4=new JRadioButton("AppName");   
		ButtonGroup bg = new ButtonGroup();
		op1.setBounds(80,290,100,32);
		op2.setBounds(160,290,100,32);
		op3.setBounds(260,290,100,32);
		op4.setBounds(325,290,100,32);
		bg.add(op1);
		bg.add(op2);
		bg.add(op3);
		bg.add(op4);
		frame.add(op1);
		frame.add(op2);
		frame.add(op3);
		frame.add(op4);

		
		start = new JButton("START");
		start.setBounds(450,290,80,32);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(path.getText()==null || path.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Enter Path First");
					frame.setVisible(false);
					new FileSystem();
				}
				fileDirectory = new File(path.getText()+""); //file path obtained from field
				/// Testing method here
				


				if(op1.isSelected()){ // Find and Replace
					if(find.getText()==null || find.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Fill the fields first");
					}
					else{
						findWord(fileDirectory);
						JOptionPane.showMessageDialog(null, "Replaced Successfully");
					}	

				}
				else if(op2.isSelected()){ // Copy Image
					if(image.getText()==null || path.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Fill the fields first");
					}
					else{
						//////////////////////////////////////////////////////////////////////////////////////////////////
						String filename = fileSelectedViaChooser.getName(); //name of the file that is to be copied obtained here
						File newFile = new File(filename); // new file object with same name created
						newFile = new File(fileDirectory.getPath()+"\\"+newFile.getPath()); // adding the destination address of file
		        		newFile.mkdir(); // making a new file where the contents should be copied
						try{ // try catch blocks because IO Files throw exceptions
							fileCopy(fileSelectedViaChooser, newFile); // fileCopy method passed source and destionation
						}catch(Exception exp){
							exp.printStackTrace();
						}
					}	
				}
				else if(op3.isSelected()){ //FB Id Group
					if(fbId.getText()==null || fbId.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Fill the fields first");
					}
					else{
						saveID(fbId.getText(), fileDirectory);
					}
				}
				else if(op4.isSelected()){ //App Name  Group
					if(appName.getText()==null || appName.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Fill the fields first");
					}
					else{
						saveAppName(appName.getText(), fileDirectory);
					}
				}

				else if(!(op1.isSelected() && op2.isSelected() && op3.isSelected() && op4.isSelected())){
					JOptionPane.showMessageDialog(null, "Please Choose an Action First");
				}
				


			   
			} // end of actionPerformed
		});
		frame.add(start);

		frame.setVisible(true); // making the Frame Visible

	}
	public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException { //method that resizes the Image using graphics 2D
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    return resizedImage;
	}
	public void fileCopy(File source, File destination)throws IOException{ // image copying method
				URL imageURL = source.toURL(); //source's URL obtained because it's a picture
				BufferedImage bi = ImageIO.read(imageURL); // image read from URL
				//ImageIO.write(bi, "png", destination); // image copied as it is


				//Resizing the Image; 
				BufferedImage img1 = resizeImage(bi, 72,72);
				BufferedImage img2 = resizeImage(bi, 48,48);
				BufferedImage img3 = resizeImage(bi, 96,96);
				BufferedImage img4 = resizeImage(bi, 144,144);
				BufferedImage img5 = resizeImage(bi, 192,192);


				//Copying resized files twice as name ic_launcher.png and ic_launcher_round.png 
				//Constatn Directories
				File PATH1 = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-hdpi\\ic_launcher.png");
				File PATH1a = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-hdpi\\ic_launcher_round.png");
				
				File PATH2 = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-mdpi\\ic_launcher.png");
				File PATH2a = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-mdpi\\ic_launcher_round.png");
				
				File PATH3 = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-xhdpi\\ic_launcher.png");
				File PATH3a = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-xhdpi\\ic_launcher_round.png");
				
				File PATH4 = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-xxhdpi\\ic_launcher.png");
				File PATH4a = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-xxhdpi\\ic_launcher_round.png");
				
				File PATH5 = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-xxxhdpi\\ic_launcher.png");
				File PATH5a = new File(path.getText()+"\\app\\src\\main\\res\\mipmap-xxxhdpi\\ic_launcher_round.png");
		


				ImageIO.write(img1, "png", PATH1);
				ImageIO.write(img2, "png", PATH2);
				ImageIO.write(img3, "png", PATH3);
				ImageIO.write(img4, "png", PATH4);
				ImageIO.write(img5, "png", PATH5);

				ImageIO.write(img1, "png", PATH1a);
				ImageIO.write(img2, "png", PATH2a);
				ImageIO.write(img3, "png", PATH3a);
				ImageIO.write(img4, "png", PATH4a);
				ImageIO.write(img5, "png", PATH5a);
        		JOptionPane.showMessageDialog(null,"Copied! Successfully"); // Message Printed

	}
	public void findWord(File pathToStart){
		// find word in every sub folders and subfiles.

		File[] filesInDirectory = pathToStart.listFiles(); // all sub files listed
		findWord = find.getText();
		replaceWord = replace.getText();
		driver();


	}
	public void driver(){ // drive all the files
		File dir = new File(path.getText());
        //fill here
        if(dir.exists() && dir.isDirectory()) 
        { 
            // array for files and sub-directories  
            // of directory pointed by maindir 
            File arr[] = dir.listFiles(); 
            
              
            // Calling recursive method 
            RecursivePrint(arr,0,0);  
       } 


        //showFiles(dir.listFiles());

	}
	public void saveID(String id, File destination){
		File f = new File(destination.getPath()+"\\app\\src\\main\\res\\values\\strings.xml");
		//geting older appName
		String oldName = ""; // will store older app name
		try{

			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line ="";
			while((line=br.readLine())!=null){
				if(line.contains("string")){
					String word[] = line.split(">");
					String word2[] = word[1].split("<");
					oldName = word2[0];
				}
			}
		}catch(Exception ee){
			ee.printStackTrace();
		} 
		
		modifyFile(f.getPath(), oldName, id);
		JOptionPane.showMessageDialog(null, "ID saved successfully !");
	}
	public void saveAppName(String name, File destination){
		File f = new File(destination.getPath()+"\\app\\src\\main\\res\\values\\strings.xml");
		//geting older appName
		String oldName = ""; // will store older app name
		try{

			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line ="";
			while((line=br.readLine())!=null){
				if(line.contains("string")){
					String word[] = line.split(">");
					String word2[] = word[1].split("<");
					oldName = word2[0];
				}
			}
		}catch(Exception ee){
			ee.printStackTrace();
		} 
		
		modifyFile(f.getPath(), oldName, name);
		JOptionPane.showMessageDialog(null, "App Name saved successfully !");
	}

    
    private void findAndReplace(String currentFile) {

        System.out.println("Current file: " + currentFile);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String content = "";
        try {
            reader = new BufferedReader(new FileReader(currentFile));

            String line = reader.readLine();

            while (line != null) {
                content += line + System.lineSeparator();
                line = reader.readLine();
            }


            String updatedContent = content.replaceAll(findWord, replaceWord);

            writer = new BufferedWriter(new FileWriter(currentFile));
            writer.write(updatedContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     

    public void RecursivePrint(File[] arr,int index,int level)  //arr file array
     { 	
     	// terminate condition 
     	if(index == arr.length) 
             return; 
           
         // tabs for internal levels 
         for (int i = 0; i < level; i++) 
             System.out.print("\t"); 
           
         // for files 
         if(arr[index].isFile()){ 
             
             
                     String fileNamee;
       
          
          fileNamee=arr[index].getAbsolutePath();
          
         
        
      if( fileNamee.contains(".png") == false && fileNamee.contains(".jpg") == false && fileNamee.contains(".jpeg") == false 
              && fileNamee.contains(".rawproto") == false&& fileNamee.contains(".apk") == false && fileNamee.contains(".pro") == false){
     modifyFile(fileNamee, findWord, replaceWord);
         
        System.out.println("\nDoing The Task");
    }
    else{
        //System.out.print("File Not Readaable might be img file or other  on-readable file");
    }
      

             

         
         }
           
         // for sub-directories 
         else if(arr[index].isDirectory() && !(arr[index].getName().equals(".git"))) 
         { 
         	

             // recursion for sub-directories 
             RecursivePrint(arr[index].listFiles(), 0, level + 1); 
         } 
            
         // recursion for main directory 
         RecursivePrint(arr,++index, level); 
    }
    public static void modifyFile(String filePath, String oldString, String newString){
        File fileToBeModified = new File(filePath);
         
        String oldContent = "";
         
        BufferedReader reader = null;
         
        FileWriter writer = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }
             
            //Replacing oldString with newString in the oldContent
             
            String newContent = oldContent.replaceAll(oldString, newString);
             
            //Rewriting the input text file with newContent
             
            writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

     


    private static String getExtension(String filename) { // method that gets extension from a file
        if (filename == null) {
            return null;
        }

  			String extension = ""; 
            int i = filename.lastIndexOf('.'); 
  			if (i > 0) { 
                extension = filename.substring(i + 1);
                return extension; 
            } 
           	return "";  
  
           
    }
	public static void main(String[] args){
		new FileSystem();
		//System.out.println(getExtension("MainActivity.txt"));
		
			
	}
}