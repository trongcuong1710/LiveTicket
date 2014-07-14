package RequestApiLib;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;
import org.json.*;

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
	private int resonseCode;
	
	// response error message
	private String message;
	
	// response 
	private String response;
	
	// request method
	// default is GET
	private RequestMethod method = RequestMethod.GET;
	
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
}
