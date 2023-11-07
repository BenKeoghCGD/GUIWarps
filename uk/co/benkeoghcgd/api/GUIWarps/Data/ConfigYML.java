package uk.co.benkeoghcgd.api.GUIWarps.Data;

import uk.co.benkeoghcgd.api.AxiusCore.API.Utilities.DataHandler;
import uk.co.benkeoghcgd.api.GUIWarps.GUIWarps;

public class ConfigYML extends DataHandler {

    static ConfigYML instance;

    public static ConfigYML getInstance() {
        return instance;
    }

    public ConfigYML() {
        super(GUIWarps.getInstance(), "Config");
        saveDefaults();
        instance = this;
    }

    @Override
    protected void saveDefaults() {
        setData("showWarpsWithoutPermissions", false, false);
    }
}
