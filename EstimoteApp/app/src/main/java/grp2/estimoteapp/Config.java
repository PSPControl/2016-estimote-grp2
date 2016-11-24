package grp2.estimoteapp;

/**
 * Created by pauli on 22/11/16.
 */

public class Config {

    public static class Server {
        public static String ADDRESS = "10.4.2.164";
    }



    public  static class Api {
        public static String URL_BASE = Config.Server.ADDRESS + "/index.php/api";

        public static String URL_BEACONCONFIG = URL_BASE + "/beaconconfig";

        public static String URL_BEACONVALUES = URL_BASE + "/beaconvalues";

        public static String URL_SONGS = URL_BASE + "/songs";

        public static String URL_BACKGROUNDS = URL_BASE + "/backgrounds";
    }



}
