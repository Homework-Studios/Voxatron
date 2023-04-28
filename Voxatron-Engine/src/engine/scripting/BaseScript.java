package engine.scripting;

public abstract class BaseScript {

    public abstract void start();

    public abstract void update();

    public abstract void FixedUpdate();

    public void startFixedUpdate() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                FixedUpdate();
            }
        }).start();

    }

}
