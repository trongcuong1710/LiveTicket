package RequestApiLib;

import java.util.*;

import org.apache.http.*;

import Interface.*;
import RequestApiLib.RestClient.RequestMethod;
import android.os.*;

public class RequestAsyncTask extends AsyncTask<Void, Void, RequestAsyncResult> 
{	
	// request url
	private String url;
	
	// request parameters
	private ArrayList<NameValuePair> params;
	
	// request header
	private ArrayList<NameValuePair> headers;

    /**
     * async callback
     * listen to onPreExecute and onPostExecute
     */
    private IAsyncCallBack listener;

    /**
     * Constructor
     * @param url : request url
     * @param params : request parameters
     * @param headers : request headers
     * @param listener : callback listener
     */
	public RequestAsyncTask(String url, ArrayList<NameValuePair> params, ArrayList<NameValuePair> headers, IAsyncCallBack listener)
	{
		this.url = url;
		this.params = params;
		this.headers = headers;
        this.listener = listener;
	}

	@Override
	protected RequestAsyncResult doInBackground(Void... params) {
		// TODO Auto-generated method stub
		RestClient client = new RestClient(this.url);
		client.setMethod(RequestMethod.POST);
		
		// add params
        if (this.params != null)
        {
            for (NameValuePair p : this.params)
            {
                client.AddParam(p.getName(), p.getValue());
            }
        }
		
		// add headers
        if (this.headers != null)
        {
            for (NameValuePair h : this.headers)
            {
                client.AddHeader(h.getName(), h.getValue());
            }
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

    @Override
    protected void onPreExecute()
    {
        this.listener.onBeginTask();
    }

    @Override
    protected void onPostExecute(RequestAsyncResult result)
    {
        this.listener.onTaskComplete(result);
    }
}
