import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLfunc {

    Connection connec;
    public SQLfunc(Connection e){
        connec = e;
    }

    //Insertion: into Match History Table
    public void insertMatch_History(String matchHistoryID, String username, String championid) {
        PreparedStatement ps;

        //insert into Match_History values (10000,'jayJ',100);
        String iString = "INSERT INTO Match_History VALUES ("+matchHistoryID+",'"+username+"',"+championid+")";
        System.out.println(iString);
        /*iString.concat(Integer.toString(matchHistoryID));
        iString.concat(", ");
        iString.concat(username);
        iString.concat(", ");
        iString.concat(champion);
        iString.concat(")");*/
        try {
            ps = connec.prepareStatement(iString);
            ps.executeUpdate();

            // commit work
            connec.commit();

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            try {
                // undo the insert
                connec.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

}
