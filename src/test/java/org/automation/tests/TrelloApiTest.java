package org.automation.tests;

import com.automation.trelloPages.BoardPage;
import com.automation.trelloPages.CardPage;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TrelloApiTest {

    @Test
    public void trelloBoardAndCardsTest() {
        BoardPage boardPage = new BoardPage();
        CardPage cardPage = new CardPage();

        // Board oluştur
        String boardId = boardPage.createBoard("Test Board");
        System.out.println("Board oluşturuldu: " + boardId);

        // Board içine 2 kart oluştur
        String card1 = cardPage.createCard(boardId, "Kart 1");
        String card2 = cardPage.createCard(boardId, "Kart 2");
        System.out.println("Kartlar oluşturuldu: " + card1 + ", " + card2);

        // Rastgele bir kartı güncelle
        String[] cards = {card1, card2};
        String cardToUpdate = cards[new Random().nextInt(cards.length)];
        cardPage.updateCard(cardToUpdate, "Güncellenmiş Kart");
        System.out.println("Kart güncellendi: " + cardToUpdate);

        // Kartları sil
        cardPage.deleteCard(card1);
        cardPage.deleteCard(card2);
        System.out.println("Kartlar silindi.");

        // Board’u sil
        boardPage.deleteBoard(boardId);
        System.out.println("Board silindi.");
    }
}