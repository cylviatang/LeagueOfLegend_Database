//import com.sun.java.util.jar.pack.Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main implements ActionListener{

    private Connection con;
    public static void main(String[] args) {

    }

    public boolean connect(String username, String password)
    {
        String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";

        try
        {
            con = DriverManager.getConnection(connectURL,username,password);

            System.out.println("\nConnected to Oracle!");
            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            return false;
        }
    }


    /*
     * event handler for login window
     */
    public void actionPerformed(ActionEvent e)
    {
        /*if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
        {
            // if the username and password are valid,
            // remove the login window and display a text menu
            mainFrame.dispose();
            showLeague();
        }
        else
        {
            loginAttempts++;

            if (loginAttempts >= 3)
            {
                mainFrame.dispose();
                System.exit(-1);
            }
            else
            {
                // clear the password
                passwordField.setText("");
            }
        }*/

    }


    /*
     * displays simple text interface
     */
    /*private void showMenu()
    {
        int choice;
        boolean quit;

        quit = false;

        try
        {
            // disable auto commit mode
            con.setAutoCommit(false);

            while (!quit)
            {
                System.out.print("\n\nPlease choose one of the following: \n");
                System.out.print("1.  Insert League\n");
                System.out.print("2.  Delete League\n");
                System.out.print("3.  Update League\n");
                System.out.print("4.  Show League\n");
                System.out.print("5.  Quit\n>> ");

                choice = Integer.parseInt(in.readLine());

                System.out.println(" ");

                switch(choice)
                {
                    case 1:  insertLeague(); break;
                    case 2:  deleteLeague(); break;
                    case 3:  updateLeague(); break;
                    case 4:  showLeague(); break;
                    case 5:  quit = true;
                }
            }

            con.close();
            in.close();
            System.out.println("\nGood Bye!\n\n");
            System.exit(0);
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            try
            {
                con.close();
                System.exit(-1);
            }
            catch (SQLException ex)
            {
                System.out.println("Message: " + ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }*/



    // my code
    /*private boolean checkIfTableDoesNotExistWithName(String tname) {
        for (int i = 0; i < tInfoList.size(); i ++){
            ArrayList<String> currentTable = tInfoList.get(i);
            if (tname.equals(currentTable.get(0))) { // a table with such name already exists
                return false;
            }
        }
        return true;
    }

    private String createStringForPSInsertWithNumberOfAttribute(int nAttr){ // assuming that nAttr will always be larger than 0;
        String rString = "(? ?";

        for (int i = 1; i < nAttr; i ++){ //starting from 1 because we are bypassing the very first one
            rString.concat(",? ?");//compensating for the dataType

        }

        rString.concat(")");
        return rString;
    }*/


    // GUI codes
    /*private void iInsertTable(String tname){ // should be invoked when pressed InsertTable button

        if(checkIfTableDoesNotExistWithName(tname)){
            //open another window
        } else {
            System.out.print("Error:Table with that name already exists");
            // does not do anything;

        }



        //getAttributesOfTableName(tname); //with this list of attributes, make GUI that allows the user to input values to each attribute and press insert button to go to insertLeague()

    }

    private void fInsertTable(ArrayList<String> tInfo){ //assuming that the first element of tInfo is the name of the table //tInfo should also contain dataType information

        PreparedStatement ps;

        String pString = "CREATE TABLE ? (";
        pString.concat(createStringForPSInsertWithNumberOfAttribute(tInfo.size()));
        pString.concat(")");

        try {

            ps = con.prepareStatement(pString);


            for (int i = 0; i < tInfo.size(); i++) {
                ps.setString(i + 1, tInfo.get(i)); //i+1 becuse setString because parameter from 1 not 0;

            }

        } catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }*/
