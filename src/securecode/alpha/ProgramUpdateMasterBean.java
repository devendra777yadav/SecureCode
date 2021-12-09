/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcodeguard.alpha;

/**
 *
 *  
 */
public class ProgramUpdateMasterBean {

    private int Program_Update_ID;
    private int Program_ID;

    private String Update_Date;

    //included for report
    private int User_ID;
    private String Create_Date;
    private String Program_Name;




    public int getProgram_ID() {
        return Program_ID;
    }

    public void setProgram_ID(int Program_ID) {
        this.Program_ID = Program_ID;
    }

    public int getProgram_Update_ID() {
        return Program_Update_ID;
    }

    public void setProgram_Update_ID(int Program_Update_ID) {
        this.Program_Update_ID = Program_Update_ID;
    }

    public String getUpdate_Date() {
        return Update_Date;
    }

    public void setUpdate_Date(String Update_Date) {
        this.Update_Date = Update_Date;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String Create_Date) {
        this.Create_Date = Create_Date;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public String getProgram_Name() {
        return Program_Name;
    }

    public void setProgram_Name(String Program_Name) {
        this.Program_Name = Program_Name;
    }

   
    

}
