package Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;

import Interface.IConfirmDialog;

/**
 * Created by DFS on 7/17/2014.
 */
public class ConfirmDialog extends PromptDialog
{
    /**
     * listener which will listen when dialog button clicked
     */
    protected IConfirmDialog listener;

    /**
     * constructor
     */
    public ConfirmDialog()
    {
        super();
    }

    /**
     * override buidDialog to build a confirm dialog
     * @return
     */
    @Override
    protected AlertDialog.Builder buildDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(this.title);
        builder.setMessage(this.message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfirmDialog.this.listener.onDialogPositiveClick(ConfirmDialog.this);
                ConfirmDialog.this.getDialog().cancel();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfirmDialog.this.listener.onDialogNegativeClick(ConfirmDialog.this);
                ConfirmDialog.this.getDialog().cancel();
            }
        });

        return builder;
    }

    /**
     * overrride onAttach method to assign listener
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) throws ClassCastException
    {
        super.onAttach(activity);

        try
        {
            this.listener = (IConfirmDialog)activity;
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
