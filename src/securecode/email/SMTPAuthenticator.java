/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcodeguard.email;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 *  
 */
public class SMTPAuthenticator extends Authenticator{
    private String username,password;
	public SMTPAuthenticator(String username,String password){
		this.username=username;
		this.password=password;
		
	}
	public javax.mail.PasswordAuthentication getPasswordAuthentication(){
		
		return new PasswordAuthentication(this.username,this.password);
	}

}
