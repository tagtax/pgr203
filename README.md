<h1>Eksamen Avansert Java PGR 203 Kandidatnr: 9001</h1>
Link: https://github.com/tagtax/pgr203
<br> Video: https://drive.google.com/file/d/1C7MRMhs4ukha00CSXPyfnClzenFLFHZb/view?usp=sharing
<br> Github actions: https://github.com/tagtax/pgr203/runs/1986003689?check_suite_focus=true

<h1>Hvordan kjøre dette programmet</h1>
1. mvn package <br>
2. java -jar target/pg203k9001-1.0-SNAPSHOT.jar <br>
3. open i browser 127.0.0.1:8080 <br>

<h1>Funksjonalitet</h1>
Opprett prosjektmedlem med navn og email - ja <br>
Liste prosjektmedlemmer - ja <br>
Opprett ny prosjektoppgave med navn og status -ja <br>
Tildel oppgave til prosjektmedlemmer -ja <br>
Liste opp prosjektoppgaver, inkludert status og tildelte prosjektmedlemmer -ja <br>
Endre oppgavestatus - ja <br>
Filtrere oppgaver per prosjektmedlem og status -ja <br>

<h1>Designbeskrivelse</h1>
Fronten er brukt av vedlegg til eksamen, eget HTML kode med inkludert Maven script. 

<h1>Egenevaluering</h1>
Alt filene jobbet i lokal. Opplaste all via github – private men det skulle være github classroom.
Har også hatt store problemer med å linke fra den repository til classroom. Og ikke fått invitasjon/TA til Kristiania github. Derfor laget egne classroom med invitasjon til repository.

<h1>Hva jeg lærte underveis</h1>
Har lært mye om oppbygning av avanserte SQL table systemer. 
ut i fra oppgaver så vil jeg ta i bruk et løsningen som hete Many to Many relationships.
og takk til https://www.youtube.com/watch?v=1eUn6lsZ7c4 med inspirasjon av data table.
Løsningen ble 3 tables som er til member, project og progress/jointable.
har også prøv til å lage 4 table for bonuspoeng, som status vil ha egne table. Det skal ser ut som på https://dbdiagram.io/d/603647f5fcdcb6230b2148c9 .

<h1>Hva jeg skulle hadde ønske gjort annerledes</h1>
Skulle ha gjort andeleveres er å sette litt mer tid på designer til fronten kanskje implement bootstrap eller noe det virker finere enn er bare på html. 
