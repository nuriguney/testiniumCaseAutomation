package com.automation.trelloPages;


import com.automation.api.BaseTestTrello;

import static io.restassured.RestAssured.given;

public class CardPage extends BaseTestTrello {

    public String createCard(String boardId, String cardName) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("idList", getFirstListId(boardId))
                .queryParam("name", cardName)
                .when()
                .post(baseUrl + "/cards")
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public void updateCard(String cardId, String newName) {
        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", newName)
                .when()
                .put(baseUrl + "/cards/" + cardId)
                .then()
                .statusCode(200);
    }

    public void deleteCard(String cardId) {
        given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .delete(baseUrl + "/cards/" + cardId)
                .then()
                .statusCode(200);
    }

    private String getFirstListId(String boardId) {
        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .get(baseUrl + "/boards/" + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id"); // ilk listeyi al
    }
}