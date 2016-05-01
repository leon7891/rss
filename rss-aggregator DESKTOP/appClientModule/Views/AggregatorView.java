package Views;
import  javax.swing.*;
import  javax.swing.border.*;

import Controllers.AggregatorController;
import Models.ActivatedFluxsModel;

import  java.awt.*;
import java.util.List;

public class AggregatorView  extends JFrame
{
    private ActivatedFluxsModel[]   model; 
    private AggregatorController    controller;

    public AggregatorView()
    {
        pack();
        setSize(600,800);
        setTitle("RSS Agreggator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}
