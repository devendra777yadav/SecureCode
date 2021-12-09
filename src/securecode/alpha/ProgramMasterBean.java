/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcodeguard.alpha;

/**
 *
 *  
 */
public class ProgramMasterBean {

    private int Program_ID;
    private int User_ID;
    private String Program_Name;
    private String Program_Path;
    private String Create_Date;

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String Create_Date) {
        this.Create_Date = Create_Date;
    }

    public int getProgram_ID() {
        return Program_ID;
    }

    public void setProgram_ID(int Program_ID) {
        this.Program_ID = Program_ID;
    }

    public String getProgram_Name() {
        return Program_Name;
    }

    public void setProgram_Name(String Program_Name) {
        this.Program_Name = Program_Name;
    }

    public String getProgram_Path() {
        return Program_Path;
    }

    public void setProgram_Path(String Program_Path) {
        this.Program_Path = Program_Path;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }
}
