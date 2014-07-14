package RequestApiLib;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

// Thread safe factory for HttpClient
public class HttpClientFactory {
	
	// default http client
	private static DefaultHttpClient client;
	
	// get http client 
	public synchronized static DefaultHttpClient getThreadSafeClient() {
		if (client != null)
		{
			return client;
		}
		
		client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		HttpParams params = client.getParams();
		
		client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
		
		return client;
	}
}
