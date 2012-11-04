package com.pratikabu.expart.components;

import com.eteks.parser.CalculatorParser;
import com.eteks.parser.CompilationException;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import javax.swing.JTextField;

/**
 *
 * @author SSJ
 */
public class CurrencyTextField extends JTextField {

    private static Color backgroundColor = new Color(255, 255, 204);

    private double amount;

    private boolean legal;

    public CurrencyTextField(){
        super();

        this.setBackground(backgroundColor);
        this.setHorizontalAlignment(JTextField.RIGHT);
        this.setFont(new Font("Arial", Font.BOLD, 12));

        this.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                CurrencyTextField.this.focusGained();
            }

            public void focusLost(FocusEvent e) {
                legal=renderWrittenAmount();
            }
        });
    }

    private void showValueProperly(double d){
        DecimalFormat df=new DecimalFormat("0.00");
        this.setText(df.format(d));
    }

    public boolean renderWrittenAmount(){
        try{
            CalculatorParser cp=new CalculatorParser();
            double d=cp.computeExpression(getText());
            setAmount(d);
            return true;
        }catch(CompilationException e){
            return false;
        }
    }

    public void setAmount(double amount){
        this.amount=amount;
        showValueProperly(amount);
    }

    public double getAmount() {
        renderWrittenAmount();
        return amount;
    }

    private void focusGained(){
        this.selectAll();
    }

    public boolean isLegal() {
        return legal=renderWrittenAmount();
    }

    public static String formatCurrency(double amount){
        DecimalFormat df=new DecimalFormat("#,###,###,##0.00");
        return df.format(amount);
    }
}
