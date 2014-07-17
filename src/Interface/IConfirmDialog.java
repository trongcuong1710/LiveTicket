package Interface;

import android.app.DialogFragment;

/**
 * Created by DFS on 7/17/2014.
 */
public interface IConfirmDialog
{
    /**
     * notify when dialog positive button clicked
     * @param dialogFragment
     */
    void onDialogPositiveClick(DialogFragment dialogFragment);

    /**
     * notify when dialog negative button click
     * @param dialogFragment
     */
    void onDialogNegativeClick(DialogFragment dialogFragment);
}
