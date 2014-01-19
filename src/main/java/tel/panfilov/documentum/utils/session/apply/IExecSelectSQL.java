package tel.panfilov.documentum.utils.session.apply;

import com.documentum.fc.client.IDfCollection;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public interface IExecSelectSQL extends IApplyCommand<IDfCollection> {

    IExecSelectSQL setSQL(String sql);

}
