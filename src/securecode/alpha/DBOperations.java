/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcodeguard.alpha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 *  
 */
public class DBOperations {

    //----------------------------------------------------------------------
    //                               Login Related
    //----------------------------------------------------------------------
    public UsermasterBean authenticateUser(String userName, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsermasterBean objBean = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    objBean = new UsermasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setPassword(rs.getString("Password"));
                    objBean.setUserType(rs.getString("User_Type"));
                    objBean.setUserStatus(rs.getString("User_Status"));
                    objBean.setSecurityQuestion(rs.getString("Security_Question"));
                    objBean.setSecurityAnswer(rs.getString("Security_Answer"));
                }
            }
        } catch (Exception e) {
            System.out.println("authenticateUser(String userName, String password) : of DBoperations" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("authenticateUser(String userName, String password) : of DBoperations" + e);
            }
        }
        return objBean;
    }

    public UsermasterBean getUserDetailByUsername(String userName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsermasterBean objBean = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                objBean = new UsermasterBean();
                objBean.setUserId(rs.getInt("User_ID"));
                objBean.setUsername(rs.getString("Username"));
                objBean.setPassword(rs.getString("Password"));
                objBean.setUserType(rs.getString("User_Type"));
                objBean.setUserStatus(rs.getString("User_Status"));
                objBean.setSecurityQuestion(rs.getString("Security_Question"));
                objBean.setSecurityAnswer(rs.getString("Security_Answer"));
            }
        } catch (Exception e) {
            System.out.println("getUserDetailByUsername(String userName)  of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getUserDetailByUsername(String userName) of DBoperations : " + e);
            }
        }
        return objBean;
    }

    public int addUserActivity(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int userActivityId = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("insert into useractivitymaster ( User_ID ,Login_Time) values(?,?) ");
            pstmt.setInt(1, userId);
            pstmt.setString(2, getCurrentDateTime());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                pstmt = conn.prepareStatement("select max(Activity_ID) from useractivitymaster");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    userActivityId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("addUserActivity(int userId) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("addUserActivity(int userId) of DBoperations : " + e);
            }
        }
        return userActivityId;
    }

    public void updateUserActivity(int userActivityId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("update useractivitymaster set Logout_Time = ? where Activity_ID=?");
            pstmt.setString(1, getCurrentDateTime());
            pstmt.setInt(2, userActivityId);

            int i = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("addUserActivity(int userId) of DBoperations : " + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("addUserActivity(int userId) of DBoperations : " + e);
            }
        }
    }

    public ArrayList getAllUserActivityDetailList() {
        Connection conn = null;
        ArrayList alstUserActivity = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select uam.Activity_ID,uam.User_ID,um.Username, uam.Login_Time,uam.Logout_Time  from usermaster um,useractivitymaster uam where um.User_Id=uam.User_Id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UserActivityMasterBean objBean = new UserActivityMasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setActivityId(rs.getInt("Activity_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setLoginTime(rs.getString("Login_Time"));
                    objBean.setLogoutTime(rs.getString("Logout_Time"));
                    alstUserActivity.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserActivityDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserActivityDetailList() of DBoperations : " + e);
            }
        }
        return alstUserActivity;
    }

    public ArrayList getAllUserActivityDetailListByUsername(String username) {
        Connection conn = null;
        ArrayList alstUserActivity = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select uam.Activity_ID,uam.User_ID,um.Username, uam.Login_Time,uam.Logout_Time  from usermaster um,useractivitymaster uam where um.User_Id=uam.User_Id and um.Username=?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UserActivityMasterBean objBean = new UserActivityMasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setActivityId(rs.getInt("Activity_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setLoginTime(rs.getString("Login_Time"));
                    objBean.setLogoutTime(rs.getString("Logout_Time"));
                    alstUserActivity.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserActivityDetailListByUsername(String username)  of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserActivityDetailListByUsername(String username)  of DBoperations : " + e);
            }
        }
        return alstUserActivity;
    }
    //-------------------------------------------------------------------------
    //                                 UserAccountDetail Related
    //-------------------------------------------------------------------------

    public ArrayList getAllUserNameList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select Username from usermaster");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                alstUser.add(rs.getString("Username"));
            }
        } catch (Exception e) {
            System.out.println("getAllUserList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserList() of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public ArrayList getAllUserDetailList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select um.User_ID,Username,Password,User_Type,User_Status,Security_Question,Security_Answer from usermaster um,userpersonaldetail upd where um.User_Id=upd.User_Id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UsermasterBean objBean = new UsermasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setPassword(rs.getString("Password"));
                    objBean.setUserType(rs.getString("User_Type"));
                    objBean.setUserStatus(rs.getString("User_Status"));
                    objBean.setSecurityQuestion(rs.getString("Security_Question"));
                    objBean.setSecurityAnswer(rs.getString("Security_Answer"));
                    alstUser.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserDetailList() of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public UsermasterBean getUserAccountDetailByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsermasterBean objBean = new UsermasterBean();
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select User_ID,Username,Password,User_Type,User_Status,Security_Question,Security_Answer from usermaster where User_Id = ?");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                {

                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setPassword(rs.getString("Password"));
                    objBean.setUserType(rs.getString("User_Type"));
                    objBean.setUserStatus(rs.getString("User_Status"));
                    objBean.setSecurityQuestion(rs.getString("Security_Question"));
                    objBean.setSecurityAnswer(rs.getString("Security_Answer"));
                }
            }
        } catch (Exception e) {
            System.out.println("getUserDetailByUserId(int userId) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getUserDetailByUserId(int userId) of DBoperations : " + e);
            }
        }
        return objBean;
    }

    public int getMaxUserId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxUserID = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select max(User_ID) from usermaster");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maxUserID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getMaxUserId() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getMaxUserId() of DBoperations : " + e);
            }
        }
        return maxUserID;
    }

    public String addUserAccountDetail(UsermasterBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ?");
            pstmt.setString(1, objBean.getUsername());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = "exists";
            } else {

                pstmt = conn.prepareStatement("insert into usermaster ( User_ID ,Username ,Password,User_Type, User_Status ,Security_Question , Security_Answer) values(?,?,?,?,?,?,?) ");
                pstmt.setInt(1, objBean.getUserId());
                pstmt.setString(2, objBean.getUsername());
                pstmt.setString(3, objBean.getPassword());
                pstmt.setString(4, objBean.getUserType());
                pstmt.setString(5, objBean.getUserStatus());
                pstmt.setString(6, objBean.getSecurityQuestion());
                pstmt.setString(7, objBean.getSecurityAnswer());

                System.out.println(pstmt.toString());
                int i = pstmt.executeUpdate();
                if (i > 0) {
                    pstmt = conn.prepareStatement("insert into userpersonaldetail ( User_ID) values(?) ");
                    pstmt.setInt(1, objBean.getUserId());
                    int j = pstmt.executeUpdate();
                    if (j > 0) {
                        result = "added";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("addUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("addUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
            }
        }
        return result;
    }

    public String updateUserAccountDetail(UsermasterBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ? and User_ID !=?");
            pstmt.setString(1, objBean.getUsername());
            pstmt.setInt(2, objBean.getUserId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = "exists";
            } else {
                pstmt = conn.prepareStatement("update usermaster set Username = ?,Password=?,User_Type =?, User_Status = ? ,Security_Question = ?, Security_Answer = ? where User_ID=?");
                pstmt.setString(1, objBean.getUsername());
                pstmt.setString(2, objBean.getPassword());
                pstmt.setString(3, objBean.getUserType());
                pstmt.setString(4, objBean.getUserStatus());
                pstmt.setString(5, objBean.getSecurityQuestion());
                pstmt.setString(6, objBean.getSecurityAnswer());
                pstmt.setInt(7, objBean.getUserId());
                System.out.println(pstmt.toString());
                int i = pstmt.executeUpdate();
                if (i > 0) {
                    result = "updated";
                }
            }
        } catch (Exception e) {
            System.out.println("updateUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("updateUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
            }
        }
        return result;
    }

    //---------------------------------------------------------------------------------------
    //        UserPersonalDetail Related
    //-----------------------------------------------------------------------------------------------
    public ArrayList getAllUserPersonalDetailList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select upd.User_ID,Name,Date_Of_Birth,Address,Phone,Mobile,Email from usermaster um,userpersonaldetail upd where um.User_Id=upd.User_Id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UserPersonalDetailBean objBean = new UserPersonalDetailBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setName(rs.getString("Name"));
                    objBean.setDateOfBirth(rs.getString("Date_Of_Birth"));
                    objBean.setAddress(rs.getString("Address"));
                    objBean.setPhone(rs.getString("Phone"));
                    objBean.setMobile(rs.getString("Mobile"));
                    objBean.setEmail(rs.getString("Email"));
                    alstUser.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserPersonalDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserPersonalDetailList() of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public UserPersonalDetailBean getUserPersonalDetailByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserPersonalDetailBean objBean = new UserPersonalDetailBean();
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select User_ID,Name,Date_Of_Birth,Address,Phone,Mobile,Email from userpersonaldetail where User_Id=?");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                {
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setName(rs.getString("Name"));
                    objBean.setDateOfBirth(rs.getString("Date_Of_Birth"));
                    objBean.setAddress(rs.getString("Address"));
                    objBean.setPhone(rs.getString("Phone"));
                    objBean.setMobile(rs.getString("Mobile"));
                    objBean.setEmail(rs.getString("Email"));
                }
            }
        } catch (Exception e) {
            System.out.println("getUserPersonalDetailByUserId(int userId) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getUserPersonalDetailByUserId(int userId) of DBoperations : " + e);
            }
        }
        return objBean;
    }

    public String updateUserPersonalDetail(UserPersonalDetailBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("update userpersonaldetail set Name = ?,Date_Of_Birth=?,Address =?, Phone = ? ,Mobile = ?, Email= ? where User_ID =?");
            pstmt.setString(1, objBean.getName());
            pstmt.setString(2, objBean.getDateOfBirth());
            pstmt.setString(3, objBean.getAddress());
            pstmt.setString(4, objBean.getPhone());
            pstmt.setString(5, objBean.getMobile());
            pstmt.setString(6, objBean.getEmail());
            pstmt.setInt(7, objBean.getUserId());
            System.out.println(pstmt.toString());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                result = "updated";
            }
        } catch (Exception e) {
            System.out.println("updateUserPersonalDetail(UserPersonalDetailBean objBean) of DBoperations : " + e);
        } finally {
            try {

                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("updateUserPersonalDetail(UserPersonalDetailBean objBean) of DBoperations finally : " + e);
            }
        }
        return result;
    }

    public void insertUpdateProgramDetail(ProgramUpdateMasterBean objBean) {


        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            String strQuery = "insert into programupdatemaster (Program_ID, Update_Date) values ('" + objBean.getProgram_ID() + "','" + objBean.getUpdate_Date() + "')";
            int i = stmt.executeUpdate(strQuery);
            if (i > 0) {
            }
        } catch (Exception e) {
            System.out.println("Exception in insertUpdateProgramDetail DBOperations : " + e);
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in insertUpdateProgramDetail DBOperations finally" + e);
            }
        }

    }

    public int insertSavedProgramDetail(ProgramMasterBean objBean) {
        int programId = 0;




        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
          //  String programPath = objBean.getProgram_Path(); //Linux
            String programPath = objBean.getProgram_Path().replace('\\', '/'); //Windows
            String strQuery = "insert into programmaster (User_ID, Program_Name, Program_Path, Create_Date) values ('" + objBean.getUser_ID() + "','" + objBean.getProgram_Name() + "','" + programPath + "','" + objBean.getCreate_Date() + "')";


            int i = stmt.executeUpdate(strQuery);
            if (i > 0) {
                String strQuery1 = "select max(program_id) from programmaster";
                rs = stmt.executeQuery(strQuery1);
                if (rs.next()) {
                    programId = rs.getInt(1);
                }

            }
        } catch (Exception e) {
            System.out.println("Exception in insertSavedProgramDetail DBOperations : " + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in insertSavedProgramDetail DBOperations finally " + e);
            }
        }
        return programId;
    }

    public ProgramMasterBean getSavedProgramDetailByPath(String programPath) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ProgramMasterBean objBean = null;
        try {

            objBean = new ProgramMasterBean();
            objBean.setUser_ID(-1);
            objBean.setProgram_ID(-1);
            programPath = programPath.replace('\\', '/');



            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            String strQuery = "select Program_ID, User_ID, Program_Name, Program_Path, Create_Date from programmaster where program_path='" + programPath + "'";
            rs = stmt.executeQuery(strQuery);
            if (rs.next()) {
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setProgram_Path(rs.getString("Program_Path").replace('/', '\\')); // Windos
              //  objBean.setProgram_Path(rs.getString("Program_Path"));
                objBean.setUser_ID(rs.getInt("User_ID"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
            }
        } catch (Exception e) {
            System.out.println("Exception in getSavedProgramDetailByPath DBOperations : " + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getSavedProgramDetailByPath DBOperations finally " + e);
            }
        }
        return objBean;
    }

    public void insertAccessedProgramDetail(ProgramAccessMasterBean objBean) {


        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            String strQuery = "insert into programaccessmaster (Program_ID, User_ID, Access_Date) values ('" + objBean.getProgram_ID() + "','" + objBean.getUser_ID() + "','" + objBean.getAccess_Date() + "')";
            int i = stmt.executeUpdate(strQuery);
            if (i > 0) {
            }
        } catch (Exception e) {
            System.out.println("Exception in insertAccessedProgramDetail DBOperations : " + e);
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in insertAccessedProgramDetail DBOperations finally" + e);
            }
        }

    }

    public ArrayList getUsernameList() {
        ArrayList al = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select UserName from usermaster");

            while (rs.next()) {
                al.add(rs.getString("UserName"));
            }
        } catch (Exception e) {
            System.out.println("Exception in DBOperations getUsernameList()" + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in DBOperations getUsernameList() finally " + e);
            }
        }
        return al;
    }

    public ArrayList getUpdatedProgramReportList() {
        ArrayList alPrograms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select pum.Program_ID, pum.Update_Date, pm.User_ID, pm.Program_Name, pm.Create_Date from programupdatemaster as pum , programmaster pm where pum.program_id=pm.program_id");
            while (rs.next()) {
                ProgramUpdateMasterBean objBean = new ProgramUpdateMasterBean();
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setUser_ID(rs.getInt("User_ID"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setUpdate_Date(rs.getString("Update_Date"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
                alPrograms.add(objBean);

            }

        } catch (Exception e) {
            System.out.println("Exception in getUpdatedProgramReportList DBOperations : " + e);
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getUpdatedProgramReportList DBOperations finally " + e);
            }
        }
        return alPrograms;
    }

    public ArrayList getUpdatedProgramReportList(String username) {
        ArrayList alPrograms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select pum.Program_ID, pum.Update_Date, pm.User_ID, pm.Program_Name, pm.Create_Date from programupdatemaster as pum , programmaster pm ,usermaster um where pum.program_id=pm.program_id and pm.user_id=um.user_id and um.username='" + username + "'");
            while (rs.next()) {
                ProgramUpdateMasterBean objBean = new ProgramUpdateMasterBean();
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setUser_ID(rs.getInt("User_ID"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setUpdate_Date(rs.getString("Update_Date"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
                alPrograms.add(objBean);

            }

        } catch (Exception e) {
            System.out.println("Exception in getUpdatedProgramReportList DBOperations : " + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getUpdatedProgramReportList DBOperations finally " + e);
            }
        }
        return alPrograms;
    }

    public ArrayList getAcessedProgramReportList() {
        ArrayList alPrograms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT p.`Program_ID`, p.`User_ID` as creater, p.`Program_Name`, p.`Create_Date`, pa.`Access_Date`, pa.`User_ID` as viewer FROM programmaster p, programaccessmaster pa WHERE pa.Program_ID=p.Program_ID;");
            while (rs.next()) {
                ProgramAccessMasterBean objBean = new ProgramAccessMasterBean();
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setUser_ID(rs.getInt("creater"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setAccess_Date(rs.getString("Access_Date"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
                objBean.setViewer_User_ID(rs.getInt("viewer"));
                alPrograms.add(objBean);

            }

        } catch (Exception e) {
            System.out.println("Exception in getAcessedProgramReportList DBOperations : " + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getAcessedProgramReportList DBOperations finally " + e);
            }
        }
        return alPrograms;
    }

    public ArrayList getAcessedProgramReportListByUsername(String username) {
        ArrayList alPrograms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT p.`Program_ID`, p.`User_ID` as creater, p.`Program_Name`, p.`Create_Date`, pa.`Access_Date`, pa.`User_ID` as viewer FROM programmaster p, programaccessmaster pa, usermaster u WHERE pa.Program_ID=p.Program_ID AND u.`Username`='" + username + "' AND p.`User_ID`= u.`User_ID`");
            while (rs.next()) {
                ProgramAccessMasterBean objBean = new ProgramAccessMasterBean();
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setUser_ID(rs.getInt("creater"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setAccess_Date(rs.getString("Access_Date"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
                objBean.setViewer_User_ID(rs.getInt("viewer"));
                alPrograms.add(objBean);
            }

        } catch (Exception e) {
            System.out.println("Exception in getAcessedProgramReportList DBOperations : " + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getAcessedProgramReportList DBOperations finally" + e);
            }
        }
        return alPrograms;
    }

    public ArrayList getSavedProgramList() {
        ArrayList alPrograms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select Program_ID, User_ID, Program_Name, Program_Path, Create_Date from programmaster");
            while (rs.next()) {
                ProgramMasterBean objBean = new ProgramMasterBean();
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setUser_ID(rs.getInt("User_ID"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setProgram_Path(rs.getString("Program_Path"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
                alPrograms.add(objBean);

            }

        } catch (Exception e) {
            System.out.println("Exception in getSavedProgramList DBOperations : " + e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getSavedProgramList DBOperations finally" + e);
            }
        }
        return alPrograms;
    }

    public ArrayList getSavedProgramListByUsername(String username) {
        ArrayList alPrograms = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select Program_ID, pm.User_ID, Program_Name, Program_Path, Create_Date from programmaster pm,usermaster um where um.user_id=pm.user_id and um.username='" + username + "'");
            while (rs.next()) {
                ProgramMasterBean objBean = new ProgramMasterBean();
                objBean.setProgram_ID(rs.getInt("Program_ID"));
                objBean.setUser_ID(rs.getInt("User_ID"));
                objBean.setProgram_Name(rs.getString("Program_Name"));
                objBean.setProgram_Path(rs.getString("Program_Path"));
                objBean.setCreate_Date(rs.getString("Create_Date"));
                alPrograms.add(objBean);

            }

        } catch (Exception e) {
            System.out.println("Exception in getSavedProgramListByUsername DBOperations : " + e);
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getSavedProgramListByUsername DBOperations finally" + e);
            }
        }
        return alPrograms;
    }


    public String getEmailByUsername(String username)
    {
    Connection conn=null;
    Statement stmt=null;
    ResultSet rs= null;
    String result="failed";

        try {


            conn= DBConnection.getConnection();
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select email from usermaster um,userpersonaldetail upd where um.user_id=upd.user_id and um.username='"+username+"'");

while(rs.next())
{
result=rs.getString("email");
}





        } catch (Exception e) {

            System.out.println("in getEmailByUsername(username) in dboperations"+e);
            return result;
        }
    finally
    {
        try {

                rs.close();
                stmt.close();
                conn.close();

        } catch (Exception e) {

            System.out.println("in getEmailByUsername(username) in dboperations finally"+e);
            return result;
        }
    }

return result;


    }
    //---------------------------------------------------------------------------------------
    //          Common Methods
    //-----------------------------------------------------------------------------------------------
    public String getCurrentDateTime() {
        java.util.Date dd = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        String strDate = sdf.format(dd);
        return strDate;
    }
}
