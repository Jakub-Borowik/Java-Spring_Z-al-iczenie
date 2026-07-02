# Rezerwacja_Ladowarek_Elektrycznych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/charging-sessions`
- Panel roli wyzszej: `/operator/charging-sessions`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/charging-sessions`
- Baza PostgreSQL: `rezerwacja_ladowarek_elektrycznych`
- Loginy: `client/client`, `operator/operator`

## Opis algorytmu

Algorytm dopasowuje ladowarke do wymaganej mocy, czasu ladowania, standardu i kolizji rezerwacji. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
