import javax.swing.JOptionPane;
import java.net.Socket;
import java.net.InetAddress;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReceiveNetwork{
private static Point UIpoint=new Point();
private BufferedInputStream bufferInput;
private BufferedOutputStream bufferOutput;
private static String fileName;
private Process command;
private BufferedReader bufferedReader;
private Socket receiver;
private FileOutputStream fileOutput;


public String getIPAddress(){
	String gateway;
	String IPAddress="";
	try{
command=Runtime.getRuntime().exec("ipconfig");
bufferedReader=new BufferedReader(new InputStreamReader(command.getInputStream()));
do{
	gateway=bufferedReader.readLine();
	if(gateway.contains("Default Gateway . . . . . . . . . :"))
	break;
}while(gateway!=null);

int ipindex=gateway.indexOf('1');
IPAddress=gateway.substring(ipindex,gateway.length());
}

catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());
                 return null;
}
    return IPAddress;
}
public void receiveFileName( )
{  try{
	receiver= new Socket(InetAddress.getLocalHost(),2211);
	bufferedReader=new BufferedReader(new InputStreamReader(receiver.getInputStream()));
    fileName=bufferedReader.readLine();
    bufferedReader.close();
    receiver.close();
 }
    catch(IOException io) { JOptionPane.showMessageDialog(null,io.getMessage()); }
}
synchronized public void receiveFile(){
	try{
    receiver= new Socket(InetAddress.getLocalHost(),3322);
    String username=System.getProperty("user.name");
    File  f=new File("C:/Users/"+username+"/Documents/File Allocation/");
    if(!f.exists())
    {
		f.mkdirs();
	}
    fileOutput=new FileOutputStream(f+"/"+fileName);
	bufferOutput=new BufferedOutputStream(fileOutput,8000);
	bufferInput=new BufferedInputStream(receiver.getInputStream(),8000);
	byte[] buffer=new byte[8000];
	int ch;
		while(-1!=(ch=bufferInput.read(buffer)))
		{
			if(ch!=-1)
		    bufferOutput.write(buffer,0,ch);
		    if(receiver.isClosed())
		    JOptionPane.showMessageDialog(null,"Connection Lost");
	}

	bufferInput.close();
	bufferOutput.close();
	fileOutput.close();
	receiver.close();
} catch(IOException io) { JOptionPane.showMessageDialog(null,io.getMessage()); }
}
public void openFileReceiver(){
JFrame UI=new JFrame();
UI.setUndecorated(true);


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
name.setBounds(55,0,120,32);
name.setForeground(Color.WHITE);
name.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
//TopBar Panel Ends Here
//Image add to design look
JLabel fs=new JLabel();
fs.setIcon(new ImageIcon(getClass().getResource("images/animate.png")));
fs.setBounds(200,70,680,300);
JLabel textreceiving=new JLabel("Please wait! Receiving...");
textreceiving.setBounds(440,470,200,30);
textreceiving.setFont(new Font("Comic",Font.BOLD,17));
textreceiving.setVisible(false);
//recieve button
JButton Recieve=new JButton("Receive");
Recieve.setBounds(450,420,140,37);
Recieve.setBackground(new Color(51,114,196));
Recieve.setForeground(Color.WHITE);
Recieve.setFocusable(false);
Recieve.setBorder(BorderFactory.createLineBorder(Color.black));
Recieve.setFont(new Font("Comic", Font.BOLD,20));
Recieve.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ae){
	 fs.setIcon(new ImageIcon(getClass().getResource("images/animate.gif")));
	 textreceiving.setVisible(true);
     receiveFileName();
     receiveFile();
     fs.setIcon(new ImageIcon(getClass().getResource("images/animate.png")));
     textreceiving.setText("Successfully received");
//code here
	} });
JPanel whitebg=new JPanel(null);
whitebg.setBounds(0,33,1000,567);
whitebg.setBackground(Color.white);
whitebg.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(51,114,196)));
  //adding options

  UI.add(Recieve);
UI.add(fs);
toppanel.add(name);
UI.add(textreceiving);
toppanel.add(toplogo);
toppanel.add(minimize);
toppanel.add(close);
UI.add(toppanel);
//UI setups
UI.add(whitebg);
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
}
