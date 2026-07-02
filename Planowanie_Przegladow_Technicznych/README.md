# Planowanie_Przegladow_Technicznych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/inspections`
- Panel roli wyzszej: `/inspector/inspections`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/inspections`
- Baza PostgreSQL: `planowanie_przegladow_technicznych`
- Loginy: `client/client`, `inspector/inspector`

## Opis algorytmu

Algorytm dobiera stanowisko diagnostyczne po typie pojazdu, masie, klasie i czasie badania. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
