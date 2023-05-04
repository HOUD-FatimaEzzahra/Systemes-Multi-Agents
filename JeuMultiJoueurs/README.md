# TP 1 : Jeu multi-joueurs de détermination d'un nombre magique secret
Ce projet est le premier TP du module "Systèmes Multi-Agents". Il consiste en la création d'un jeu multi-joueurs basé sur les systèmes multi-agents. Le but du jeu est de déterminer un nombre magique secret compris entre 0 et 100, généré aléatoirement par un agent serveur. Les joueurs vont tenter de trouver ce nombre en soumettant des tentatives et en recevant des indications s'ils sont en dessus ou en dessous du nombre magique.

## Agents
Le système se compose de trois agents :
- Agent serveur : génère le nombre magique et reçoit les tentatives des joueurs. Il envoie un message à tous les joueurs pour informer du nombre magique trouvé et arrêter le jeu si un joueur a trouvé le nombre magique. Sinon, il indique au joueur s'il est en dessus ou en dessous du nombre magique.
- Agent joueur 1 : possède une interface graphique JavaFX pour saisir un nombre, l'envoyer au serveur et afficher les messages reçus de l'agent serveur.
- Agent joueur 2 : le même que l'agent joueur 1, jouant contre lui et possédant également une interface graphique JavaFX.
