import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.io.*;


class mainscreen extends JFrame{
	JFrame screen;
	FileWriter fileWriter;
	FileReader fileReader;
	BufferedReader bufferReader;
	JTextField ssidpass;
	static ReceiveNetwork receiver;
	static StartNetwork startNetwork;
	private static Point UIpoint=new Point();
	public String getAppName()
	{
		return "File Allocation";
	}
	public String password(){
		try{
			fileReader=new FileReader(System.getProperty("user.dir")+"/Account1.dat");
			bufferReader=new BufferedReader(fileReader);
			String password=bufferReader.readLine();
			bufferReader.close();
			fileReader.close();
			return password;
		}catch(IOException io) { return "42678192"; }
	}
	public String ssid(){
		try{
        fileReader=new FileReader(System.getProperty("user.dir")+"/Account.dat");
        bufferReader=new BufferedReader(fileReader);
        String ssid=bufferReader.readLine();
        fileReader.close();
        bufferReader.close();
        return ssid; }
        catch(IOException io) {
			return getDefaultSSID();}

	}
	public String getDefaultSSID(){
		String defaultSSID=System.getProperty("user.name");
		return (defaultSSID.replaceAll("\\s",""));
	}
	mainscreen() {
	screen=new JFrame();
    screen.setUndecorated(true);
    initComponents();
    }
    public void initComponents(){
    JPanel topbar=new JPanel(null);
    topbar.setBounds(0,0,400,50);
    topbar.setBackground(new Color(51,114,196));
    JButton close=new JButton();
    close.setBounds(320,0,30,30);
	close.setIcon(new ImageIcon(getClass().getResource("images/icons8_Cancel_32px.png")));
   close.setBackground(new Color(51,114,196));
   close.setBorder(BorderFactory.createEmptyBorder());
    close.setFocusable(false);
    close.setToolTipText("Close");
    //ActionOnCloseButton
	close.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
		try{
	Process p=Runtime.getRuntime().exec("netsh wlan stop hostednetwork");
	p.waitFor();
	}
	catch(Exception ex) { JOptionPane.showMessageDialog(null,ex.getMessage()); }
	System.exit(0);
	        }
    });
    JButton minimize=new JButton();
	minimize.setBounds(288,0,30,30);
    minimize.setIcon(new ImageIcon(getClass().getResource("images/icons8_Minus_32px.png")));
	minimize.setBackground(new Color(51,114,196));
	minimize.setBorder(BorderFactory.createEmptyBorder());
    minimize.setFocusable(false);
    minimize.setToolTipText("Minimize");
    //MinimizeButtonActionListner
	minimize.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
	screen.setState(Frame.ICONIFIED);
	}
});

//label img icon
JLabel fileicon=new JLabel();
fileicon.setIcon(new ImageIcon(getClass().getResource("images/logo.png")));
fileicon.setBounds(0,0,60,30);
JLabel icontext=new JLabel(getAppName());
icontext.setBounds(60,0,150,30);
icontext.setForeground(Color.white);
icontext.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
JPanel extra=new JPanel(null);
extra.setBounds(0,30,400,100);
extra.setBackground(new Color(51,114,196));
JPanel white=new JPanel(null);
white.setBounds(0,2,400,1);
JLabel computer=new JLabel();
computer.setIcon(new ImageIcon(getClass().getResource("images/icons8-administrator-male-96.png")));
computer.setBounds(0,-13,100,125);

//SSID Name Label
JLabel pc=new JLabel("SSID:");
pc.setBounds(120,17,140,30);
pc.setForeground(Color.white);
pc.setFont(new Font("Garamond", Font.BOLD, 22));
pc.setToolTipText("SSID");
//SSID taken input from file
JLabel ssid=new JLabel(ssid());
ssid.setBounds(176,17,140,30);
ssid.setForeground(Color.green);
ssid.setFont(new Font("Garamond", Font.BOLD,22));
//Password Label
JLabel pwd=new JLabel("PWD:");
pwd.setBounds(120,43,140,30);
pwd.setForeground(Color.white);
pwd.setFont(new Font("Garamond", Font.BOLD, 22));
pwd.setToolTipText("Password");
//Password take from file
JLabel password=new JLabel(password());
password.setBounds(176,43,140,30);
password.setForeground(Color.green);
password.setFont(new Font("Garamond", Font.BOLD, 22));


