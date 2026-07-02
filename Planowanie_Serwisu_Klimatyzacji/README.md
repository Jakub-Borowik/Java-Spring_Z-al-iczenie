# Planowanie_Serwisu_Klimatyzacji

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/ac-services`
- Panel roli wyzszej: `/technician/ac-services`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/ac-services`
- Baza PostgreSQL: `planowanie_serwisu_klimatyzacji`
- Loginy: `client/client`, `technician/technician`

## Opis algorytmu

Algorytm dopasowuje technika po rodzaju uslugi, liczbie urzadzen, uprawnieniach i czasie naprawy. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
