package uiutil;

import javax.swing.JFrame;
import javax.swing.JLabel;
import org.junit.Assert;
public class UIutil extends JFrame {
	@org.junit.Test
	public void testUI() {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	UIutil uiutil = new UIutil();
        		uiutil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		uiutil.pack();
        		uiutil.setVisible(true);
            }
        });
		
	}
	
	 public static void main(String[] args) {
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	UIutil uiutil = new UIutil();
	        		uiutil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        		uiutil.pack();
	        		uiutil.setVisible(true);
	            }
	        });
	 }
}
