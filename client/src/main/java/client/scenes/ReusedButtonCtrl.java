package client.scenes;

public abstract class ReusedButtonCtrl {

    protected final MainCtrl mainCtrl;

    public ReusedButtonCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void showHome() {
        mainCtrl.showHome();
    }

    public void toggleSound() {}


}
