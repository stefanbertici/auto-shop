# Laboratorul 3-5

Fiecare student își alege o problemă. Folosiți arhitectură stratificată cu domain, UI, service, repository. Tema se întinde pe 3 săptămâni (3 iterații).

1. Prima iterație (săptămâna 3): toate funcționalitățile CRUD, interfață de consolă, excepții. 
2. A doua iterație (săptămâna 4): minim 2 funcționalități non-CRUD, repository generic.
3. A treia iterație (săptămâna 5): toate funcționalitățile, test coverage de minim 90%, validări folosind clase proprii de excepții.  

---

1. **Farmacie Online**   
    1.1. CRUD medicament: id, nume, producător, preț, necesită rețetă. Prețul să fie strict pozitiv.  
    1.2. CRUD card client: id, nume, prenume, CNP, data nașterii (`dd.mm.yyyy`), data înregistrării (`dd.mm.yyyy`). CNP-ul trebuie să fie unic.  
    1.3. CRUD tranzacție:  id, id_medicament, id_card_client (poate fi nul), nr_bucăți, data și ora (`dd.mm.yyyy HH:mm`). Dacă există un card client, atunci aplicați o reducere de `10%` dacă medicamentul nu necesită rețetă și de `15%` dacă necesită. Se tipărește prețul plătit și reducerile acordate.  
    1.4. Căutare medicamente și clienți. Căutare full text.  
    1.5. Afișarea tuturor tranzacțiilor dintr-un interval de zile dat.  
    1.6. Afișarea medicamentelor ordonate descrescător după numărul de vânzări.  
    1.7. Afișarea cardurilor client ordonate descrescător după valoarea reducerilor obținute.  
    1.8. Ștergerea tuturor tranzacțiilor dintr-un anumit interval de zile.  
    1.9. Scumpirea cu un procentaj dat a tuturor medicamentelor cu prețul mai mic decât o valoare dată.  


2. **Cinema**   
    2.1. CRUD film: id, titlu, an apariție, preț bilet, în program. Prețul să fie strict pozitiv.  
    2.2. CRUD card client: id, nume, prenume, CNP, data nașterii (`dd.mm.yyyy`), data înregistrării (`dd.mm.yyyy`), puncte acumulate. CNP-ul trebuie să fie unic.  
    2.3. CRUD rezervare: id, id_film, id_card_client (poate fi nul), data și ora (`dd.mm.yyyy HH:mm`). Clientul acumulează pe card `10%` (parte întreagă) din prețul filmului. Se tipărește numărul total de puncte de pe card. Rezervarea se poate face doar dacă filmul este încă în program.  
    2.4. Căutare filme și clienți. Căutare full text.  
    2.5. Afișarea tuturor rezervărilor dintr-un interval de ore dat, indiferent de zi.  
    2.6. Afișarea filmelor ordonate descrescător după numărul de rezervări.  
    2.7. Afișarea cardurilor client ordonate descrescător după numărul de puncte de pe card.  
    2.8. Ștergerea tuturor rezervărilor dintr-un anumit interval de zile.  
    2.9. Incrementarea cu o valoare dată a punctelor de pe toate cardurile a căror zi de naștere se află într-un interval dat.   


3. **Service auto**  
    3.1. CRUD mașină: id, model, an achiziție, nr. km, în garanție. Km și anul achiziției să fie strict pozitivi.  
    3.2. CRUD card client: id, nume, prenume, CNP, data nașterii (`dd.mm.yyyy`), data înregistrării (`dd.mm.yyyy`). CNP-ul trebuie să fie unic.  
    3.3. CRUD tranzacție:  id, id_mașină, id_card_client (poate fi nul), sumă piese, sumă manoperă, data și ora (`dd.mm.yyyy HH:mm`). Dacă există un card client, atunci aplicați o reducere de `10%` pentru manoperă. Dacă mașina este în garanție, atunci piesele sunt gratis. Se tipărește prețul plătit și reducerile acordate.  
    3.4. Căutare mașini și clienți. Căutare full text.  
    3.5. Afișarea tuturor tranzacțiilor cu suma cuprinsă într-un interval dat.  
    3.6. Afișarea mașinilor  ordonate descrescător după suma obținută pe manoperă.  
    3.7. Afișarea cardurilor client ordonate descrescător după valoarea reducerilor obținute.  
    3.8. Ștergerea tuturor tranzacțiilor dintr-un anumit interval de zile.  
    3.9. Actualizarea garanției la fiecare mașină: o mașină este în garanție dacă și numai dacă are maxim `3` ani de la achiziție și maxim `60 000` de km.  