/*
    //Insertion: into Match History Table
   /* public void insertMatch_History(int matchHistoryID, String username, String champion)
    {
        PreparedStatement  ps;

        String iString = "INSERT INTO Match_History VALUES (";
        iString.concat(Integer.toString(matchHistoryID));
        iString.concat(", ");
        iString.concat(username);
        iString.concat(", ");
        iString.concat(champion);
        iString.concat(")");

        try
        {
            ps = con.prepareStatement(iString);
            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }

        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }*/
   /* public void insertMatch_History(int matchHistoryID, String username, String champion)
    {
        PreparedStatement  ps;

        String iString = "INSERT INTO Match_History VALUES (";
        iString.concat(Integer.toString(matchHistoryID));
        iString.concat(", ");
        iString.concat(username);
        iString.concat(", ");
        iString.concat(champion);
        iString.concat(")");

        try
        {
            ps = con.prepareStatement(iString);
            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }

        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }*/

    /*private ArrayList<String> getAttributesOfTableName(String tname){
        for (int i = 0; i < tInfoList.size(); i ++) {
            ArrayList<String> currentTable = tInfoList.get(i);
            if (tname.equals(currentTable.get(0))) { // the name of each table
                return currentTable;
            }
        }
        return new ArrayList<String>(); //table of given name does not exist
    }*/



    /*
     * deletes a League
     */
    public void deleteGame_User(String username)
    {
        String iString = "DELETE FROM Game_User WHERE username = ";
        iString.concat(username);

        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement( iString);

            /*System.out.print("\nLeague ID: ");
            bid = Integer.parseInt(in.readLine());
            ps.setInt(1, bid);

            int rowCount = ps.executeUpdate();

            if (rowCount == 0)
            {
                System.out.println("\nLeague " + bid + " does not exist!");
            }*/

            con.commit();

            ps.close();
        }
        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }


    /*
     * updates the name of a League
     */
    public void updateClubs(int clubIDToUpdate, int newClubManagerID)
    {
        /*int                bid;
        String             bname;*/
        String iString = "UPDATE Clubs SET clubmanagerID = ";
        iString.concat(Integer.toString(newClubManagerID));
        iString.concat(" WHERE clubid = ");
        iString.concat(Integer.toString(clubIDToUpdate));


        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement(iString);

            /*System.out.print("\nLeague ID: ");
            bid = Integer.parseInt(in.readLine());
            ps.setInt(2, bid);

            System.out.print("\nLeague Name: ");
            bname = in.readLine();
            ps.setString(1, bname);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nLeague " + bid + " does not exist!");
            }*/

            con.commit();

            ps.close();
        }
        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }


    /*
     * display information about Leaguees
     */
    public String showTableWithName(String tname)
    {
        /*String     bid;
        String     bname;
        String     baddr;
        String     bcity;
        String     bphone;*/
        Statement stmt;
        ResultSet  rs;
        //ArrayList<ArrayList<String>> rtInfo;
        String rtInfo = "";

        String iString = "SELECT * FROM ";
        iString.concat(tname);


        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery(iString);

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();



            //System.out.println(" ");
            rtInfo.concat("\n");

            ArrayList<String> columnNames = new ArrayList<String>();
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                //System.out.printf("%-15s", rsmd.getColumnName(i+1));
                String columnName = rsmd.getColumnName(i+1);
                rtInfo.concat(columnName);
                columnNames.add(columnName);
                rtInfo.concat(returnSpacesAccordingToLengthOfText(columnName));

            }

            //System.out.println(" ");
            rtInfo.concat("\n");


            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                for (String columnName : columnNames){
                    String att = rs.getString(columnName);
                    rtInfo.concat(att);
                    rtInfo.concat(returnSpacesAccordingToLengthOfText(columnName));

                }

                rtInfo.concat("\n");

                /*bid = rs.getString("League_id");
                System.out.printf("%-10.10s", bid);

                bname = rs.getString("League_name");
                System.out.printf("%-20.20s", bname);

                baddr = rs.getString("League_addr");
                if (rs.wasNull())
                {
                    System.out.printf("%-20.20s", " ");
                }
                else
                {
                    System.out.printf("%-20.20s", baddr);
                }

                bcity = rs.getString("League_city");
                System.out.printf("%-15.15s", bcity);

                bphone = rs.getString("League_phone");
                if (rs.wasNull())
                {
                    System.out.printf("%-15.15s\n", " ");
                }
                else
                {
                    System.out.printf("%-15.15s\n", bphone);
                }*/
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }

        return rtInfo;
    }

    public void updatePassword(String username, String newPassword) {
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("UPDATE Game_User1 SET password = ? WHERE username = ?");

            ps.setString(2, username);

            ps.setString(1, newPassword);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nGame_User1 " + username + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    public void experienceUpdate(String username, int newExperience) {
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("UPDATE Normal_User SET password = ? WHERE username = ?");

            ps.setString(2, username);

            ps.setInt(1, newExperience);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nNormal_User " + username + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    public void rankUp(String username, int newRank) {
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("UPDATE Rank_User SET ranklvl = ? WHERE username = ?");

            ps.setString(2, username);

            ps.setInt(1, newRank);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nRank_User " + username + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    public String formatString(String input) {
        int spaces = 20 - input.length();
        for (int i = 0; i < spaces; i++) {
            input.concat(" ");
        }
        return input;
    }

    public String clubInformation() {
        String information = "";
        Statement stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT COUNT(username), AVG(ranklvl) FROM Rank_User GROUP BY clubid");

                    // get info on ResultSet
                    ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                String columnName = rsmd.getColumnName(i+1);
                information.concat(formatString(columnName));
            }

            information.concat("\n");

            while(rs.next())
            {
                information.concat(formatString(rs.getString("username")));
                information.concat(formatString(rs.getString("ranklvl")));
                information.concat(formatString(rs.getString("clubid")));
                information.concat("\n");
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }

        return information;
    }

    public class ParticipationInformation {
        private String eventName;
        private int seasonAndYr;

        public ParticipationInformation(){
            this.eventName = null;
            this.seasonAndYr = 0;
        }


    }


    //returns list of event participation info of selected user
    // includes: username, events they participated in, and when they participated
    //Copied exceptions idk what they actually do hopefully we don't catch any lmao

