package client.scenes;

import client.utils.ServerUtils;

public class JokersCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public JokersCtrl(ServerUtils server, client.scenes.MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void getDoublePoints(){ }

    public void getHint(){ }

    public void shortenOpponentsTime(){ }
}
