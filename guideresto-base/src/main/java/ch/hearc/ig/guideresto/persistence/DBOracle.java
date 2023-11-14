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

            /*
            System.out.println("Autocomit " + sess.getAutoCommit());
            sess.setAutoCommit(false);
            System.out.println("Autocomit " + sess.getAutoCommit());
            sess.commit();

            DatabaseMetaData mtdt = sess.getMetaData();
            System.out.println("URL connexion: " + mtdt.getURL());
            System.out.println("Nom utilisateur: " + mtdt.getUserName());
            System.out.println("Constructeur: " + mtdt.getDatabaseProductName());
            System.out.println("Version BD: " + mtdt.getDatabaseProductVersion());
            System.out.println("Drive: " + mtdt.getDriverName());
            System.out.println("Driver version: " + mtdt.getDriverVersion());
            System.out.println("supp. SQL Keywords: " + mtdt.getSQLKeywords());

*/
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
