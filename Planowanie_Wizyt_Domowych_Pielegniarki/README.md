# Planowanie_Wizyt_Domowych_Pielegniarki

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/home-visits`
- Panel roli wyzszej: `/nurse/home-visits`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/home-visits`
- Baza PostgreSQL: `planowanie_wizyt_domowych_pielegniarki`
- Loginy: `client/client`, `nurse/nurse`

## Opis algorytmu

Algorytm dobiera zespol pielegniarski do typu wizyty, wymaganej trudnosci, uprawnien i wolnego okna czasowego. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
