# Rendu_Projet_PVZ_Java Antonin Dudragne

J'ai mis très peu de commentaires dans le code, car la structure de l'API est parfaitement classique.
Seules les parties un peu plus complexes ou non standard sont commentées.

## Parties réalisées

- L'intégralité des parties obligatoires ont été réalisées et sont fonctionnelles.
- L'architecture du projet suit l'architecture classique d'un BackEnd comme vu en cours, 
c'est-à-dire Controller, DTO et DAO, services et models.
- Les tests unitaires sont présents et tous valides. Utilisation d'H2 pour créer une DB spécifique aux tests.
- Utilisation également de Mockito pour simuler les appels à la DB et tester les services.
- Utilisation de jakarta pour les tests d'intégration.

J'ai réalisé quelques ajouts en termes de gestion des exceptions :
- Ajout de la gestion des exceptions notamment de création/suppression 
de Zombie en fonction des maps, ce que les tests du FrontEnd ne prenaient pas en compte.
- Vérification du format des données et de leur existence pour éviter des
  NullPointerException.
- Ajout de messages d'erreurs plus précis pour les exceptions.


## Modification notables

Pour faire fonctionner le projet, j'ai dû modifier quelques parties du code proposé dans le README du FrontEnd, 
notamment le WebConfig et le web.xml, idem pour le CorsFilter, car il y avait toujours
des problèmes de CORS. Il y a également des doublons de dossier image mais au pluriel, 
car il y avait des erreurs à cause de la manière dont les images étaient récupérées.

Merci pour votre temps.