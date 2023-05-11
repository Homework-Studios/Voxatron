package window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class SettingsManager {

    public static SettingsManager instance;
    public HashMap<String, String> settings = new HashMap<>();
    private String unparsedSettings = "";

    public SettingsManager() {
        instance = this;
        try {
            loadSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSettings() throws IOException {
        // load the file into the string at "workdir/Voxatron/Settings/settings.vts"
        String workdir = System.getProperty("user.dir");
        String path = workdir + "\\Voxatron\\Settings\\settings.vts";
        Path settingsPath = Path.of(path);
        unparsedSettings = Files.readString(settingsPath);

        // split the string at ;
        String[] settings = unparsedSettings.split(";");

        // a settings looks like this: "settingName:settingValue"
        // split the setting at : and add it to the dictionary
        for (String setting : settings) {

            // skip empty
            if (setting.equals("")) {
                continue;
            }

            String[] settingParts = setting.split(":");
            this.settings.put(settingParts[0], settingParts[1]);
        }
    }

    public String getSetting(String settingName) {
        return settings.getOrDefault(settingName, "0");
    }

    public boolean getSettingBoolean(String settingName) {
        return getSetting(settingName).equals("1");
    }

    public void setSetting(String settingName, String settingValue) {
        settings.put(settingName, settingValue);
    }

    public void setSetting(String settingName, boolean settingValue) {
        setSetting(settingName, settingValue ? "1" : "0");
    }

    public void saveSettings() throws IOException {
        // reparse the settings
        unparsedSettings = "";

        // loop over each setting and put the setting in the string separated by ;
        for (String settingName : settings.keySet()) {
            String settingValue = settings.get(settingName);
            unparsedSettings += settingName + ":" + settingValue + ";";
        }

        // save the string to the file at "workdir/Voxatron/Settings/settings.vts"
        String workdir = System.getProperty("user.dir");
        String path = workdir + "\\Voxatron\\Settings\\settings.vts";
        Path settingsPath = Path.of(path);
        Files.writeString(settingsPath, unparsedSettings);
    }
}
