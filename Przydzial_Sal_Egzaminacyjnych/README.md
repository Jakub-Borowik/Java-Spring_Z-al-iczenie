# Przydzial_Sal_Egzaminacyjnych

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/exam-rooms`
- Panel roli wyzszej: `/coordinator/exam-rooms`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/exam-rooms`
- Baza PostgreSQL: `przydzial_sal_egzaminacyjnych`
- Loginy: `client/client`, `coordinator/coordinator`

## Opis algorytmu

Algorytm wybiera sale po typie egzaminu, liczbie osob, standardzie i dostepnosci. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
