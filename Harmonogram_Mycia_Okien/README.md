# Harmonogram_Mycia_Okien

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/window-cleaning`
- Panel roli wyzszej: `/supervisor/window-cleaning`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/window-cleaning`
- Baza PostgreSQL: `harmonogram_mycia_okien`
- Loginy: `client/client`, `supervisor/supervisor`

## Opis algorytmu

Algorytm dobiera ekipe do liczby okien, wysokosci, wymaganego sprzetu i kosztu. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
