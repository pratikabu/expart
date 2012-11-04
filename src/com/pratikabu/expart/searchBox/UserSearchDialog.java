package com.pratikabu.expart.searchBox;

import com.gmail.pratikabu.pls.controller.DatabaseController;
import com.pratikabu.expart.boundary.UserEntry;
import java.sql.ResultSet;
import java.util.Vector;
import newComponents.searchBox.DefaultListDialog;

/**
 *
 * @author Blue
 */
public class UserSearchDialog extends DefaultListDialog{

    private Vector<Object> vectUser;

    private String sql;

    public UserSearchDialog() {
        super(new UserRenderer());
        this.

        prepareSQL();
    }

    private void prepareSQL(){
        StringBuffer sb=new StringBuffer("");
        //initial append
        sb.append("SELECT uid, uName ");
        sb.append("FROM  User ");
        sb.append("WHERE ");
        
        sb.append("uName LIKE ?");
        sb.append(" ORDER BY uName");

        //final assignment
        sql=sb.toString();
    }

    @Override
    public void updateData(String text) {
        vectUser=new Vector<Object>();

        DatabaseController dbc=new DatabaseController();

        StringBuffer sb=new StringBuffer();
        sb.append('%');
        sb.append(text);
        sb.append('%');

        Object[] value={sb.toString()};

        ResultSet rset=dbc.getQueryResultSet(sql, value);

        try{
            while(rset.next())
                vectUser.add(new UserData(rset.getInt(1), rset.getString(2)));
        }catch(Exception e){
            //do nothing
        }finally{
            dbc.closeConnection();
        }
        
        setListData(vectUser);
    }

    @Override
    public void addNew(){
        UserEntry.showVoucher(null, -1);
    }
}
