# Przydzial_Terapeutow

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/therapy`
- Panel roli wyzszej: `/therapist/therapy`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/therapy`
- Baza PostgreSQL: `przydzial_terapeutow`
- Loginy: `client/client`, `therapist/therapist`

## Opis algorytmu

Algorytm dobiera terapeute po typie sesji, trudnosci, doswiadczeniu i wolnym terminie. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
