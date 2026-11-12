# WorldBattles Quests — Forge 1.20.1

Projet Forge 1.20.1 / Forge 47.4.10 / Java 17 / GeckoLib 4.4.9.

La version 0.1.12 conserve Jarvis, le Traqueur et Maylline. Mira (Explorer) est retirée.
Chaque PNJ raconte progressivement une partie de l'histoire des Freelands lorsqu'une quête est validée. La fenêtre affiche aussi le montant réellement ajouté au solde Shop.

Les chaînes et récompenses de Jarvis et du Traqueur conservent leurs index, quantités et montants; seul le shulker (End) du Traqueur est remplacé par une araignée des cavernes. Maylline a désormais une conclusion réalisable sans aller dans l'End.

## Faire apparaître les PNJ

`/summon worldbattles_quests:blacksmith ~ ~ ~`

`/summon worldbattles_quests:hunter ~ ~ ~`

`/summon worldbattles_quests:maylline ~ ~ ~`

Les commandes d'administration `/wbquests remove` et `/wbquests turn` acceptent ces trois identifiants.

## Progression

Le stockage `worldbattles_quests_progress` est conservé tel quel. Les clés des chaînes `blacksmith_XX`, `hunter_XX` et `maylline_XX` restent inchangées; aucune remise à zéro n'est appliquée aux sauvegardes des joueurs.

## Compilation

Le projet cible Forge 1.20.1-47.4.10 et Java 17. Les ressources du modèle Maylline sont extraites automatiquement depuis `assets/maylline-assets.zip` pendant la compilation Gradle. GitHub Actions produit le JAR.
