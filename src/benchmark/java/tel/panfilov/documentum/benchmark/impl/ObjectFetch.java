package tel.panfilov.documentum.benchmark.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectFetch extends ObjectCreation {

	protected BufferedReader _objects;

	public ObjectFetch(String docbase, String userName, String password) {
		super(docbase, userName, password);
	}

	@Override
	public void doSetup() {
		try {
			super.doSetup();
			DfQuery query = new DfQuery("SELECT r_object_id FROM dm_document WHERE FOLDER('/Temp', DESCEND) "
					+ "AND r_content_size=0 and r_object_type='dm_document'");
			IDfCollection collection = null;
			File log = File.createTempFile("ids", "txt");
			log.deleteOnExit();
			OutputStream stream = new BufferedOutputStream(new FileOutputStream(log));
			try {
				collection = query.execute(_session, IDfQuery.DF_EXEC_QUERY);
				while (collection.next()) {
					stream.write((collection.getString(DfDocbaseConstants.R_OBJECT_ID) + "\n").getBytes("UTF-8"));
				}
				stream.flush();
				stream.close();
				_objects = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(log))));
			} finally {
				if (collection != null && collection.getState() != IDfCollection.DF_CLOSED_STATE) {
					collection.close();
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (DfException ex) {
			throw new RuntimeException(ex);
		}
	}

}
