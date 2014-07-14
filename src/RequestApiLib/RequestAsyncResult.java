package RequestApiLib;

public class RequestAsyncResult 
{	
	// determine whether process has error
	private Boolean hasError;
	
	// response status code
	private int statusCode;
	
	// result
	private String result;
	
	// determine whether process has error
	public Boolean getHasError()
	{
		return this.hasError;
	}
	
	// response status code
	public int StatusCode()
	{
		return this.statusCode;
	}
	
	// result
	public String Result()
	{
		return this.result;
	}
	
	// constructor
	public RequestAsyncResult(Boolean hasError, int statusCode, String result)
	{
		this.hasError = hasError;
		this.statusCode = statusCode;
		this.result = result;
	}
}
