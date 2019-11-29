Configuratie van een EdgeRouter Lite 3 voor KPN aansluitingen zonder telefonie (VoIP).
We gaan er vanuit dat:
- op ethernet poort 0 de NTU (glasvezelmodem) wordt aangesloten.
- op ethernet poort 1 het interne netwerk wordt aangesloten.
- ethernet poort 2 wordt in deze configuratie niet gebruikt.

TIP: Installeer WinSCP (https://winscp.net) op jouw PC om eenvoudig bestanden naar de EdgeRouter te kunnen kopiëren en de configuratie uit te voeren.

Installatie instructies met WinSCP:

Kopieer de bestanden uit deze zip naar de directory /config/user-data op de EdgeRouter.

Klik met de rechter muisknop op het bestand KPN-Setup in de directory /config/user-data en kies voor "Eigenschappen".
Geef de "Eigenaar" rechten om KPN-Setup op te starten door een vinkje bij X te plaatsen (Octaal = 0744) en klik op OK.
Geef de opdracht sudo su -c /config/user-data/KPN-Setup in terminal binnen WinSCP.
De configuratie zal worden uitgevoerd waarna de EdgeRouter opnieuw wordt opgestart.


Installatie instructies zonder WinSCP:

Kopieer de bestanden uit deze zip naar de directory /config/user-data op de EdgeRouter.

Login op de EdgeRouter via een telnet of ssh sessie.
Wijzig de rechten van het bestand KPN-Setup: chmod 744 /config/user-data/KPN-Setup
Start de configuratie van de EdgeRouter als root: sudo su -c /config/user-data/KPN-Setup
De configuratie zal worden uitgevoerd waarna de EdgeRouter opnieuw wordt opgestart.

Login op de EdgeRouter op https://192.168.2.254 en start deze nogmaals opnieuw op met de restart in het System scherm.
 
LET OP: De huidige configuratie zal overschreven worden.

Mocht je alleen de rfc3442-classless-routes willen installeren, volg dan de onderstaande instructies.

Kopieer het bestand copy-rfc3442-classless-routes naar de directory /config/scripts/post-config.d
Kopieer het bestand rfc3442-classless-routes naar de directory /config/user-data
Login op de EdgeRouter via een telnet of ssh sessie.
Wijzig de rechten van het bestand copy-rfc3442-classless-routes: chmod 744 /config/scripts/post-config.d/copy-rfc3442-classless-routes
Reboot de EdgeRouter
