package tel.panfilov.documentum.utils.session.apply;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
class ExecSelectSQL extends AbstractApplyCommand<IDfCollection> implements
        IExecSelectSQL {

    public ExecSelectSQL(IDfSession session) {
        super(session);
    }

    @Override
    protected String getCommand() {
        return "EXEC_SELECT_SQL";
    }

    @Override
    public IExecSelectSQL setSQL(String sql) {
        setString("QUERY", sql);
        return this;
    }

    @Override
    public IDfCollection execute() throws DfException {
        return applyForCollection();
    }

}
