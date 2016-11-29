package rc.wedding;

import rc.wedding.gwt.client.jwPersistToServerRpc;
import rc.wedding.ui.MenuView;

import com.vaadin.addon.touchkit.annotations.CacheManifestEnabled;
import com.vaadin.addon.touchkit.annotations.OfflineModeEnabled;
import com.vaadin.addon.touchkit.extensions.OfflineMode;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The UI's "main" class
 */
@SuppressWarnings("serial")
@Widgetset("rc.wedding.gwt.jwWidgetSet")
@Theme("wedding")
// Cache static application files so as the application can be started
// and run even when the network is down.
@CacheManifestEnabled
// Switch to the OfflineMode client UI when the server is unreachable
@OfflineModeEnabled
// Make the server retain UI state whenever the browser reloads the app
@PreserveOnRefresh
public class jwTouchKitUI extends UI {

    private final jwPersistToServerRpc serverRpc = new jwPersistToServerRpc() {
        @Override
        public void persistToServer() {
            // TODO this method is called from client side to store offline data
        }
    };

    @Override
    protected void init(VaadinRequest request) {
        final NavigationManager navigationManager = new NavigationManager();
        navigationManager.setCaption("Menu");
        navigationManager.setCurrentComponent(new MenuView());
        setContent(navigationManager);

        // Use of the OfflineMode connector is optional.
        OfflineMode offlineMode = new OfflineMode();
        offlineMode.extend(this);
        // Maintain the session when the browser app closes.
        offlineMode.setPersistentSessionCookie(true);
        // Define the timeout in secs to wait when a server request is sent
        // before falling back to offline mode.
        offlineMode.setOfflineModeTimeout(300);
    }
}

