# Ship tracker

## O aplikacji

Aplikacja służy do śledzenia aktualnej pozycji oraz celu podróży statków.

### Działanie
Statki są oznaczone pinami z ikonką statku, po kliknięciu wybranego statku wyświetla się zestaw informacji na jego temat + jeśli znane są dane na temat celu podróży oznaczona jest jego droga do tego celu (cel oznaczony jest wyróżniającą się pinezką).

Informacje nt. konkretnych statków:
- nazwa statku
- typ statku (każdy typ jest zwizualizowany odpowiednim zdjęciem tego typu statku)
- kierunek ruchu statku
- poziom zanurzenia
- znak wywoławczy
- kraj pochodzenia statku
- lokalizacja celu (miasto, kraj, kontynent)

Niektóre informacje zawierają wyjaśnienie w dymku.

Widok przystosowany do różnych szerokości ekranu.

### Technologie
Frontend:
- React.js
- styled-components
- Open Street Map by react-leaflet

Backend:
- Spring Boot
- Hibernate
- Swagger
- JUnit 5
- PostgreSQL
- Docker

### Dane
Dane pochodzą z dwóch API:
- www.barentswatch.no
- www.positionstack.com

### Przykładowe widoki

#### Desktop:

![Desktop - information modal](./screenshots/Ship%20tracker%20-%20desktop%20-%20information%20modal.png)
![Explanation tooltip](./screenshots/Ship%20tracker%20-%20explanation%20tooltip.png)

#### Mobile:

![Mobile - pin active](./screenshots/Ship%20tracker%20-%20iPhone%206%20-%20pin%20active.png)
![Mobile - information modal](./screenshots/Ship%20tracker%20-%20iPhone%206%20-%20information%20modal.png)

## Uruchomienie aplikacji

1. Zbuduj wersję `.jar` aplikacji poprzez komendę `mvn clean package`
2. Przenieś wygenerowany plik - `ship-tracker-0.0.1-SNAPSHOT.jar` do folderu `src/docker`
3. Wypełnij brakujące dane w `docker-compose.yml`
- `API_BARENTSWATCH_CLIENT_ID` - twój klient id z api www.barentswatch.no(np. xyz@sdvrecft.com:jan.kowalski)
- `API_BARENTSWATCH_CLIENT_SECRET` - twój sekret dla klienta z api www.barentswatch.no
- `API_POSITIONSTACK_ACCESSKEY` -  klucz dostępu z api www.positionstack.com
4. Uruchom aplikacje za pomocą `docker-compose up`, zatrzymaj za pomocą `docker-compose down`
5. Swagger UI jest dostępny pod adresem: `http://localhost:8081/swagger-ui.html`
