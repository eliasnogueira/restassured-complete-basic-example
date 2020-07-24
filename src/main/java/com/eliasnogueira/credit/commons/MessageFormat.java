package com.eliasnogueira.credit.commons;

import com.eliasnogueira.credit.config.Configuration;
import com.eliasnogueira.credit.config.ConfigurationManager;

public class MessageFormat {

    private MessageFormat() {}
    /*
     * This method was created to remove the post if the environment is test because the 443 port must be informed
     * to make the requests, but should not be show in the URL
     */
    public static String locationURLByEnvironment() {
        String locationURL;
        Configuration configuration = ConfigurationManager.getConfiguration();
        String env = System.getProperty("environment");

        locationURL = env != null && env.equals("test") ? java.text.MessageFormat
            .format("{0}{1}/simulations/", configuration.baseURI(), configuration.basePath())
            : java.text.MessageFormat.format("{0}:{1}{2}/simulations/", configuration.baseURI(),
                String.valueOf(configuration.port()), configuration.basePath());

        return locationURL;
    }
}
