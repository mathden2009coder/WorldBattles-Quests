# WorldBattles Quests — Forge 1.20.1

Projet Forge 1.20.1 / Java 17 / GeckoLib 4.4.9.

La version 0.1.11 ajoute Maylline, la Gardienne des Archives, en conservant Jarvis, Mira et Dr. K. Driver.
Son modèle assis utilise les animations idle, reading et talking fournies avec les fichiers Blockbench.

## Faire apparaître Maylline

En jeu, avec les permissions nécessaires :

`/summon worldbattles_quests:maylline ~ ~ ~`

Les commandes d'administration `/wbquests remove` et `/wbquests turn` acceptent maintenant aussi l'identifiant `maylline`.

## Quêtes

Maylline possède une chaîne indépendante de 20 quêtes de livraison autour des livres, cartes, encres et reliques.
Sa progression et ses récompenses sont stockées séparément des autres PNJ.

## Compilation

Le projet cible Forge 1.20.1-47.4.10 et Java 17. Les ressources du modèle sont extraites automatiquement depuis `assets/maylline-assets.zip` pendant la compilation Gradle.

GitHub Actions produit le JAR `WorldBattlesQuests-0.1.11.jar`.
