package com.worldbattles.quests.quest;

import java.util.List;

/**
 * Short, friendly lore fragments revealed one at a time when a quest is claimed.
 * The three stories share the history of the Freelands and the old Lantern Road.
 */
public final class QuestLore {
    private static final String MAYLLINE_INTRO =
        "Je suis Maylline, gardienne des Archives. Les pages que je protège racontent comment les Freelands ont perdu leur ancienne route vers le monde d'où venaient les premiers voyageurs.";
    private static final String BLACKSMITH_INTRO =
        "Je suis Jarvis. Ma forge travaille encore le métal des premières maisons des Freelands. Si tu m'aides, je te raconterai pourquoi nos lanternes ne s'éteignent jamais.";
    private static final String HUNTER_INTRO =
        "On m'appelle le Traqueur. Depuis la Nuit de Brume, je surveille les sentiers autour des Freelands. Les créatures ne sont pas toutes arrivées ici par hasard.";

    private static final List<String> MAYLLINE = List.of(
        "Les anciens voyageurs suivaient une route de lanternes pour atteindre les Freelands. Chaque lumière marquait un refuge sûr entre les collines.",
        "Le premier village fut bâti près d'un grand chêne. On raconte que ses racines passaient sous les anciennes pierres de la route.",
        "Ma grand-mère copiait les cartes à la main. Elle disait qu'une carte n'indique pas seulement où aller : elle garde aussi le souvenir de ceux qui sont passés.",
        "Les Archives furent installées sous la première bibliothèque, à l'abri de la pluie et des pillards. C'est là que je protège les récits de notre peuple.",
        "Sur une vieille carte, le nom « Freelands » est écrit dans une encre bleue. Personne ne sait qui a choisi ce nom, mais il est resté.",
        "La boussole du premier cartographe pointait vers une tour qui n'existe plus. Depuis la Nuit de Brume, son aiguille tremble près des vieilles ruines.",
        "Jarvis m'a raconté que son maître forgeait des supports pour les lanternes de la route. Sans son travail, les voyageurs auraient perdu leur chemin bien plus tôt.",
        "Les livres des Archives parlent d'un soir où toutes les lanternes se sont éteintes en même temps. C'est ainsi que commença la Nuit de Brume.",
        "Les mineurs rapportèrent du lapis bleu trouvé sous les collines. Ma grand-mère l'utilisait pour colorer les sceaux qui fermaient les vieux registres.",
        "Les calamars lumineux vivent près des eaux profondes. Leur encre servait à écrire les passages importants, visibles même dans les caves sombres.",
        "Les géodes d'améthyste résonnent doucement. Les anciens pensaient que leur son aidait à retrouver les pages perdues dans les tunnels.",
        "Depuis une colline, une longue-vue permet d'apercevoir les ruines d'un ancien poste de garde. Le Traqueur affirme qu'une piste y commence.",
        "Les Endermen traversent parfois les bois de l'Overworld. Une perle qu'ils laissent derrière eux est la preuve que même les chemins lointains peuvent nous parvenir.",
        "Un journal retrouvé près du vieux poste raconte le dernier voyage d'un messager. Il transportait une page que personne n'a jamais pu remettre aux Archives.",
        "Les livres enchantés servaient autrefois à protéger les cartes de l'humidité et du feu. Celui-ci porte le sceau de la famille de ma grand-mère.",
        "Quand on place l'améthyste près des vitraux, des lignes apparaissent sur la pierre. Elles dessinent une partie du chemin qui menait au refuge.",
        "J'ai comparé les horloges des anciennes maisons : elles se sont toutes arrêtées à la même heure. C'était l'instant précis où la Nuit de Brume a commencé.",
        "Le quartz du Nether était utilisé pour graver des repères blancs sur les tunnels. Ces marques indiquaient une sortie vers les collines des Freelands.",
        "Cette obsidienne pleureuse vient d'un portail abandonné. Il ne mène plus nulle part, mais son cadre porte encore le symbole de la première route.",
        "Avec ce dernier lutrin, je peux replacer la page maîtresse du registre. La route n'a pas disparu : elle attend quelque part dans les Freelands, au-delà des anciennes lanternes. Je vais enfin pouvoir guider les prochains voyageurs."
    );

