# Rezerwacja_Boxow_Magazynowych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/storage`
- Panel roli wyzszej: `/warehouse/storage`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/storage`
- Baza PostgreSQL: `rezerwacja_boxow_magazynowych`
- Loginy: `client/client`, `warehouse/warehouse`

## Opis algorytmu

Algorytm wybiera box po objetosci, zabezpieczeniach, typie skladowania i cenie. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
