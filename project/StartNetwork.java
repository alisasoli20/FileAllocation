import java.net.Socket;
import java.net.ServerSocket;
import javax.swing.JOptionPane;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;


    public class StartNetwork {
	private String filePath;
	private	String fileName;
    private JFrame UI;
    private static Point UIpoint=new Point();
    private FileInputStream fileInput;
	private BufferedInputStream bufferInput;
	private BufferedOutputStream bufferOutput;
	private Socket socket;
	private ServerSocket serversocket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private FileWriter filesend;
	private FileReader filereceive;
	private Process startCommand;
    synchronized public boolean checkWifi()
   {
	   try{
	String checker;
	startCommand=Runtime.getRuntime().exec("netsh wlan show drivers");
	bufferedReader=new BufferedReader(new InputStreamReader(startCommand.getInputStream()));
	do{
		checker=bufferedReader.readLine();
		if(checker.contains("Hosted network supported"))
		{
			break;
		}
	}while(!checker.equals(null));
    bufferedReader.close();
	if(checker.contains("Hosted network supported  : Yes"))
	{
		return true;
	}
	else {
	return false;}
}catch(Exception ex){ JOptionPane.showMessageDialog(null,ex.getMessage());
   return false;
}
}

synchronized public void setUpWifi(String ssid,String password)
{

		try{
			String command="netsh wlan set hostednetwork mode=allow ssid="+ssid+" key="+password;
			filesend=new FileWriter(System.getProperty("user.dir")+"/SetUpWifi.bat");
             filesend.write(command);
             filesend.close();
             startCommand=Runtime.getRuntime().exec("powershell.exe Start-Process "+System.getProperty("user.dir")+"/SetUpWifi.bat -verb RunAs");
             startCommand.waitFor();
             }
  catch(Exception io){   JOptionPane.showMessageDialog(null,io.getMessage()); }
}
synchronized public void startWifi()
{
		try{
			startCommand=Runtime.getRuntime().exec("powershell.exe Start-Process "+System.getProperty("user.dir")+"/StartWifi.bat -verb RunAs");
			startCommand.waitFor();
	  }
	  catch(Exception io)
	  {   JOptionPane.showMessageDialog(null,io.getMessage()); }

}

public void sendFileName(String fileName)
{    try{
	   serversocket=new ServerSocket(2211);
	   socket=serversocket.accept();
	   bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	   bufferedWriter.write(fileName);
	   bufferedWriter.close();
	   serversocket.close();
	   socket.close();
   }catch(IOException io){ JOptionPane.showMessageDialog(null,io.getMessage()); }
}

synchronized public void sendFile(){
	try{
	    serversocket=new ServerSocket(3322);
	   	   socket=serversocket.accept();
	      fileInput=new FileInputStream(filePath);
	      bufferInput=new BufferedInputStream(fileInput,8000);
	 	   bufferOutput=new BufferedOutputStream(socket.getOutputStream(),8000);
	 	        int ch;
	 	        byte[] buffer=new byte[8000];
	 	        while(-1!=(ch=bufferInput.read(buffer)))
	 	   	  {
	 	   	   if(ch!=-1)
	 	   	   bufferOutput.write(ch);
	 	   	   if(socket.isClosed())
	 	   	   JOptionPane.showMessageDialog(null,"Connection Lost");
	 	   	 }
            bufferInput.close();
            bufferOutput.close();
            fileInput.close();
            serversocket.close();
            socket.close();
 }  catch(IOException io){  JOptionPane.showMessageDialog(null,io.getMessage()); }
}

public void startFileChooser(){
	UI=new JFrame();
	UI.setUndecorated(true);
	//sidebar Ends Here
	//TopBar JPanel
	JPanel toppanel=new JPanel(null);
	toppanel.setBounds(0,0,1000,33);
	toppanel.setBackground(new Color(51,114,196));
	//TopPanel Buttons
	//close Button
	JButton close=new JButton();
	close.setBounds(960,0,32,32);
	close.setIcon(new ImageIcon(getClass().getResource("images/icons8_Cancel_32px.png")));
	close.setBackground(new Color(51,114,196));
	close.setBorder(BorderFactory.createEmptyBorder());
	close.setFocusable(false);
	//ActionOnCloseButton
	close.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
		try{
		Process p=Runtime.getRuntime().exec("netsh wlan stop hostednetwork");
		p.waitFor();
	}catch(Exception io){ JOptionPane.showMessageDialog(null,io.getMessage()); }
     System.exit(0);
	        }
	    });
	//minimize Button
	JButton minimize=new JButton();
	minimize.setBounds(925,0,32,32);
	minimize.setIcon(new ImageIcon(getClass().getResource("images/icons8_Minus_32px.png")));
	minimize.setBackground(new Color(51,114,196));
	minimize.setBorder(BorderFactory.createEmptyBorder());
	minimize.setFocusable(false);
	//MinimizeButtonActionListner
	minimize.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
	UI.setState(Frame.ICONIFIED);
	}
	});
	//Logo Top bar
	JLabel toplogo=new JLabel();
	toplogo.setBounds(0,0,120,32);
	toplogo.setIcon(new ImageIcon(getClass().getResource("images/logo.png")));
	JLabel name=new JLabel("File Allocation");
	name.setBounds(60,0,120,32);
	name.setForeground(Color.WHITE);
	name.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
	//TopBar Panel Ends Here
	//Bottom Send Button
	JButton Send=new JButton("Send File");
	Send.setBounds(410,300,150,40);
	Send.setBackground(new Color(51,114,196));
	Send.setForeground(Color.WHITE);
	Send.setFont(new Font("SERIF", Font.BOLD, 22));
	Send.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	Send.setFocusable(false);
	Send.setVisible(false);
	//do not show untill file is selected
	//FileSection
	JPanel filesection=new JPanel(null);
	filesection.setBounds(330,60,500,400);
	//label img icon

   JLabel selectfileicon=new JLabel();
	selectfileicon.setIcon(new ImageIcon(getClass().getResource("images/file.png")));
	selectfileicon.setBounds(100,80,100,100);
    selectfileicon.setVisible(false);

	JLabel folder=new JLabel(" No File Selected");
	folder.setBounds(100,170,300,50);



	//file selector
	JPanel filebutton=new JPanel(null);
	filebutton.setBounds(20,250,300,100);
	filebutton.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
	//button
	JButton selectit=new JButton("Select File");
	selectit.setBounds(100,20,100,50);
	selectit.setBackground(new Color(51,114,196));
	selectit.setForeground(Color.white);
    selectit.setBorder(BorderFactory.createEmptyBorder());
    selectit.setFocusable(false);
    selectit.setFont(new Font("Comic Sans MS",Font.BOLD,17));
    JPanel runpanel=new JPanel(null);
      runpanel.setBounds(300,490,430,80);
      runpanel.setBorder(BorderFactory.createLineBorder(Color.black));
    JLabel runit=new JLabel();
    runit.setBounds(450,500,300,50);
    runit.setIcon(new ImageIcon(getClass().getResource("images/run.gif")));
