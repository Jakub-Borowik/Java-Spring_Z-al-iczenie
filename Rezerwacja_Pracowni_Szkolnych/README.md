# Rezerwacja_Pracowni_Szkolnych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/school-labs`
- Panel roli wyzszej: `/teacher/school-labs`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/school-labs`
- Baza PostgreSQL: `rezerwacja_pracowni_szkolnych`
- Loginy: `client/client`, `teacher/teacher`

## Opis algorytmu

Algorytm wybiera pracownie po typie zajec, liczbie uczniow, standardzie i dostepnym terminie. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
