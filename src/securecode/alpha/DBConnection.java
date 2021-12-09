/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcodeguard.alpha;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 *  
 */
public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql:///jcodeguard","jarvis","jarvis@8010");

        } catch (Exception e) {
            System.out.println("Exception in getConnection of DBConnection : "+e);
        }
        return con;
    }
}