JPanel runtext=new JPanel(null);
runtext.setBounds(0,0,100,80);
runtext.setBackground(Color.black);
JLabel textrun=new JLabel("Sending...");
textrun.setBounds(10,1,89,79);
textrun.setForeground(Color.white);
textrun.setFont(new Font("Serif",Font.BOLD,17));
//set All visible false
textrun.setVisible(false);
runtext.setVisible(false);
runit.setVisible(false);
runpanel.setVisible(false);
	//open Jfile when button clicked
	selectit.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){

	        final JFileChooser fc = new JFileChooser();
	        int returnVal = fc.showOpenDialog(fc);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            filePath = file.getPath();
	            folder.setText(filePath);
	            folder.setBounds(0,170,600,50);
	            fileName=file.getName();
	            //now show the Send Button
	            Send.setVisible(true);
	            selectfileicon.setVisible(true);
                filebutton.setVisible(false);
                selectit.setVisible(false);


	        }
	}});

	//Send Walea Button Ka action Listener Yahan per hy. Yahan ap apna code dalana
	Send.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
		textrun.setVisible(true);
		runtext.setVisible(true);
		runit.setVisible(true);
        runpanel.setVisible(true);
		sendFileName(fileName);
		sendFile();
	}});

	//end here
	  //adding options

	  UI.add(Send);
	   UI.add(runit);
	  runtext.add(textrun);
	   runpanel.add(runtext);
	   UI.add(runpanel);
	   filebutton.add(selectit);
	    filesection.add(filebutton);
	     filesection.add(folder);
	   	 filesection.add(selectfileicon);
	UI.add(filesection);
	toppanel.add(name);
//	UI.add(sidebar);
	toppanel.add(toplogo);
	toppanel.add(minimize);
	toppanel.add(close);
	UI.add(toppanel);
	//UI setups
	UI.setSize(1000,600);
	UI.setLayout(null);
	UI.setVisible(true);
	//Centerized
	Dimension dashdim = Toolkit.getDefaultToolkit().getScreenSize();
	UI.setLocation(dashdim.width/2-UI.getSize().width/2, dashdim.height/2-UI.getSize().height/2);
	//code for mouse move
	 UI.addMouseListener(new MouseAdapter() {
	      public void mousePressed(MouseEvent e) {
	        UIpoint.x = e.getX();
	        UIpoint.y = e.getY();
	      }
	    });
	    UI.addMouseMotionListener(new MouseMotionAdapter() {
	      public void mouseDragged(MouseEvent e) {
	        Point p = UI.getLocation();
	        UI.setLocation(p.x + e.getX() - UIpoint.x, p.y + e.getY() - UIpoint.y);
	      }
    });
}

  /*  public static void main(String arg[]){
	StartNetwork s=new StartNetwork();

} */

}