
// We need to import the java.sql package to use JDBC
import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 
public class textbased implements ActionListener {
    // command line reader 
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame mainFrame;


    /*
     * constructs login window and loads JDBC driver
     */
    public textbased() {
        mainFrame = new JFrame("User Login");

        JLabel usernameLabel = new JLabel("Enter username: ");
        JLabel passwordLabel = new JLabel("Enter password: ");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');

        JButton loginButton = new JButton("Log In");

        JPanel contentPane = new JPanel();
        mainFrame.setContentPane(contentPane);


        // layout components using the GridBag layout manager

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(usernameLabel, c);
        contentPane.add(usernameLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(usernameField, c);
        contentPane.add(usernameField);

        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(passwordLabel, c);
        contentPane.add(passwordLabel);

        // place the password field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(passwordField, c);
        contentPane.add(passwordField);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(loginButton, c);
        contentPane.add(loginButton);

        // register password field and OK button with action event handler
        passwordField.addActionListener(this);
        loginButton.addActionListener(this);

        // anonymous inner class for closing the window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        mainFrame.pack();

        // center the frame
        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        mainFrame.setVisible(true);

        // place the cursor in the text field for the username
        usernameField.requestFocus();

        try {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            // may be oracle.jdbc.driver.OracleDriver as of Oracle 11g
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
    }


    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    private boolean connect(String username, String password) {
        String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";

        try {
            con = DriverManager.getConnection(connectURL, username, password);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            return false;
        }
    }

    /*
     * event handler for login window
     */
    public void actionPerformed(ActionEvent e) {
        if (connect(usernameField.getText(), String.valueOf(passwordField.getPassword()))) {
            // if the username and password are valid,
            // remove the login window and display a text menu
            mainFrame.dispose();
            showMenu();
        } else {
            loginAttempts++;

            if (loginAttempts >= 3) {
                mainFrame.dispose();
                System.exit(-1);
            } else {
                // clear the password
                passwordField.setText("");
            }
        }

    }
    /*
     * displays simple text interface
     */
    private void showMenu() {
        int choice;
        boolean quit;

        quit = false;

        try {
            // disable auto commit mode
            con.setAutoCommit(false);

            while (!quit) {
                System.out.print("\n\nPlease choose one of the following: \n");
                System.out.print("1.  Insert Match\n");
                System.out.print("2.  Delete User\n");
                System.out.print("3.  Update Club Manager ID\n");
                System.out.print("4.  Find event information of rank player\n");
                System.out.print("5.  Find a player's post match information\n");
                System.out.print("6.  Find the number of champions owned by all players\n");

                System.out.print("7.  Update rank \n");
                System.out.print("8.  Update experience level \n");
                System.out.print("9.  Update password \n");
                System.out.print("10. Find club information (averge rank of players, number of members)\n");
                System.out.print("11. Find all competitions taking place in a location");
                System.out.print("12. Quit\n>> ");


                choice = Integer.parseInt(in.readLine());

                switch (choice) {
                    case 1:
                        insertMatch();
                        break;
                    case 2:
                        deleteUser();
                        break;
                    case 3:
                        updateClubManagerID();
                        break;
                    case 4:
                        getParticipationInfo();
                        break;
                    case 5:
                        getMatchInfo();
                        break;
                    case 6:
                        getNumChamp();
                        break;
                    case 7:
                        updateUserLevel();
                        break;
                    case 8:
                        updateUserExperience();
                        break;
                    case 9:
                        updatePassword();
                        break;
                    case 10:
                        showClubInfo();
                        break;
                    case 11:
                        viewNAEvents();
                        break;
                    case 12:
                        quit = true;
                }
            }

            con.close();
            in.close();
            System.out.println("\nGood Bye!\n\n");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException!");

            try {
                con.close();
                System.exit(-1);
            } catch (SQLException ex) {
                System.out.println("Message: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
    }


    /*
     * Deliverable 2
     */
    private void insertMatch() {
        int matchhistoryid;
        String username;
        PreparedStatement ps;
        int championID;

        try {
            ps = con.prepareStatement("INSERT INTO Match_History VALUES (?,?,?)");

            System.out.print("\nMatch History ID: ");
            matchhistoryid = Integer.parseInt(in.readLine());
            ps.setInt(1, matchhistoryid);

            System.out.print("\nUsername: ");
            username = in.readLine();
            ps.setString(2, username);

            System.out.print("\nChampionID ");
            championID = Integer.parseInt(in.readLine());
            ps.setInt(3, championID);

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            try {
                // undo the insert
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }


    /*
     * Deliverable 3
     */
    private void deleteUser() {
        String username;
        PreparedStatement ps;

        try {
            String psString = "DELETE FROM Game_User1 WHERE username ='";

            System.out.print("\nUsername: ");
            username = in.readLine();
            psString = psString+username+"'";
            System.out.print(psString);
            ps = con.prepareStatement(psString);

            ps.executeUpdate();

            int rowCount = ps.executeUpdate();

            /*if (rowCount == 0) {
                System.out.println("\nUser " + username + " does not exist!");
            }*/

            con.commit();

            ps.close();
        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }


    /*
     * Deliverable 4
     */
    private void updateClubManagerID() {
        int oldID;
        int newID;
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("UPDATE Clubs SET clubmanagerID = ? WHERE clubmanagerID = ?");

            System.out.print("\nOld Club Manager ID: ");
            oldID = Integer.parseInt(in.readLine());
            ps.setInt(2, oldID);

            System.out.print("\nNew Club Manager ID: ");
            newID = Integer.parseInt(in.readLine());
            ps.setInt(1, newID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println("\nClub Manager " + oldID + " does not exist!");
            }

            con.commit();

            ps.close();
        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    /*
     * Deliverable 5
     */
    public void getParticipationInfo() {
        Statement statement;
        ResultSet rs;
        String en;
        int date;


        try {
            System.out.print("\n Username: ");
            String username = in.readLine();

            statement = con.createStatement();
            String participationQuery;
            participationQuery = "SELECT p.eventname AS EventName, comp.seasonandyear AS SeasonAndYear FROM Rank_User ru, Clubs c, Participation p, Competitions comp WHERE ru.username ='" + username+ "'AND ru.clubid =  c.clubid AND c.clubid = p.clubid AND p.eventname = comp.eventname";
            //System.out.print(participationQuery);
            rs = statement.executeQuery(participationQuery);

            ResultSetMetaData rsmd = rs.getMetaData();

            //get number of columns
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++) {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }


            System.out.println("\n");

            while (rs.next()) {

                en = rs.getString("EventName");
                System.out.printf("%-10.10s", en);

                date = rs.getInt("SeasonAndYear");
                System.out.printf("%-10.10s", date);
            }

            statement.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getNumChamp() {
        Statement statement;
        ResultSet rs;
        int count;
        String username;

        try {
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT u.username AS Username, COUNT(*) AS ChampionsCount" +
                    "FROM Game_User u, Owns o, Champion champ" +
                    "WHERE u.username = o.username AND o.championid = champ.championid" +
                    "GROUP BY username");


            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println("\n");

            // display column names;
            for (int i = 0; i < numCols; i++) {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }

            System.out.println("\n");

            while (rs.next()) {
                username = rs.getString("Username");
                System.out.printf("%-10.10s", username);

                count = rs.getInt("ChampionsCount");
                System.out.printf("%-10.10s", count);
            }

            statement.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    public void getMatchInfo() {
        Statement statement;
        ResultSet rs;
        String username;
        int date;
        int time;
        String mode;

        try {

            System.out.print("\nUsername: ");
            username = in.readLine();

            statement = con.createStatement();
            rs = statement.executeQuery("SELECT m.date AS Date, m.time AS Time, m.modeid AS Mode" +
                    "FROM Game_User u, Match m, Match_History mh" +
                    "WHERE u.username =" + username + "u.username AND = mh.username AND mh.matchhistoryid = m.matchhistoryid");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++) {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }

            System.out.println("\n");

            while (rs.next()) {

                date = rs.getInt("Date");
                System.out.printf("%-10.10s", date);

                time = rs.getInt("Time");
                System.out.printf("%-10.10s", time);

                mode = rs.getString("Mode");
                System.out.printf("%-10.10s", mode);

            }
            statement.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Deliverable 7

    private void showClubInfo()
    {
        String     clubid;
        String     count;
        String     average;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT clubid, COUNT(username) AS count, AVG(ranklvl) AS average FROM Rank_User GROUP BY clubid");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                clubid = rs.getString("clubid");
                System.out.printf("%-15.15s", clubid);

                count = rs.getString("count");
                System.out.printf("%-20.20s", count);

                average = rs.getString("average");
                if (rs.wasNull())
                {
                    System.out.printf("%-20.20s\n", " ");
                }
                else
                {
                    System.out.printf("%-20.20s\n", average);
                }
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    /*
     * Deliverable 8
     */
    private void updateUserLevel()
    {
        String          username;
        int             ranklvl;
        PreparedStatement  ps;

        try
        {
            System.out.print("\nUsername: ");
            username = in.readLine();

            System.out.print("\nNew rank level: ");
            ranklvl = Integer.parseInt(in.readLine());

            String psString = "UPDATE Rank_User SET ranklvl = " + ranklvl + " WHERE username = '" + username + "'";
            ps = con.prepareStatement(psString);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nUser " + username + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
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
    }

    /*
     * Deliverable 9
     */
    private void updateUserExperience()
    {
        String          username;
        int             experiencelvl;
        PreparedStatement  ps;

        try
        {
            System.out.print("\nUsername: ");
            username = in.readLine();

            System.out.print("\nNew experience level: ");
            experiencelvl = Integer.parseInt(in.readLine());

            String psString = "UPDATE Normal_User SET experiencelvl = " + experiencelvl + " WHERE username = '" + username + "'";
            ps = con.prepareStatement(psString);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nUser " + username + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
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
    }

    /*
     * Deliverable 10
     */
    private void updatePassword()
    {
        String          username;
        String          password;
        PreparedStatement  ps;

        try
        {
            System.out.print("\nUsername: ");
            username = in.readLine();

            System.out.print("\nNew password: ");
            password = in.readLine();

            String psString = "UPDATE Game_User1 SET password = '" + password + "' WHERE username = '" + username + "'";
            ps = con.prepareStatement(psString);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nUser " + username + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
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
    }

    public void viewNAEvents(){
        Statement statement;
        ResultSet rs;
        String name;
        int date;

        try {
            statement = con.createStatement();
            rs = statement.executeQuery("CREATE OR REPLACE VIEW [Events in North America] AS" +
                    "SELECT Eventname AS Name, seasonandyear AS SeasonAndYear" +
                    "FROM Competitions" +
                    "WHERE location = “NorthAmerica”");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++) {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }

            System.out.println("\n");

            while (rs.next()) {

                name = rs.getString("Name");
                System.out.printf("%-10.10s", name);

                date = rs.getInt("Date");
                System.out.printf("%-10.10s", date);

            }
            statement.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    public static void main(String args[])
    {
        textbased db = new textbased();
    }

}