/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcodeguard.alpha;

/**
 *
 *  
 */
public class ProgramAccessMasterBean {

    private int Program_Access_ID;
    private int Program_ID;
    private int User_ID;
    private String Access_Date;

    //Added for Report

    private int Viewer_User_ID;
    private String Create_Date;
    private String Program_Name;

    

    public String getAccess_Date() {
        return Access_Date;
    }

    public void setAccess_Date(String Access_Date) {
        this.Access_Date = Access_Date;
    }

    public int getProgram_Access_ID() {
        return Program_Access_ID;
    }

    public void setProgram_Access_ID(int Program_Access_ID) {
        this.Program_Access_ID = Program_Access_ID;
    }

    public int getProgram_ID() {
        return Program_ID;
    }

    public void setProgram_ID(int Program_ID) {
        this.Program_ID = Program_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String Create_Date) {
        this.Create_Date = Create_Date;
    }

    public String getProgram_Name() {
        return Program_Name;
    }

    public void setProgram_Name(String Program_Name) {
        this.Program_Name = Program_Name;
    }

    public int getViewer_User_ID() {
        return Viewer_User_ID;
    }

    public void setViewer_User_ID(int Viewer_User_ID) {
        this.Viewer_User_ID = Viewer_User_ID;
    }    
}
