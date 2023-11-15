package ch.hearc.ig.guideresto.persistence;
import java.sql.* ;
import static java.sql.DriverManager.*; //registerDriver

public class DBOracle {
    public static Connection createSession() {
        try {
            registerDriver(new oracle.jdbc.OracleDriver());
            Connection sess = getConnection("jdbc:oracle:thin:@db.ig.he-arc.ch:1521:ens",
                    "benjamin_casey",
                    "benjamin_casey");

            sess.setAutoCommit(true);

            return sess;
        } catch (SQLException sqlerr) {
            System.out.println(sqlerr);
        }
        return null ;
    }
    public static void dropSession( Connection sess) {
        try {
            sess.close();
        } catch (SQLException sqlerr) {
            System.out.println(sqlerr);
        }
    }
}
