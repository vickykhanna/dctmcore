package tel.panfilov.documentum.benchmark.impl;

import java.sql.DriverManager;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class OracleJDBC extends AbstractDFC {

    public OracleJDBC(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doSetup() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doOp() {
        try {
            java.sql.Connection conn = null;
            try {
                conn = DriverManager.getConnection(_docbase, _userName,
                        _password);
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doRelease() {

    }

}
