package com.automation.trelloPages;

import com.automation.api.BaseTestTrello;

import static io.restassured.RestAssured.given;

public class BoardPage extends BaseTestTrello {

    public String createBoard(String boardName) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", boardName)
                .when()
                .post(baseUrl + "/boards/")
                .then()
                .statusCode(200)
                .extract().path("id");
    }

    public void deleteBoard(String boardId) {
        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete(baseUrl + "/boards/" + boardId)
                .then()
                .statusCode(200);
    }
}
