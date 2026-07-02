# Planowanie_Dostaw_Diet_Pudelkowych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/meal-deliveries`
- Panel roli wyzszej: `/dispatcher/meal-deliveries`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/meal-deliveries`
- Baza PostgreSQL: `planowanie_dostaw_diet_pudelkowych`
- Loginy: `client/client`, `dispatcher/dispatcher`

## Opis algorytmu

Algorytm dobiera trase po strefie, liczbie adresow, izolacji termicznej i czasie dostaw. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
