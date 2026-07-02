# Harmonogram_Odbioru_Odpadow

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/waste-pickups`
- Panel roli wyzszej: `/dispatcher/waste-pickups`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/waste-pickups`
- Baza PostgreSQL: `harmonogram_odbioru_odpadow`
- Loginy: `client/client`, `dispatcher/dispatcher`

## Opis algorytmu

Algorytm przydziela pojazd do frakcji odpadow, pojemnosci, klasy i realnego czasu trasy. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
