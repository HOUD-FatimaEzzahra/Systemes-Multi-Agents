
# Plateforme de vente-achat des services
## Description du projet
Ce projet consiste à développer une plateforme d'achat-vente de services en utilisant une architecture multi-agents. L'architecture est basée sur JADE (Java Agent Development Framework) et permet la communication entre plusieurs agents vendeurs et un agent acheteur. Chaque vendeur publie ses services et le vendeur qui propose le meilleur prix est sélectionné par l'acheteur.

Le langage ACL (Agent Communication Language) est utilisé pour la communication entre les différents agents. Il permet une communication fiable et cohérente entre les agents, ce qui facilite le processus d'achat-vente.


## Structure du code
Voici la description de chaque classe mentionnée dans la structure de code proposée :
- BuyerAgent : cette classe implémente l'agent acheteur qui sélectionne le service avec le meilleur prix. Elle peut contenir des méthodes pour communiquer avec les agents vendeurs et pour prendre des décisions d'achat.
- SellerAgent : cette classe implémente les agents vendeurs qui publient leurs services. Elle peut contenir des méthodes pour publier des services, répondre aux requêtes d'achat de l'agent acheteur et ajuster les prix des services en fonction de la demande.
- MainContainer : cette classe représente le conteneur principal de JADE qui peut contenir plusieurs conteneurs secondaires (instances de SimpleContainer). Le conteneur principal gère les communications inter-conteneurs et peut être utilisé pour gérer l'ensemble du système multi-agents.
- SimpleContainer : cette classe représente un conteneur secondaire de JADE qui peut contenir un ou plusieurs agents. Les instances de SimpleContainer sont créées à partir du conteneur principal et sont utilisées pour exécuter des groupes d'agents vendeurs ou l'agent acheteur.
