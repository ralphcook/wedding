package rc.wedding.ui;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class UiUtil
{
  public static void showNotification(String s, int duration)  // duration here is in seconds
  {
    Notification n = new Notification(s);
    if (duration > 0) { duration *= 1000; }  // convert from seconds to milliseconds
    n.setDelayMsec(duration);
    n.show(Page.getCurrent());
  }
}
