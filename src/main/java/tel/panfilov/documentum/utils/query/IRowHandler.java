package tel.panfilov.documentum.utils.query;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;

public interface IRowHandler {

    void handleRow(IDfCollection row, int position) throws DfException;

}
