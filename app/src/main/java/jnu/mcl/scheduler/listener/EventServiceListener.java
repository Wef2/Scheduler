package jnu.mcl.scheduler.listener;

/**
 * Created by Kim on 2015-11-30.
 */
public interface EventServiceListener {
    void onEventCreate();
    void onEventDelete();
    void onEventUpdate();
}
