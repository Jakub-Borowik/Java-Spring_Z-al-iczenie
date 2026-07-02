# Przydzial_Serwisantow_Fotowoltaiki

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/solar-services`
- Panel roli wyzszej: `/technician/solar-services`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/solar-services`
- Baza PostgreSQL: `przydzial_serwisantow_fotowoltaiki`
- Loginy: `client/client`, `technician/technician`

## Opis algorytmu

Algorytm przydziela serwisanta po typie usterki, liczbie paneli, uprawnieniach i czasie. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
