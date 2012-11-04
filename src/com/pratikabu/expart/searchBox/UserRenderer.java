/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pratikabu.expart.searchBox;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Blue
 */
public class UserRenderer implements ListCellRenderer{

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        UserData cd=(UserData)value;
        JLabel lb=new JLabel(cd.getUName());
        lb.setOpaque(true);

        if(isSelected){
            lb.setBackground(list.getSelectionBackground());
            lb.setForeground(list.getSelectionForeground());
            lb.setFont(new Font("Arial", Font.BOLD, 12));
        }else{
            lb.setBackground(list.getBackground());
            lb.setForeground(list.getForeground());
        }

        return lb;
    }

}
