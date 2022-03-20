package client.scenes;


public abstract class ReusedButtonCtrl {

    protected final MainCtrl mainCtrl;
//    protected final ApplicationUtils utils;

    public ReusedButtonCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
//        this.utils = utils;
    }

    public void showHome() {
        mainCtrl.showHome();
    }

    public void toggleSound() {}


}
