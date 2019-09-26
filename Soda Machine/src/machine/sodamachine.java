package machine;
//Usually you will require both swing and awt packages
//even if you are working with just swings.
import javax.swing.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
public class sodamachine extends JFrame{
	static Logger logger;
	static JFrame frame;
public sodamachine(String name) {
super(name);
}
public boolean hasChange(String changestr) {
	 Double change = Double.valueOf(changestr.substring(changestr.indexOf("$") +1, changestr.indexOf(".") +3));
	 if(change != 0.00) {
		 return true;
	 }
	 return false;
}

public String adjustChange(String changestr, double adj) {
	 DecimalFormat df = new DecimalFormat("#.00");
	 Double change = Double.valueOf(changestr.substring(changestr.indexOf("$") +1, changestr.indexOf(".") +3));
	 change = change + adj;
	 return "Change: $"+df.format(change);
}

public void addComponentsToPane(final Container pane, Vending machine) {
    final boolean shouldFill = true;
    final boolean shouldWeightX = true;
    JButton button;
    JLabel changeLabel = new JLabel("Change: $0.00"); 
    JLabel errorLabel = new JLabel("Error: "); 
	pane.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	if (shouldFill) {
		//natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;
	}
	
	button = new JButton("Remove Quarter");
	if (shouldWeightX) {
		c.weightx = 0.5;
	}
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	pane.add(button, c);
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e)
	      {
			DecimalFormat df = new DecimalFormat("#.00");
			String changestr = changeLabel.getText();
//			System.out.println(changestr.substring(changestr.indexOf("$") +1, changestr.indexOf(".") +3));
			 if(hasChange(changestr)) {

				 c.gridx = 0;
				 c.gridy = 2;
				 c.gridwidth = 1;
				 c.ipady = 40;
				 c.fill = GridBagConstraints.CENTER;
				 changeLabel.setText(adjustChange(changestr, -.25));
				 pane.add(changeLabel, c);
				 logger.info("Remove Quarter: " + changeLabel.getText());
				 
				 c.gridx = 1;
				 c.gridy = 2;
				 c.gridwidth = 4;
				 c.ipady = 40;
				 c.fill = GridBagConstraints.CENTER;
				 errorLabel.setText("");
				 pane.add(errorLabel, c);
				 pane.revalidate();
				 pane.repaint();
			 } 
			 else {
				 c.gridx = 1;
				 c.gridy = 2;
				 c.gridwidth = 4;
				 c.ipady = 40;
				 c.fill = GridBagConstraints.CENTER;
				 errorLabel.setText("ERROR: Cannot remove more Quarters");
				 logger.info("Remove Quarter: ERROR: Cannot remove more Quarters" );
				 pane.add(errorLabel, c);
				 pane.revalidate();
				 pane.repaint();
			 }
	      }
	});
	
	button = new JButton("Add Quarter");
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 0;
	pane.add(button, c);
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e)
	      {

			String changestr = changeLabel.getText();

				 c.gridx = 0;
				 c.gridy = 2;
				 c.ipady = 40;
				 c.gridwidth = 1;
				 c.fill = GridBagConstraints.CENTER;
				 changeLabel.setText(adjustChange(changestr, .25) );				 
				 pane.add(changeLabel, c);
				 logger.info("Add Quarter: " + changeLabel.getText());
				 
				 c.gridx = 1;
				 c.gridy = 2;
				 c.ipady = 40;
				 c.gridwidth = 4;
				 c.fill = GridBagConstraints.CENTER;
				 errorLabel.setText("");
				 pane.add(errorLabel, c);
				 pane.revalidate();
				 pane.repaint();
	      }
	});
	
	int x = 0;
	for(String soda: machine.getsodas()) {
		button = new JButton(soda);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = x;
		c.gridy = 1;
		pane.add(button, c);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String changestr = changeLabel.getText();
				if(hasChange(changestr)) {
					if(machine.giveaway) {
						String text = machine.contest();
						logger.info(text);
					}else {
						int inv = machine.dispense(soda);
						if(inv != -1) {
							logger.info("Dispense " + soda + ". Remaining Sodas: " + inv);
							changeLabel.setText(adjustChange(changestr, -.25));
						}
						else {
	
							 c.gridx = 1;
							 c.gridy = 2;
							 c.ipady = 40;
							 c.gridwidth = 4;
							 c.fill = GridBagConstraints.CENTER;
							 errorLabel.setText("SOLD OUT");
						     logger.info("SOLD OUT");
							 pane.add(errorLabel, c);
							 pane.revalidate();
							 pane.repaint();
						}
					}
				}
				else {
					c.gridx = 1;
					 c.gridy = 2;
					 c.ipady = 40;
					 c.gridwidth = 4;
					 c.fill = GridBagConstraints.CENTER;
					 errorLabel.setText("Please Add Quarters");
					 logger.info("Cannot Dispense, Insufficient funds");
					 pane.add(errorLabel, c);
					 pane.revalidate();
					 pane.repaint();
				}
			}
		});
		x++;
	}
	

	//label = new JLabel("Change: $0.00"); 
	c.gridx = 0;
	c.gridy = 2;
	c.ipady = 40;
	c.fill = GridBagConstraints.CENTER;
	pane.add(changeLabel, c);
}
 

private static void createAndShowGUI(Vending machine) {
	//Create and set up the window.
	sodamachine sodaFrame = new sodamachine("Soda Machine");
	sodaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//Set up the content pane.
	sodaFrame.addComponentsToPane(sodaFrame.getContentPane(), machine);
	
	//Display the window.
	sodaFrame.pack();
	sodaFrame.setVisible(true);
}
 
public static void main(String[] args) throws Exception {
	try {

		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
		ex.printStackTrace();
		} catch (IllegalAccessException ex) {
		ex.printStackTrace();
		} catch (InstantiationException ex) {
		ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
		ex.printStackTrace();
		}
		String[] sodas = {"Pepsi","Coke","Dr Pepper"};
		Vending machine = new Vending(sodas, 10);
		machine.giveaway = false;
		boolean append = true;
	    FileHandler handler = new FileHandler("default.log", append);
	    logger = Logger.getLogger("Vending Machine Sales Log");
	    logger.addHandler(handler);
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				createAndShowGUI(machine);
			}
		});
	}
}