    private static final List<String> BLACKSMITH = List.of(
        "J'ai appris la forge auprès de maître Orvan, qui fabriquait les ferrures des premières maisons des Freelands.",
        "Le charbon que tu m'apportes servait à garder le feu allumé pendant les longues nuits de construction.",
        "Le minerai de fer venait des collines du nord. Les premiers mineurs y avaient trouvé des galeries déjà creusées.",
        "Mon premier lingot fut transformé en clou pour la porte des Archives. Maylline dit qu'il tient encore debout.",
        "J'ai forgé ma première pioche pour ouvrir un passage jusqu'à la rivière souterraine.",
        "La première épée n'était pas destinée à la guerre : elle protégeait les ouvriers des créatures qui rôdaient dans les mines.",
        "Le cuivre servait à couvrir les toits et à fabriquer les conduits des lanternes. Il devient vert avec le temps, mais il tient bon.",
        "L'or était réservé aux outils des cartographes et aux petits sceaux des messagers.",
        "Les blocs de fer formaient les piliers du vieux pont. Il reliait autrefois le village à la route des lanternes.",
        "Les premiers diamants furent trouvés au fond d'une mine abandonnée. Mon maître les a gardés pour réparer les outils les plus précieux.",
        "Une pioche en diamant permit de dégager la salle où Maylline conserve aujourd'hui les cartes anciennes.",
        "J'ai forgé cette épée pour le premier garde du pont. Il disait que le courage était plus important que le métal.",
        "L'obsidienne venait d'un portail effondré. J'ai appris à la tailler sans fissurer les morceaux encore utilisables.",
        "L'enclume de la forge porte une marque presque effacée : celle de maître Orvan, le premier artisan du village.",
        "Les blocs d'or décoraient la salle des messagers. Ils reflétaient la lumière des lanternes quand la brume arrivait.",
        "Les émeraudes servaient à payer les bâtisseurs venus d'autres villages. Elles ont aidé les Freelands à grandir.",
        "Le vieux pont avait deux pierres de diamant au centre. Elles indiquaient l'endroit où les voyageurs devaient changer de direction.",
        "Le Netherite est solide, mais mon maître disait qu'aucun métal ne peut remplacer une bonne route et des amis fiables.",
        "J'ai réparé l'outil de maître Orvan avec un lingot de Netherite. Il m'a alors confié le secret de la forge : fabriquer pour protéger, jamais pour dominer.",
        "Le dernier bloc est destiné au socle de la grande lanterne. Quand Maylline aura retrouvé la page maîtresse, je pourrai rallumer le signal qui guidait les voyageurs vers les Freelands."
    );

    private static final List<String> HUNTER = List.of(
        "Avant de devenir Traqueur, j'étudiais les animaux des plaines. Je voulais comprendre pourquoi certains fuyaient les vieilles ruines.",
        "Les zombies apparaissent surtout quand les lanternes des sentiers sont abandonnées. Je patrouille donc près des maisons isolées.",
        "Les squelettes évitent les endroits éclairés. Ils se rassemblent près des panneaux qui indiquaient autrefois la route des Freelands.",
        "Les araignées tissent leurs toiles dans les tours désertées. Plusieurs de ces tours servaient de relais aux messagers.",
        "Les creepers ont endommagé une partie du vieux pont. Jarvis a encore les plans pour le réparer.",
        "Les noyés gardent parfois des objets tombés dans les rivières. J'y ai trouvé une boucle portant le sceau des Archives.",
        "Les Endermen traversent les forêts de l'Overworld sans prévenir. Ils ne sont pas la cause de la Brume, mais ils semblent attirés par les anciennes pierres.",
        "Les sorcières connaissent des chemins que les cartes ont oubliés. Je préfère les observer de loin plutôt que de les sous-estimer.",
        "Les pillards ont récupéré des panneaux de l'ancienne route. Je les ai vus les transporter vers un avant-poste abandonné.",
        "Les fantômes apparaissent quand on néglige son sommeil. Les voyageurs de la route racontaient qu'ils suivaient les lanternes éteintes.",
        "Les slimes vivent dans les cavernes humides sous le village. Leurs traces m'ont aidé à retrouver une galerie oubliée.",
        "Les Blazes protègent certaines forteresses du Nether. Leurs flammes éclairent les anciens passages gravés dans la roche.",
        "Un Ghast a détruit un pont de pierre dans le Nether. Heureusement, les marques de quartz indiquaient encore le chemin du retour.",
        "Les Hoglins sont dangereux, mais ils restent dans les forêts carmin. Les anciens chasseurs connaissaient des sentiers pour les contourner.",
        "Les araignées des cavernes occupent une mine abandonnée. Derrière leurs toiles, j'ai retrouvé un vieux panneau de la route.",
        "Les Gardiens protègent les monuments sous-marins. Un ancien relais se trouvait près de l'un d'eux, au bord de l'océan.",
        "Les ravageurs accompagnent les raids des pillards. Leur présence près des Freelands confirme que quelqu'un cherche encore à contrôler les routes.",
        "Les évocateurs utilisent une magie étrange. Dans leurs coffres, j'ai trouvé des notes parlant d'une lumière bleue au milieu de la Brume.",
        "Les squelettes Wither gardent les forteresses du Nether. Leurs os sombres rappellent les histoires que Maylline conserve dans ses vieux livres.",
        "Même les petits cubes de magma peuvent brûler un sentier entier. La route est presque prête à être surveillée de nouveau : Maylline possède désormais la page maîtresse, et je connais le chemin sûr jusqu'à la grande lanterne."
    );

    public static String intro(String npcId) {
        return switch (npcId) {
            case "blacksmith" -> BLACKSMITH_INTRO;
            case "hunter" -> HUNTER_INTRO;
            case "maylline" -> MAYLLINE_INTRO;
            default -> "Les Freelands gardent encore bien des secrets. Chaque chapitre nous rapproche de leur histoire.";
        };
    }

    public static String chapter(String npcId, int oneBasedIndex) {
        List<String> chapters = switch (npcId) {
            case "blacksmith" -> BLACKSMITH;
            case "hunter" -> HUNTER;
            case "maylline" -> MAYLLINE;
            default -> List.of();
        };
        int index = oneBasedIndex - 1;
        return index >= 0 && index < chapters.size() ? chapters.get(index) : "";
    }

    private QuestLore() {}
}