JButton changessid=new JButton();
changessid.setIcon(new ImageIcon(getClass().getResource("images/icons8_Next_page_48px.png")));
changessid.setBounds(300,30,48,48);
changessid.setBackground(new Color(51,114,196));
changessid.setFocusable(false);
changessid.setBorder(BorderFactory.createEmptyBorder());
changessid.setToolTipText("Change SSID and password");

//new frame for ssid starts from here
JFrame ssidframe=new JFrame();
ssidframe.setSize(380,250);
ssidframe.setLayout(null);
ssidframe.setUndecorated(true);
//pannel for whole frame
JPanel full=new JPanel(null);
full.setBounds(0,0,380,250);
full.setBackground(Color.WHITE);
full.setBorder(BorderFactory.createLineBorder(new Color(51,114,196)));
//panel for header
JPanel headerfull=new JPanel();
headerfull.setBounds(0,0,380,30);
headerfull.setBackground(new Color(51,114,196));
JLabel textheaderfull=new JLabel("Change Your SSID");
textheaderfull.setForeground(Color.WHITE);
textheaderfull.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
//Label for ssid
JLabel userName=new JLabel("Username:");
userName.setBounds(35,45,100,100);
userName.setForeground(new Color(51,114,196));
userName.setFont(new Font("Comic Sans MS",Font.BOLD,18));
userName.setToolTipText("Username");

//Label for password
JLabel passwordLabel=new JLabel("Password:");
passwordLabel.setBounds(37,85,100,100);
passwordLabel.setForeground(new Color(51,114,196));
passwordLabel.setFont(new Font("Comic Sans MS",Font.BOLD,18));
passwordLabel.setToolTipText("Password");

//input for change ssid
JTextField ssidinput=new JTextField(15);
ssidinput.setBounds(150,70,160,40);
ssidinput.setFont(new Font("Serif", Font.BOLD,17));

//input for password Change
ssidpass=new JTextField(8);
ssidpass.setBounds(150,115,160,40);
ssidpass.setFont(new Font("Serif", Font.BOLD,17));

//Buttons
JButton change=new JButton("Change");
change.setBounds(298,210,79,30);
change.setBackground(new Color(51,114,196));
change.setForeground(Color.WHITE);
change.setFont(new Font("Serif", Font.BOLD,14));
change.setBorder(BorderFactory.createEmptyBorder());
change.setToolTipText("Change SSID and password");
change.setFocusable(false);
//actionListner for change button
	change.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
		try{
       String changessidws=ssidinput.getText();
       ssidinput.setText(null);
       String changessid=changessidws.replaceAll("\\s","");
       fileWriter=new FileWriter(System.getProperty("user.dir")+"/Account.dat");
       fileWriter.write(changessid);
       fileWriter.close();

       String changepassws=ssidpass.getText();
	   ssidpass.setText(null);
	   fileWriter=new FileWriter(System.getProperty("user.dir")+"/Account1.dat");
	   fileWriter.write(changepassws);
       fileWriter.close();
       }
       catch(Exception io) { JOptionPane.showMessageDialog(null,io.getMessage()); }
       ssid.setText(ssid());
       password.setText(password());
    ssidframe.setVisible(false);
}
});

//Creating and Managing Cancel button
JButton cancel=new JButton("Cancel");
cancel.setBounds(215,210,77,30);
cancel.setBackground(new Color(51,114,196));
cancel.setForeground(Color.WHITE);
cancel.setFont(new Font("Serif", Font.BOLD,14));
cancel.setBorder(BorderFactory.createEmptyBorder());
cancel.setToolTipText("Cancel");
cancel.setFocusable(false);

//actionListner for cancel button
	cancel.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
   ssidframe.setVisible(false);
	}
});


//adding options in frame ssid
full.add(ssidpass);
full.add(cancel);
full.add(change);
full.add(ssidinput);
full.add(userName);
full.add(passwordLabel);
headerfull.add(textheaderfull);
full.add(headerfull);
ssidframe.add(full);

//end of new frame

   //change ssidButtonActionListner
	changessid.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
    ssidframe.setVisible(true);
	}
});

