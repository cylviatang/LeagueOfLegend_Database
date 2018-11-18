import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//for the main League GUI window
import java.awt.Frame;


/*
 * This class implements a graphical login window
 */
public class League_Database implements ActionListener
{
    // command line reader
    //private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // container of the login window
    private JFrame mainFrame;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;

    // container of the LOL window
    private JFrame leagueFrame;


    /*
     * constructs login window and loads JDBC driver
     */
    public League_Database()
    {

    //LOGIN WINDOW GUI

        //naming the GUI frame
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
    public Connection getCon(){return con;}

    public void setLeagueFrame(){
        //Containers
        leagueFrame = new JFrame("League Of Legend Functional GUI");
        JPanel LeagueUserPane = new JPanel();
        leagueFrame.setContentPane(LeagueUserPane);

        //Components
        JLabel insertFieldLabel1 = new JLabel("MatchHistory ID ");
        JLabel insertFieldLabel2 = new JLabel("UserName ");
        JLabel insertFieldLabel3 = new JLabel("Champion ID");

        JTextField insertMatchHistoryID = new JTextField(10);
        JTextField insertUserName = new JTextField(10);
        JTextField insertChampionID = new JTextField(10);

        JButton insertIntoMarchHistory = new JButton("Insert Into Match History");
        //insertIntoMarchHistory.setEnabled(false);

        //to use the grid bag layout manager
        GridBagLayout gbLeague = new GridBagLayout();
        GridBagConstraints cLeague = new GridBagConstraints();

        //The main border setting of the pane
        LeagueUserPane.setLayout(gbLeague);
        LeagueUserPane.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));

        // <REQ 1> place the insertMatchHistory label
        cLeague.gridx = 0;
        cLeague.gridy = 0;
        gbLeague.setConstraints(insertFieldLabel1, cLeague);
        LeagueUserPane.add(insertFieldLabel1);

        // place the text field for the insertMatchHistoryID
        cLeague.gridx = 0;
        cLeague.gridy = 1;
        gbLeague.setConstraints(insertMatchHistoryID, cLeague);
        insertMatchHistoryID.setEditable(true);
        LeagueUserPane.add(insertMatchHistoryID);
        insertMatchHistoryID.requestFocus();

        // <REQ 2> place the insertUserName label
        cLeague.gridx = 1;
        cLeague.gridy = 0;
        gbLeague.setConstraints(insertFieldLabel2, cLeague);
        LeagueUserPane.add(insertFieldLabel2);

                 // place the text field for the User Name
        cLeague.gridx = 1;
        cLeague.gridy = 1;
        gbLeague.setConstraints(insertUserName, cLeague);
        insertUserName.setEditable(true);
        LeagueUserPane.add(insertUserName);

        // <REQ 3> place the insertChampion ID label
        cLeague.gridx = 2;
        cLeague.gridy = 0;
        gbLeague.setConstraints(insertFieldLabel3, cLeague);
        LeagueUserPane.add(insertFieldLabel3);

        // place the text field for the champion ID
        cLeague.gridx = 2;
        cLeague.gridy = 1;
        gbLeague.setConstraints(insertChampionID, cLeague);
        insertChampionID.setEditable(true);
        LeagueUserPane.add(insertChampionID);

        // place the login button
        cLeague.gridx = 0;
        cLeague.gridy = 2;
        LeagueUserPane.add(insertIntoMarchHistory,cLeague);

        // anonymous inner class for closing the window
        leagueFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        leagueFrame.pack();

        // center the frame
        Dimension d = leagueFrame.getToolkit().getScreenSize();
        Rectangle r = leagueFrame.getBounds();
        leagueFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        leagueFrame.setVisible(true);

        // register insert button with action event handler
        insertIntoMarchHistory.addActionListener(this);



       /* insertIntoMarchHistory.setEnabled(true);
        String resultMID = insertMatchHistoryID.getText();
        String resultUserName = insertUserName.getText();
        String resultChamID = insertChampionID.getText();

        if(insertIntoMarchHistory.isEnabled()){
            SQLfunc sq = new SQLfunc(con);
            sq.insertMatch_History(resultMID,resultUserName,resultChamID);
        }*/

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
            //TODO: call the next window from here
            setLeagueFrame();


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


    public static void main(String args[])
    {
        League_Database b = new League_Database();
    }
}