import assets.Asset;

public class Main {

    public static void main(String[] args) {
        //new Input();

        EngineForm form = new EngineForm();

        form.pack();
        form.setVisible(true);
        //init assets and create a test asset
        if (args.length > 0 && !args[0].equals("")) {
            Asset.ASSET_DIR = args[0];
        }
        //Asset.init();
//window.init();

        //Renderer renderer = new Renderer();
        //renderer.begin();
    }
}