package PoolGame.Config;

import PoolGame.Items.Pocket;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class PocketsConfig implements Configurable{
    private List<PocketConfig> pocketList;

    /**
     * Initialise the instance with the provided JSONArray of pocket
     * @param obj A JSONArray containing all the pocket configuration
     */
    public PocketsConfig(Object obj) {
        this.parseJSON(obj);
    }

//    /**
//     * Initialise the instance with the provided pocket configs
//     * @param pocketList A list of pocket configs
//     */
//    public PocketsConfig(List<PocketConfig> pocketList) {
//        this.init(pocketList);
//    }

    private void init(List<PocketConfig> pocketList) {
        this.pocketList = pocketList;
    }

    public Configurable parseJSON(Object obj) {
        List<PocketConfig> list = new ArrayList<>();
        JSONArray json = (JSONArray) obj;

        for (Object p : json) {
            list.add(new PocketConfig(p));
        }
        this.init(list);
        return this;
    }

    /**
     * Get the ball list as defined in the config.
     * @return A list of pockets
     */
    public List<PocketConfig> getPocketConfigs() {
        return this.pocketList;
    }
}
