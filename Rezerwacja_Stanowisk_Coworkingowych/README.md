# Rezerwacja_Stanowisk_Coworkingowych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/coworking-bookings`
- Panel roli wyzszej: `/manager/coworking-bookings`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/coworking-bookings`
- Baza PostgreSQL: `rezerwacja_stanowisk_coworkingowych`
- Loginy: `client/client`, `manager/manager`

## Opis algorytmu

Algorytm wybiera stanowisko po liczbie osob, standardzie, typie pracy i cenie za wymagany czas. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