public List<ParticipationInformation> getParticipationInfo(String username){
        Statement statement;
        ResultSet rs;
    List<ParticipationInformation> loPptInfo = new ArrayList<>();

    try {
        statement = con.createStatement();
        rs = statement.executeQuery("SELECT ru.username, p.eventname, comp.seasonandyear" +
                "FROM Rank_User ru, Clubs c, Participation p, Competitions comp" +
                "WHERE ru.username =" +username + "AND ru.clubid =  c.clubid AND c.clubid = p.clubid AND p.eventname = comp.eventname");


        /* DO WE HAVE TO PRINT COLUMNS STILL IF WE'RE NOT DIRECTLY RUNNING THIS IN TERMINAL???????

        HIHIHIHIHIHIHIHI PLS READ THIS

        ResultSetMetaData rsmd = rs.getMetaData();

        int numCols = rsmd.getColumnCount();
        ArrayList<String> cNames = new ArrayList<>();

        for (int i = 0; i < numCols; i++) {
            String cName = rsmd.getColumnName(i + 1);
            cNames.add(cName);

        }

        System.out.println("\n");

        */
        while (rs.next()) {
            ParticipationInformation pi = new ParticipationInformation();
            pi.eventName = rs.getString("eventname");
            pi.seasonAndYr = rs.getInt("seasonandyear");
            loPptInfo.add(pi);
        }
        statement.close();

        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/

    }

    catch (SQLException ex)
    {
        System.out.println("Message: " + ex.getMessage());

        try
        {
            con.rollback();
        }
        catch (SQLException ex2)
        {
            System.out.println("Message: " + ex2.getMessage());
            System.exit(-1);
        }
    }
    return loPptInfo;
}
    //returns number of champions owned by selected user
    // includes: username and number of champions they own

    public int getNumChamp(String username){
        Statement statement;
        ResultSet rs;
        int championCount = 0;

        try {
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT u.username AS Username, COUNT(*) AS numChampions" +
                    "FROM Game_User u, Owns o, Champion champ" +
                    "WHERE u.username =" + username +"AND u.username = o.username AND o.championid = champ.championid"+
                    "GROUP BY username");
            while(rs.next()) {
                championCount = rs.getInt("numChapmions");
            }

            statement.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        return championCount;
    }

    //HIHIHIHIHIHI ARE THESE CLASSES EXTRA? IS THIS ALLOWED?????? IDK HOW TO JAVA

    //information for one player in a single match
    public class matchInformation {
        private String username;
        private int date;
        private int time;
        private String mode;

        public matchInformation(){
            this.username = null;
            this.date= 0;
            this.time = 0;
            this.mode = null;
        }

    }

    public List<matchInformation> getMatchInfo(String username){
        Statement statement;
        ResultSet rs;
        List<matchInformation> loMatInfo = new ArrayList<>();

        try {
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT u.username AS Username, m.date AS Date, m.time AS Time, m.modeid AS Mode" +
                    "FROM Game_User u, Match m, Match_History mh" +
                    "WHERE u.username =" + username + "u.username AND = mh.username AND mh.matchhistoryid = m.matchhistoryid");

            while (rs.next()) {
                matchInformation mi = new matchInformation();
                mi.date = rs.getInt("Date");
                mi.time = rs.getInt("Time");
                mi.mode = rs.getString("Mode");
                loMatInfo.add(mi);
            }
            statement.close();

        /*catch (IOException e)
        {
            System.out.println("IOException!");
        }*/

        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        return loMatInfo;
    }

    public String returnSpacesAccordingToLengthOfText(String text){
        int maxLength = 20;
        String spaces = "";
        int textLength = text.length();
        int spacesToFill = maxLength - textLength;

        for (int i = 0; i < spacesToFill; i ++) {
            spaces.concat(" ");

        }

        return spaces;
    }

}



