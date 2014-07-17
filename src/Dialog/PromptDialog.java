package Dialog;

import android.app.*;
import android.content.DialogInterface;
import android.os.*;

/**
 * Created by DFS on 7/15/2014.
 */
public class PromptDialog extends DialogFragment
{
    /**
     * dialog title
     */
    protected String title;

    /**
     * dialog message
     */
    protected String message;

    /**
     * Constructor
     */
    public PromptDialog()
    {
    }

    /**
     * set dialog title
     * @param title : dialog title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * set dialog message
     * @param message : dialog message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return this.buildDialog().create();
    }

    /**
     * build dialog to prompt
     * @return built dialog
     */
    protected AlertDialog.Builder buildDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(this.title);
        builder.setMessage(this.message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PromptDialog.this.getDialog().cancel();
            }
        });

        return builder;
    }
}
