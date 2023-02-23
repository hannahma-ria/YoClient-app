import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JToggleButton;


public class Main extends YoClient implements ItemListener{
	public static long Ysent = 0;
	public static long Yreceived = 0;
	public static long Hsent = 0;
	public static long Hreceived = 0;
	public static long count = 0;
	private JButton howButton;
	private JToggleButton toggle;
	private boolean btn = false;
	
	Main() {
		this.setJToggleButton();
		this.setAction();
	}
	public static void main(String[] args) throws IOException {
		Main GUI = new Main();
		GUI.initUI();
		GUI.connect();
		GUI.listen();
		
	}
	@Override
	public void updateStats(char recvCh) {
		// count how many times implemented
		count++;
		// to count Yo's
		if(recvCh == 'Y') {
			Ysent++;
		}
		if(recvCh == 'y') {
			Yreceived++;
		}
		// to count Howdy's
		if(recvCh == 'H') {
			Hsent++;
		}
		if(recvCh == 'h') {
			Hreceived++;
		}
		if (count % 5 == 0) {
			updateConsoleText("Yo sent: " + Ysent + ", Yo received: " + Yreceived);
			updateConsoleText("Howdy sent: " + Hsent + ", Howdy received: " + Hreceived);

		}
	}
	// adds a "send howdy" button and toggle button for "connect" and "disconnect"
	@Override
	public void initUI() throws IOException {
		super.initUI();
        howButton = new JButton("Send Howdy"); //add buttons to frame
        howButton.setEnabled(false);
        howButton.addActionListener(this);
        bPanel.add(howButton, BorderLayout.CENTER);
        bPanel.add(toggle, BorderLayout.EAST);
        
	}
	
	private void setAction() {
		toggle.addItemListener(this);
	}
	
	private void setJToggleButton() {
		toggle = new JToggleButton("Disconnect");
		toggle.addActionListener(this);
	}
	// changes the name of the button
	public void itemStateChanged(ItemEvent e) {
		if(toggle.isSelected()) {
		toggle.setText("Connect");
		btn= true;
		}
		else {
		toggle.setText("Disconnect");
		btn= false;
		}
	}
		
	// enables the howdy button and toggle button
	@Override
	public boolean connect() throws IOException {
		super.connect();
		howButton.setEnabled(true);
		toggle.setEnabled(true);
		return true;
	}
	// sends a howdy when button is clicked
	@Override
    public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        if (e.getSource() == this.howButton) {
            this.send(this.SEND_HOWDY);
        }
        if (e.getSource() == this.toggle) { 
        		// if trying to disconnect server
                if (btn== true) {
                		super.disconnect();
                		howButton.setEnabled(false);
               }
                // if trying to connect to server
                else if (btn== false){
                    try {
				connect();	
			   } catch (IOException e1) {
				e1.printStackTrace();
			   }  
                } 	       	
        }
    }	

}
