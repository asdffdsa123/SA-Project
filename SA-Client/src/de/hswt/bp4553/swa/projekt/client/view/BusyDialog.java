package de.hswt.bp4553.swa.projekt.client.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class BusyDialog extends JDialog{
    
    private static final URL LOADING_ANIMATION = BusyDialog.class.getResource("loading_circle_gray.gif");

    private static final long serialVersionUID = 1L;
    
    public BusyDialog(Frame owner) {
        super(owner);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(getImage(), BorderLayout.WEST);
        this.getContentPane().add(new JLabel("Bitte warten..."), BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);    
    }

    private JComponent getImage(){
        JLabel image = new JLabel(new ImageIcon(LOADING_ANIMATION));
        image.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        image.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        return image;
        
    }
}
