package lab2.registration.model;

import com.google.common.collect.Multimap;

public class WrapperClass {

    private ActionStatus actionStatus;
    private Multimap<Long, Long> currentSubscription;

    public WrapperClass(ActionStatus as, Multimap<Long, Long> cS) {
        this.actionStatus = as;
        this.currentSubscription = cS;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Multimap<Long, Long> getCurrentSubscription() {
        return currentSubscription;
    }

    public void setCurrentSubscription(Multimap<Long, Long> currentSubscription) {
        this.currentSubscription = currentSubscription;
    }
}
