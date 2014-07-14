package RequestApiLib;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;
import org.json.*;

import util.ConvertUtil;

public class RestClient {
	// request method
	public enum RequestMethod {
		GET,
		POST
	}
	
	// request parameters
	private ArrayList<NameValuePair> params;
	
	// request header
	private ArrayList<NameValuePair> headers;
	
	// request url
	private String url;
	
	// response status code
	private int responseCode;
	
	// response error message
	private String message;
	
	// response 
	private String response;
	
	// request method
	// default is GET
	private RequestMethod method = RequestMethod.GET;
	
	// set request method
	public void setMethod(RequestMethod method)
	{
		this.method = method;
	}
	
	// response status code
	public int Status()
	{
		return this.responseCode;
	}
	
	// response error message
	public String ErrorMessage()
	{
		return this.message;
	}
	
	// result
	public String Result()
	{
		return this.response;
	}
	
	// add request parameters
	public void AddParam(String name, String value)
	{
		this.params.add(new BasicNameValuePair(name, value));
	}
	
	// add request headers
	public void AddHeader(String name, String value)
	{
		this.headers.add(new BasicNameValuePair(name, value));
	}
	
	// constructor
	public RestClient(String url)
	{
		this.url = url;
		this.params = new ArrayList<NameValuePair>();
		this.headers = new ArrayList<NameValuePair>();
	}
	
	// perform request API
	public void RequestApi() throws Throwable
	{
		switch (this.method)
		{
			case GET :
			{
				try {
					this.Get();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}
				break;
			}
			case POST :
			{
				try {
					this.Post();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	// request using post method
	private void Post() throws Throwable
	{
		HttpPost request = new HttpPost(this.url);
		
		// add header
		for(NameValuePair h : this.headers)
		{
			request.addHeader(h.getName(), h.getValue());
		}
		
		// add params
		try {
			request.setEntity(new UrlEncodedFormEntity(this.params));
			this.executeRequest(request);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	// request using get method
	private void Get() throws Throwable
	{
		String params = "";
		
		if (!this.params.isEmpty())
		{
			params += "?";
			
			for (NameValuePair p : this.params)
			{
				String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue());
				
				if (params.length() > 1)
				{
					params += "&";
				}
				
				params += paramString;
			}
		}
		
		HttpGet request = new HttpGet(this.url + params);
		
		// add header
		for (NameValuePair h : this.headers)
		{
			request.addHeader(h.getName(), h.getValue());
		}
		
		try {
			this.executeRequest(request);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	// execute request
	private void executeRequest(HttpUriRequest request) throws Throwable
	{
		HttpClient client = HttpClientFactory.getThreadSafeClient();
		HttpResponse response;
		
		try
		{
			response = client.execute(request);
			this.responseCode = response.getStatusLine().getStatusCode();
			this.message = response.getStatusLine().getReasonPhrase();
			
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
			{
				InputStream stream = entity.getContent();
				this.response = ConvertUtil.convertStreamToString(stream);
				stream.close();
			}
		}
		catch(ClientProtocolException e)
		{
			client.getConnectionManager().shutdown();
			e.printStackTrace();
			throw e;
		}
		catch(IOException ex)
		{
			client.getConnectionManager().shutdown();
			ex.printStackTrace();
			throw ex;
		}
	}
}
