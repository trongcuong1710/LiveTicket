package ApiModel;

import android.content.Context;
import android.content.SharedPreferences;
import SharedPreference.SharedPreferenceManager;

public class UserModel
{
    /**
     * user id
     */
	private String id = "";

    /**
     * username
     */
	private String username = "";

    /**
     * email address
     */
	private String email = "";

    /**
     * access token
     */
	private String access_token = "";

    /**
     *  determine whether user logged in
     */
    private boolean isLogin = false;

    /**
     * singleton instance
     */
    private static UserModel instance = new UserModel();

    /**
     * shared preference name
     */
    private final String SHARE_PREFERENCE_NAME = "User";

    /**
     * id key
     */
    public static final String ID_KEY = "id";

    /**
     * user name key
     */
    public static final String USER_NAME_KEY = "username";

    /**
     * email key
     */
    public static final String EMAIL_KEY = "email";

    /**
     * access token key
     */
    public static final String ACCESS_TOKEN_KEY = "access_token";

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

    /**
     *  determine whether user logged in
     */
    public boolean isLogin() {
        return isLogin;
    }

    /**
     * get singleton instance
     * @return
     */
    public static UserModel getInstance()
    {
        return instance;
    }

    /**
     * private constructor
     */
    private UserModel()
    {
        SharedPreferences preferences = SharedPreferenceManager.getPreference(this.SHARE_PREFERENCE_NAME);

        if (preferences != null)
        {
            this.isLogin = true;
            this.id = SharedPreferenceManager.getString(preferences, this.ID_KEY);
            this.username = SharedPreferenceManager.getString(preferences, this.USER_NAME_KEY);
            this.email = SharedPreferenceManager.getString(preferences, this.EMAIL_KEY);
            this.access_token = SharedPreferenceManager.getString(preferences, this.ACCESS_TOKEN_KEY);
        }
    }

    /**
     * logout user
     */
    public void Logout()
    {
        this.isLogin = false;
        this.id = "";
        this.username = "";
        this.email = "";
        this.access_token = "";

        SharedPreferenceManager.Clear(this.SHARE_PREFERENCE_NAME);
    }

    /**
     * update user
     */
    public void Update()
    {
        SharedPreferences preferences = SharedPreferenceManager.getPreference(this.SHARE_PREFERENCE_NAME);

        if (preferences == null)
            preferences = SharedPreferenceManager.Create(this.SHARE_PREFERENCE_NAME);

        SharedPreferenceManager.setString(preferences, this.ID_KEY, this.id);
        SharedPreferenceManager.setString(preferences, this.USER_NAME_KEY, this.username);
        SharedPreferenceManager.setString(preferences, this.EMAIL_KEY, this.email);
        SharedPreferenceManager.setString(preferences, this.ACCESS_TOKEN_KEY, this.access_token);
    }
}
