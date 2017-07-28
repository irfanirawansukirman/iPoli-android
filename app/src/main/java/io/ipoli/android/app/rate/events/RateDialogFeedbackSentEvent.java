package io.ipoli.android.app.rate.events;

/**
 * Created by Polina Zhelyazkova <polina@ipoli.io>
 * on 5/18/16.
 */
public class RateDialogFeedbackSentEvent {
    public final String feedback;
    public final int appRun;

    public RateDialogFeedbackSentEvent(String feedback, int appRun) {
        this.feedback = feedback;
        this.appRun = appRun;
    }
}
