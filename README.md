Eksamen Avansert Java PGR 203 Kandidatnr: 9001
Link:
Video: https://drive.google.com/file/d/1C7MRMhs4ukha00CSXPyfnClzenFLFHZb/view?usp=sharing
Github actions: 

Hvordan kjøre dette programmet
1. mvn package
2. Run java file:  src/main/java/com/pgr203/k9001/PGR203ServerService.java
3. open i browser 127.0.0.1:8080 

Funksjonalitet
Opprett prosjektmedlem med navn og email - ja
Liste prosjektmedlemmer - ja
Opprett ny prosjektoppgave med navn og status -ja 
Tildel oppgave til prosjektmedlemmer -ja
Liste opp prosjektoppgaver, inkludert status og tildelte prosjektmedlemmer -ja
Endre oppgavestatus - ja
Filtrere oppgaver per prosjektmedlem og status -ja

Designbeskrivelse
Fronten er brukt av vedlegg til eksamen, eget HTML kode med inkludert Maven script. 

Egenevaluering
Alt filene jobbet i lokal. Opplaste all via github – private men det skulle være github classroom.
Har også hatt store problemer med å linke fra den repository til classroom. Og ikke fått invitasjon/TA til Kristiania github. Derfor laget egne classroom med invitasjon til repository.

Hva jeg lærte underveis
Har lært mye om oppbygning av avanserte SQL table systemer. 
ut i fra oppgaver så vil jeg ta i bruk et løsningen som hete Many to Many relationships.
og takk til https://www.youtube.com/watch?v=1eUn6lsZ7c4 med inspirasjon av data table.
Løsningen ble 3 tables som er til member, project og progress/jointable.
har også prøv til å lage 4 table for bonuspoeng, som status vil ha egne table. Det skal ser ut som på https://dbdiagram.io/d/603647f5fcdcb6230b2148c9 .

Hva jeg skulle hadde ønske gjort annerledes
Skulle ha gjort andeleveres er å sette litt mer tid på designer til fronten kanskje implement bootstrap eller noe det virker finere enn er bare på html. 
