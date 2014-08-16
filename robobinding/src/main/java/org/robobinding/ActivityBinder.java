package org.robobinding;

import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.internal.guava.Preconditions;

import android.app.Activity;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ActivityBinder {
    private final Activity activity;
    private final BinderImplementor binderImplementor;

    public ActivityBinder(Activity activity, BinderImplementor binderImplementor) {
	this.activity = activity;
	this.binderImplementor = binderImplementor;
    }

    public void inflateAndBind(int layoutId, Object presentationModel) {
	checkValidResourceId(layoutId, "invalid layoutId '" + layoutId + "'");
	Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
	
	View rootView = binderImplementor.inflateAndBind(layoutId, presentationModel);
	activity.setContentView(rootView);
    }

}
