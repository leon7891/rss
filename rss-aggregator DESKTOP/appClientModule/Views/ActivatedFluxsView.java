package Views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controllers.ActivatedFluxsController;
import Controllers.AggregatorController;
import Models.ActivatedFluxsModel;

public class ActivatedFluxsView {
	
	private ActivatedFluxsModel[]   model; 
    private AggregatorController    controller;
    
    private JTextField  edit;
	
	public ActivatedFluxsView(ActivatedFluxsModel[] model, ActivatedFluxsController controller, AggregatorView superView){
		this.model = model;       
        this.controller = controller;
        
        JPanel  panel = new   JPanel();
        panel.setLayout(new BorderLayout());
        
        //edit = new    JTextField();
        //panel.add(edit,BorderLayout.NORTH);
        
        //les boutons
        JButton but=null;
        JPanel  panel2=new  JPanel();
        panel2.setLayout(new GridLayout(4,4));
        
        //1
        but=new JButton(model[0].title);
        but.addActionListener(controller);
        panel2.add(but);
        
        //ajout du panel
        panel.add(panel2,BorderLayout.CENTER);
        
        //divers
        superView.setContentPane(panel);
        superView.pack();
        SwingUtilities.updateComponentTreeUI(superView);
	}
}