package client.scenes;

public abstract class ReusedButtonCtrl {

    private final MainCtrl mainCtrl;

    public ReusedButtonCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void showHome() {
        mainCtrl.showHome();
    }

    public void toggleSound() {}
}
