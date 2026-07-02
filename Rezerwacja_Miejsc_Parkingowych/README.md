# Rezerwacja_Miejsc_Parkingowych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/parking`
- Panel roli wyzszej: `/guard/parking`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/parking`
- Baza PostgreSQL: `rezerwacja_miejsc_parkingowych`
- Loginy: `client/client`, `guard/guard`

## Opis algorytmu

Algorytm dobiera miejsce po rozmiarze auta, typie miejsca, monitoringu i cenie za czas postoju. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
