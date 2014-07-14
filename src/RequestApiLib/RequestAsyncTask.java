package RequestApiLib;

import java.util.*;

import org.apache.http.*;

import RequestApiLib.RestClient.RequestMethod;
import android.os.AsyncTask;

public class RequestAsyncTask extends AsyncTask<Void, Void, RequestAsyncResult> 
{	
	// request url
	private String url;
	
	// request parameters
	private ArrayList<NameValuePair> params;
	
	// request header
	private ArrayList<NameValuePair> headers;
	
	// constructor
	public RequestAsyncTask(String url, ArrayList<NameValuePair> params, ArrayList<NameValuePair> headers)
	{
		this.url = url;
		this.params = params;
		this.headers = headers;
	}

	@Override
	protected RequestAsyncResult doInBackground(Void... params) {
		// TODO Auto-generated method stub
		RestClient client = new RestClient(this.url);
		client.setMethod(RequestMethod.POST);
		
		// add params
		for (NameValuePair p : this.params)
		{
			client.AddParam(p.getName(), p.getValue());
		}
		
		// add headers
		for (NameValuePair h : this.headers)
		{
			client.AddHeader(h.getName(), h.getValue());
		}
		
		try {
			client.RequestApi();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RequestAsyncResult(true, 0, null);
		}
		
		return new RequestAsyncResult(false, client.Status(), client.Result());
	}
}