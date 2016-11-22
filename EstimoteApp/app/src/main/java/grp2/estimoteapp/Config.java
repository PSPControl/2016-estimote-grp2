package grp2.estimoteapp;

/**
 * Created by pauli on 22/11/16.
 */

public class Config {

    public static class Server {
        public static String ADDRESS = "genericdomain.org";
    }



    public  static class Api {
        public String URL_BASE = Config.Server.ADDRESS + "/index.php/api";

        public String URL_BEACONCONFIG = this.URL_BASE + "/beaconconfig";
        
    }



}
