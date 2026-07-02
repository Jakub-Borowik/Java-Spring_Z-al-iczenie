# Rezerwacja_Opiekunow_Zwierzat

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/pet-care`
- Panel roli wyzszej: `/sitter/pet-care`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/pet-care`
- Baza PostgreSQL: `rezerwacja_opiekunow_zwierzat`
- Loginy: `client/client`, `sitter/sitter`

## Opis algorytmu

Algorytm wybiera opiekuna po typie zwierzecia, liczbie zwierzat, doswiadczeniu i braku kolizji. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
