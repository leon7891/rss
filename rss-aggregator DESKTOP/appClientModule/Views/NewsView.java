package Views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import Controllers.ActivatedFluxsController;
import Controllers.AggregatorController;
import Controllers.NewsController;
import Models.ActivatedFluxsModel;
import Models.NewsModel;
import java.lang.Object;

public class NewsView {    
	
	DefaultListModel dlm = new DefaultListModel();
	
	public NewsView(NewsModel[] model, NewsController newsController, AggregatorView superView){
		
        JList  list = new   JList(createImageMap(model));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(superView.getWidth(), superView.getHeight()));
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        controlPanel.add(scroll);
        
        superView.setContentPane(controlPanel);
        SwingUtilities.updateComponentTreeUI(superView);
	}
	
	private DefaultListModel createImageMap(NewsModel[] list) {
        try {
        	for(NewsModel flux : list){
        		dlm.addElement(flux.title);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dlm;
    }
}
