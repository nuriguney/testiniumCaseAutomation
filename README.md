## Gereksinimler

- Java 17+
- Maven 3.8+
- Trello API
- Zara Login

## application.properties

`src/test/resources/application.properties` dosyası aşağıdaki gibi olmalıdır:

```properties
# Trello API
trello.baseUrl=https://api.trello.com/1
trello.key=YOUR_TRELLO_API_KEY
trello.token=YOUR_TRELLO_API_TOKEN

# Zara web otomasyon
zara.email=YOUR_EMAIL
zara.password=YOUR_PASSWORD
