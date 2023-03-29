/**
 * Used to highlight specific rows
 * of a JTable
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeGUIComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class CustomColumnCellRenderer extends DefaultTableCellRenderer {
    private ArrayList<Boolean> highlight;
    private boolean selectable = false; 
    public CustomColumnCellRenderer(ArrayList<Boolean> highlight, boolean selectable){
        this.highlight = highlight;
        this.selectable = selectable;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int col) {

        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        l.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
        
        if(this.highlight.get(row)){
            l.setBackground(Color.decode("#E1E1E1"));
            l.setForeground(Color.black);        
        }else if((hasFocus || isSelected) && this.selectable){
            l.setBackground(Color.decode("#000000"));
            l.setForeground(Color.white);
        }else{
            if(row%2 == 0){
                l.setBackground(Color.decode("#D10000"));
            }else{
                l.setBackground(Color.decode("#7E0000"));
            }
            l.setForeground(Color.white);

        }
        
        return l;

    }
}