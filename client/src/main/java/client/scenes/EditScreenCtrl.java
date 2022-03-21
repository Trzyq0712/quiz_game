package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;


public class EditScreenCtrl
        extends BaseCtrl {

    private final ServerUtils server;

    @Inject
    public EditScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    public void addActivity() {
        mainCtrl.editActivity(true);
    }
}