JTextPane textArea = new JTextPane();
textArea.setContentType("text/html");
textArea.setText("<html><center><p style='font-size:16px;font-weight:bold;padding:0;margin:0;'>GuideLines</p><hr><u><font style='color:red;font-weight:bold;margin:0;padding:0;'>Please read it before use software</font></u></center> &nbsp; &nbsp; 1:Open <b>Network and Internet Sharing</b> settings<br> &nbsp; &nbsp; 2:click on <b>Sharing Options</b><br> &nbsp; &nbsp; 3:Turn <b>ON</b> network discovery for <b>Private, Guest and   &nbsp; &nbsp; &nbsp; &nbsp; All Networks</b><br> &nbsp; &nbsp; 4:When you select file and click on Send file button, it is  &nbsp; &nbsp;  &nbsp; necessary that on the other side WIFI must be connected<br> &nbsp; &nbsp; 5:After clicking <b>Send file</b> button you must also click  &nbsp; &nbsp;  &nbsp; &nbsp;  &nbsp; receive button to receive file <br><b> &nbsp; Note:</b>File sharing options must be <b>ON</b> and also <b>password  &nbsp; &nbsp; sharing</b>.</html>");
textArea.setBounds(0,130, 350,430);
textArea.setEditable(false);
textArea.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(51,114,196)));




//area.setBounds(0,130, 350,440);
//bottom

JPanel Bottom=new JPanel(null);
Bottom.setBounds(0,560,400,40);
Bottom.setBackground(new Color(51,114,196));




//Creating Send Button
JButton send=new JButton();
send.setBounds(10,5,135,30);
send.setBackground(new Color(51,114,196));
send.setIcon(new ImageIcon(getClass().getResource("images/sendali1.png")));
send.setBorder(BorderFactory.createEmptyBorder());
send.setToolTipText("Send Files");
//send.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
send.setFocusable(false);
send.setFont(new Font("Comic Sans MS", Font.BOLD, 17));

//send.setIcon(new ImageIcon(getClass().getResource("/images/if_send_326692.png")));

//Adding ActionListener for the Send Button
send.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e){
		startNetwork=new StartNetwork();
		if(startNetwork.checkWifi()){
           //startNetwork.setUpWifi(ssid(),password());
           //startNetwork.startWifi();
           startNetwork.startFileChooser();
		}
		else{
			JOptionPane.showMessageDialog(null,"Wifi drivers not found");
			System.exit(1);
			}

	}

});

//Creating Recieve Button
JButton recieve=new JButton("Recieve File");
recieve.setBounds(218,5,143,30);
recieve.setBackground(new Color(51,114,196));
recieve.setIcon(new ImageIcon(getClass().getResource("images/recieveali1.png")));
recieve.setBorder(BorderFactory.createEmptyBorder());
recieve.setToolTipText("Start receiving files");
recieve.setFocusable(false);
recieve.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
//Adding ActionListener for receive Button
recieve.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ae){
       receiver=new ReceiveNetwork();
       receiver.openFileReceiver();
	}
});


    //adding all objects to panels
    Bottom.add(recieve);
    Bottom.add(send);
    screen.add(textArea);
    screen.add(Bottom);
    extra.add(changessid);
    extra.add(pwd);
    extra.add(password);
    extra.add(ssid);
    extra.add(pc);
    extra.add(computer);
    extra.add(white);
    screen.add(extra);
    topbar.add(icontext);
    topbar.add(fileicon);
    topbar.add(minimize);
    topbar.add(close);
    screen.add(topbar);
	screen.setSize(350,600);
	screen.setLayout(null);
	screen.setVisible(true);
	//centerized it
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
screen.setLocation(dim.width/2-screen.getSize().width/2, dim.height/2-screen.getSize().height/2);
ssidframe.setLocation(dim.width/2-ssidframe.getSize().width/2, dim.height/2-ssidframe.getSize().height/2);
//code for mouse move
 screen.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        UIpoint.x = e.getX();
        UIpoint.y = e.getY();
      }
    });
    screen.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        Point p = screen.getLocation();
        screen.setLocation(p.x + e.getX() - UIpoint.x, p.y + e.getY() - UIpoint.y);
      }
    });


	}
public static void main(String args[]){
new mainscreen();

}
}