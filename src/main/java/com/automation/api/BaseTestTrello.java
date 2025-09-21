package com.automation.api;

import com.automation.utils.ConfigReader;

public class BaseTestTrello {
    protected String baseUrl = ConfigReader.getProperty("trello.baseUrl");
    protected String key = ConfigReader.getProperty("trello.key");
    protected String token = ConfigReader.getProperty("trello.token");
}
