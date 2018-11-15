import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import oracle.jdbc.*;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the League table
 */
public class Game_User implements ActionListener
{
    // command line reader
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame mainFrame;


    private ArrayList<ArrayList<String>> tInfoList; // list of the tables created; It is the list of list of informations about each table
    /*
     * constructs login window and loads JDBC driver
     */
    public Game_User()
    {
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
        mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        mainFrame.pack();

        // center the frame
        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        mainFrame.setVisible(true);

        // place the cursor in the text field for the username
        usernameField.requestFocus();

        try
        {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //Game_User.class.forName("oracle.jdbc.driver.OracleDriver");
            // may be oracle.jdbc.driver.OracleDriver as of Oracle 11g
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
    }


    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    private boolean connect(String username, String password)
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
        if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
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
        }

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



    /*
     * inserts a League
     */

    private void iInsertTable(String tname){ // should be invoked when pressed InsertTable button

        if(checkIfTableDoesNotExistWithName(tname)){
            //open another window
        } else {
            System.out.print("Error:Table with that name already exists");
            // does not do anything;

        }



        //getAttributesOfTableName(tname); //with this list of attributes, make GUI that allows the user to input values to each attribute and press insert button to go to insertLeague()

    }

    private void fInsertTable(ArrayList<String> tInfo){ //assuming that the first element of tInfo is the name of the table

        PreparedStatement  ps;

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
    }

    private void insertLeague(ArrayList<String> tInfo)
    {

        PreparedStatement  ps;

        try
        {




            createStringForPSInsertWithNumberOfAttribute(tInfo.size());

            ps = con.prepareStatement("INSERT INTO ? VALUES (?,?,?,?,?)");

            for (int i = 0; i < tInfo.size(); i ++){
                ps.setString(i+1, tInfo.get(i)); //i+1 becuse setString because parameter from 1 not 0;

            }

            /*
            System.out.print("\nLeague ID: ");
            bid = Integer.parseInt(in.readLine());
            ps.setInt(2, bid);

            System.out.print("\nLeague Name: ");
            bname = in.readLine();
            ps.setString(3, bname);

            System.out.print("\nLeague Address: ");
            baddr = in.readLine();

            if (baddr.length() == 0)
            {
                ps.setString(4, null);
            }
            else
            {
                ps.setString(4, baddr);
            }

            System.out.print("\nLeague City: ");
            bcity = in.readLine();
            ps.setString(5, bcity);

            System.out.print("\nLeague Phone: ");
            String phoneTemp = in.readLine();
            if (phoneTemp.length() == 0)
            {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            else
            {
                bphone = Integer.parseInt(phoneTemp);
                ps.setInt(5, bphone);
            }
*/
            ps.executeUpdate();

            // commit work
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
                // undo the insert
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    /*private ArrayList<String> getAttributesOfTableName(String tname){
        for (int i = 0; i < tInfoList.size(); i ++) {
            ArrayList<String> currentTable = tInfoList.get(i);
            if (tname.equals(currentTable.get(0))) { // the name of each table
                return currentTable;
            }
        }
        return new ArrayList<String>(); //table of given name does not exist
    }*/

    private boolean checkIfTableDoesNotExistWithName(String tname) {
        for (int i = 0; i < tInfoList.size(); i ++){
            ArrayList<String> currentTable = tInfoList.get(i);
            if (tname.equals(currentTable.get(0))) { // a table with such name already exists
                return false;
            }
        }
        return true;
    }

    private String createStringForPSInsertWithNumberOfAttribute(int nAttr){ // assuming that nAttr will always be larger than 0;
        String rString = "(?";

        for (int i = 1; i < nAttr; i ++){ //starting from 1 because we are bypassing the very first one
            rString.concat(",?");
            rString.concat(",?");//compensating for the dataType

        }

        rString.concat(")");
        return rString;
    }

    /*
     * deletes a League
     */
    private void deleteLeague()
    {
        int                bid;
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("DELETE FROM League WHERE League_id = ?");

            System.out.print("\nLeague ID: ");
            bid = Integer.parseInt(in.readLine());
            ps.setInt(1, bid);

            int rowCount = ps.executeUpdate();

            if (rowCount == 0)
            {
                System.out.println("\nLeague " + bid + " does not exist!");
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
     * updates the name of a League
     */
    private void updateLeague()
    {
        int                bid;
        String             bname;
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("UPDATE League SET League_name = ? WHERE League_id = ?");

            System.out.print("\nLeague ID: ");
            bid = Integer.parseInt(in.readLine());
            ps.setInt(2, bid);

            System.out.print("\nLeague Name: ");
            bname = in.readLine();
            ps.setString(1, bname);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nLeague " + bid + " does not exist!");
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
     * display information about Leaguees
     */
    private void showLeague()
    {
        String     bid;
        String     bname;
        String     baddr;
        String     bcity;
        String     bphone;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM League");

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

                bid = rs.getString("League_id");
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
}



