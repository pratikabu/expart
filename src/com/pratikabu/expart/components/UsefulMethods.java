/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pratikabu.expart.components;

import com.gmail.pratikabu.pls.controller.DatabaseController;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import newComponents.DateSelector;

/**
 *
 * @author SSJ
 */
public class UsefulMethods {

    //period for parsing
    public static Date d;

    public static ProfileInfo profile;

    public static Date parseDate(String text){
        try{
            Pattern pat=Pattern.compile("\\d+");//1 or more digits>> Thank you Kathy Sierra
            Matcher m=pat.matcher(text);

            Calendar cal=Calendar.getInstance();
            Vector<Integer> vectInt=new Vector<Integer>();
            while(m.find()){
                vectInt.add(Integer.parseInt(m.group()));
            }

            int year;

            if(vectInt.size()==3){//all have
                year=vectInt.elementAt(2);
                if(year<95){
                    year+=2000;
                }else if(year<100)
                    year+=1900;
                
                cal.set(year, vectInt.elementAt(1)-1, vectInt.elementAt(0));
            }else if(vectInt.size()==2){//two have
                cal=parseLessThan3(vectInt.elementAt(0), vectInt.elementAt(1)-1);
            }else{
                cal=parseLessThan3(vectInt.elementAt(0), -1);
            }

            return cal.getTime();
        }catch(Exception e){
            return null;
        }
    }

    private static Calendar parseLessThan3(int selectedDate, int curMonth) throws Exception{
        Calendar calS=Calendar.getInstance();
        calS.setTime(d);

        Calendar calE=Calendar.getInstance();

        if(curMonth==-1)
            curMonth=calS.get(calS.MONTH);
        int curYear=calS.get(calS.YEAR);
        int monthDays=DateSelector.getMonthDays(curYear, curMonth+1);
        int curDate;
        Calendar cal=Calendar.getInstance();

        if(selectedDate<=monthDays){
            curDate=selectedDate;
            cal.set(curYear, curMonth, curDate);

            //check the period end constraint
            if((cal.get(cal.DATE)==calE.get(calE.DATE) &&
                    cal.get(cal.MONTH)==calE.get(calE.MONTH) &&
                    cal.get(cal.YEAR)==calE.get(calE.YEAR)) || cal.getTime().before(calE.getTime())){
                //it is ok, else throw exception
            }else
                throw new Exception("Out of range");
        }else
            throw new Exception("Out of range");
        return cal;
    }

    public static final int SHORT=1, MEDIUM=2, MEDIUM_WITH_DAY=3;
    
    public static String toString(Date date, int style){
        if(date!=null){
            if(style==SHORT){
                Calendar cal=Calendar.getInstance();
                cal.setTime(date);
                String dt = cal.get(cal.DATE) + "";
                if(dt.length() == 1)
                    dt = "0" + dt;
                String mnth = (cal.get(cal.MONTH)+1) + "";
                if(mnth.length() == 1)
                    mnth = "0" + mnth;
                return dt + "-" + mnth + "-" + cal.get(cal.YEAR);
            }else if(style==MEDIUM){
                Calendar cal=Calendar.getInstance();
                cal.setTime(date);
                int dateint=cal.get(cal.DATE);
                return dateint+getDateSuffix(dateint)+" "+getMonth(cal.get(cal.MONTH))+", "+cal.get(cal.YEAR);
            }else{
                Calendar cal=Calendar.getInstance();
                cal.setTime(date);
                int dateint=cal.get(cal.DATE);
                return getDay(cal.get(cal.DAY_OF_WEEK))+", "+(dateint+getDateSuffix(dateint))+" "+getMonth(cal.get(cal.MONTH))+", "+cal.get(cal.YEAR);
            }
        }else{
            return "null";
        }
    }

    private static String getDateSuffix(int date){
        int last=date%10;
        if(last==1)
            return "st";
        else if(last==2)
            return "nd";
        else if(last==3)
            return "rd";
        else
            return "th";
    }

    private static String getMonth(int i){
        if(i==0)
            return "Jan";
        else if(i==1)
            return "Feb";
        else if(i==2)
            return "Mar";
        else if(i==3)
            return "Apr";
        else if(i==4)
            return "May";
        else if(i==5)
            return "Jun";
        else if(i==6)
            return "Jul";
        else if(i==7)
            return "Aug";
        else if(i==8)
            return "Sep";
        else if(i==9)
            return "Oct";
        else if(i==10)
            return "Nov";
        else if(i==11)
            return "Dec";
        else
            return "Out Of Range";
    }

    private static String getDay(int i){
        if(i==1)
            return "Sun";
        else if(i==2)
            return "Mon";
        else if(i==3)
            return "Tue";
        else if(i==4)
            return "Wed";
        else if(i==5)
            return "Thu";
        else if(i==6)
            return "Fri";
        else
            return "Sat";
    }

    public static void placeAtRightBottomLocation(Component c){
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension d=toolkit.getScreenSize();
        Insets i=toolkit.getScreenInsets(c.getGraphicsConfiguration());
        Rectangle r=c.getBounds();

        //actual dimension
        d.width=d.width-(i.left+i.right);
        d.height=d.height-(i.bottom+i.top);

        //center
        r.x=d.width-c.getWidth();
        r.y=d.height-c.getHeight();

        c.setBounds(r);
    }

    public static int getNextID(String idSTR, String table){
        int id=-1;

        String sql="SELECT MAX("+idSTR+") FROM "+table;
        Object[] values={};
        DatabaseController dbc=new DatabaseController();
        ResultSet rset=dbc.getQueryResultSet(sql, values);

        try{
            rset.next();
            id=rset.getInt(1)+1;
        }catch(Exception e){
            System.err.println(e+"\nError in getting the key");
        }finally{
            dbc.closeConnection();
        }

        return id;
    }

    public static String getPaymentModeString(int paymentMode){
        String sql = "SELECT pmName "
                + "FROM PaymentMode "
                + "WHERE pmid=?";

        DatabaseController dbc = new DatabaseController();
        ResultSet rset = dbc.getQueryResultSet(sql, new Object[]{paymentMode});

        try{
            rset.next();
            return rset.getString(1);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            dbc.closeConnection();
        }

        return "";
    }
}
