package demoMod;

import Helpers.ModHelper;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.*;
import characters.MonkeyKing;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import monsters.KingMonkey;
import monsters.KingYurong;
import monsters.MonkeyKingPhantom;
import pathes.AbstractCardEnum;
import pathes.ThmodClassEnum;
import relics.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class MonkeyKingMod implements  PostInitializeSubscriber,EditKeywordsSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, AddAudioSubscriber, SetUnlocksSubscriber {
    //private static final String MOD_BADGE = "img/charSelect/MonkeyKingButton.png";

    private static final String ATTACK_CC = "img/512/bg_attack_monkeyking.png";
    private static final String SKILL_CC = "img/512/bg_skill_monkeyking.png";
    private static final String POWER_CC = "img/512/bg_power_monkeyking.png";
    private static final String ENERGY_ORB_CC = "img/512/card_monkeyking_orb.png";
    public static final String CARD_ENERGY_ORB = "img/512/card_small_orb.png";

    private static final String ATTACK_CC_PORTRAIT = "img/1024/bg_attack_monkeyking.png";
    private static final String SKILL_CC_PORTRAIT = "img/1024/bg_skill_monkeyking.png";
    private static final String POWER_CC_PORTRAIT = "img/1024/bg_power_monkeyking.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/card_monkeyking_orb.png";
    

    private static final String MY_CHARACTER_BUTTON = "img/charSelect/MonkeyKingButton.png";
    private static final String MARISA_PORTRAIT = "img/charSelect/MonkeyKingBG.png";
    public static final Color OrangeRed = CardHelper.getColor(212, 126, 107);
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();

    public MonkeyKingMod() {

        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.MonkeyKing_RED,
                OrangeRed, OrangeRed, OrangeRed, OrangeRed, OrangeRed, OrangeRed, OrangeRed,
                ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT,
                SKILL_CC_PORTRAIT,POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new MonkeyKing("MonkeyKing"), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, ThmodClassEnum.MonkeyKing_CLASS);
    }

    public static void initialize() {
        new MonkeyKingMod();
    }

    @Override
    public void receiveEditCards() {

        BaseMod.addCard(new Attack_MonkeyKing());
        BaseMod.addCard(new Defend_MonkeyKing());
        BaseMod.addCard(new Flaming_Storm());
        BaseMod.addCard(new Anger());
        BaseMod.addCard(new FireBlitz());
        BaseMod.addCard(new FireTouch());
        BaseMod.addCard(new BoxingCard());
        BaseMod.addCard(new RisingDragonSlash());
        BaseMod.addCard(new HeavySlash(0));
        BaseMod.addCard(new FireMagicSlash());
        BaseMod.addCard(new ShiningPillar());
        BaseMod.addCard(new ChargeSlash());
        BaseMod.addCard(new SomersaultCloud());
        BaseMod.addCard(new ExorcismClaw());
        BaseMod.addCard(new CrackingFist());
        BaseMod.addCard(new FieryEyes());
        BaseMod.addCard(new FlamingFlash());
        BaseMod.addCard(new BloodThirsty());
        BaseMod.addCard(new Dodge());
        BaseMod.addCard(new StepFloor());
        BaseMod.addCard(new HelpfulHairs());
        BaseMod.addCard(new SeventyTwoSlashes());
        BaseMod.addCard(new FlameStrike());
        BaseMod.addCard(new FireBarrier());
        BaseMod.addCard(new FlameExtraction());
        BaseMod.addCard(new FlameCyclone());
        BaseMod.addCard(new Impenetrable());
        BaseMod.addCard(new GodDestroyingStrikeIII());
        BaseMod.addCard(new GodDestroyingStrikeII());
        BaseMod.addCard(new GodDestroyingStrikeI());
        BaseMod.addCard(new FireApeStrikes());
        BaseMod.addCard(new AttackMirage());
        BaseMod.addCard(new DefenceMirage());
        BaseMod.addCard(new SurgeBoxing());
        BaseMod.addCard(new Boost());
        BaseMod.addCard(new Ignition());
        BaseMod.addCard(new FlamesBurst());
        BaseMod.addCard(new DoubleBoxing());
        BaseMod.addCard(new PerfectedBoxing());
        BaseMod.addCard(new HeavyBoxing());
        BaseMod.addCard(new ChaosBoxing());
        BaseMod.addCard(new YinYangBoxing());
        BaseMod.addCard(new GrowingBoxing());
        BaseMod.addCard(new InHighSpirits());
        BaseMod.addCard(new BurnBlood());
        BaseMod.addCard(new RisingDragonSlashBlood());
        BaseMod.addCard(new FlamingStormBlood());
        BaseMod.addCard(new FireMagicSlashBlood());
        BaseMod.addCard(new BurningBloodBoxing());
        BaseMod.addCard(new ExtraPhantom());
        BaseMod.addCard(new PhantomStab());
        BaseMod.addCard(new PhantomSweep());
        BaseMod.addCard(new PhantomSlash());
        BaseMod.addCard(new PhantomAssault());
        BaseMod.addCard(new ControlPhantom());
        BaseMod.addCard(new PhantomDestruction());
        BaseMod.addCard(new PhantomShield());
        BaseMod.addCard(new PhantomProtection());
        BaseMod.addCard(new Phantom());
        BaseMod.addCard(new PhantomKindle());
        BaseMod.addCard(new Vellus());
        BaseMod.addCard(new Overawe());
        BaseMod.addCard(new PhantomBoxing());
        BaseMod.addCard(new ExtraPhantomBoxing());
        BaseMod.addCard(new Agglomerate());

    }






    @Override
    public void receiveEditStrings() {

        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        } else {
            lang = "ENG";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "zaomengxiyou/localization/" +lang +"/CardsStrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "zaomengxiyou/localization/" + lang + "/CharactersString.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "zaomengxiyou/localization/" + lang + "/RelicsStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "zaomengxiyou/localization/" + lang + "/PowerStrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "zaomengxiyou/localization/" + lang + "/MonsterStrings.json");
        //BaseMod.loadCustomStringsFile(Keyword.class, "zaomengxiyou/localization/" +lang +"/KeyWordsStrings.json");
        //BaseMod.loadCustomStringsFile(PotionStrings.class, "zaomengxiyou/localization/" + lang + "/potions.json");
        //BaseMod.loadCustomStringsFile(UIStrings.class, "zaomengxiyou/localization/" + lang + "/uiString.json");
    }



    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BloodthirstyFireEyes(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelicToCustomPool(new Bracelets(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelic(new FuriousRing(), RelicType.SHARED);
        BaseMod.addRelic(new GreedRing(), RelicType.SHARED);
        BaseMod.addRelic(new ObsessionRing(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new EmergingBoxingGloves(), AbstractCardEnum.MonkeyKing_RED);
    }



    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        } else {
            lang = "ENG";
        }

        String json = Gdx.files.internal("zaomengxiyou/localization/" +lang +"/KeyWordsStrings.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("dreaming_journey_to_the_west", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(ModHelper.MakePath("fire_demon_slash"), "sound/fire_demon_slash.ogg");
    }

    @Override
    public void receiveSetUnlocks() {

    }
    public void receivePostInitialize() {//Register for monsters, events
        initializeMonsters();
    }
    private void initializeMonsters() {
        BaseMod.addMonster("dreaming_journey_to_the_west:KingYurong",KingYurong.NAME,() -> new MonsterGroup(new AbstractMonster[] { new KingYurong() }));
        BaseMod.addEliteEncounter("Exordium", new MonsterInfo(KingYurong.ID, 0.2F));
        BaseMod.addMonster("dreaming_journey_to_the_west:KingMonkey", KingMonkey.NAME,() -> new MonsterGroup(new AbstractMonster[] { new KingMonkey() }));
        BaseMod.addEliteEncounter("Exordium", new MonsterInfo(KingMonkey.ID, 0.2F));
        BaseMod.addMonster("dreaming_journey_to_the_west:MonkeyKingPhantom", MonkeyKingPhantom.NAME,() -> new MonsterGroup(new AbstractMonster[] { new MonkeyKingPhantom() }));
    }
    @SpireEnum
    public static AbstractCard.CardTags BOXING;

    class Keywords {
        Keyword[] keywords;
    }
}
