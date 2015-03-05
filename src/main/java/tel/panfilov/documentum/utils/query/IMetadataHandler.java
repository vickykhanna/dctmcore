package tel.panfilov.documentum.utils.query;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;

public interface IMetadataHandler {

    void handleMetadata(IDfCollection row) throws DfException;

}
