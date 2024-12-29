package demoMod;

import Helpers.ModHelper;
import basemod.BaseMod;
import basemod.IUIElement;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.*;
import characters.MonkeyKing;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import events.act1.LoongPalace;
import events.act1.LoongPalaceNotImage;
import monsters.MonkeyKingPhantom;
import monsters.act1.*;
import monsters.act1.boss.*;
import monsters.act2.Judge;
import monsters.act2.Lamp;
import monsters.act2.QinGuangKing;
import monsters.act2.UAV;
import monsters.act2.boss.Gear;
import monsters.act2.boss.WheelTurningKing;
import monsters.act3.TheDeifiedDog;
import monsters.act3.YangJian;
import monsters.act4.*;
import pathes.AbstractCardEnum;
import pathes.ThmodClassEnum;
import relics.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import static com.megacrit.cardcrawl.core.Settings.UI_LERP_SPEED;
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
    public static Texture zmxy1bg;
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    public static boolean act1boss = false;
    public static boolean act2boss = false;
    public static boolean act3boss = false;
    public static boolean act4boss = true;
    public static SpireConfig MonkeyKingSpireConfig;
    public static String modID = "dreaming_journey_to_the_west";
    public static String FILE_NAME = "MonkeyKingModConfig";
    public static ModPanel settingsPanel;
    public static String[] CONFIG_TEXT;


    public MonkeyKingMod() {

        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.MonkeyKing_RED,
                OrangeRed, OrangeRed, OrangeRed, OrangeRed, OrangeRed, OrangeRed, OrangeRed,
                ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT,
                SKILL_CC_PORTRAIT,POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
        Properties MonkeyKingModDefaultConfig = new Properties();
        MonkeyKingModDefaultConfig.setProperty("TheBottomBoss100%fromMod",Boolean.toString(act1boss));
        MonkeyKingModDefaultConfig.setProperty("TheCityBoss100%fromMod"  ,Boolean.toString(act2boss));
        MonkeyKingModDefaultConfig.setProperty("TheBeyondBoss100%fromMod",Boolean.toString(act3boss));
        MonkeyKingModDefaultConfig.setProperty("TheEndingBoss100%fromMod",Boolean.toString(act4boss));
        try {
            MonkeyKingSpireConfig = new SpireConfig(modID,FILE_NAME,MonkeyKingModDefaultConfig);
            act1boss = MonkeyKingSpireConfig.getBool("TheBottomBoss100%fromMod");
            act2boss = MonkeyKingSpireConfig.getBool("TheCityBoss100%fromMod");
            act3boss = MonkeyKingSpireConfig.getBool("TheBeyondBoss100%fromMod");
            act4boss = MonkeyKingSpireConfig.getBool("TheEndingBoss100%fromMod");
        } catch (IOException e) {
            BaseMod.logger.info("Monkey King Mod SpireConfig initialization failed:");
            e.printStackTrace();
        }

    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new MonkeyKing("MonkeyKing"), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, ThmodClassEnum.MonkeyKing_CLASS);
    }

    public static void initialize() {
        new MonkeyKingMod();
        zmxy1bg = ImageMaster.loadImage("img/scene/zmxy1.png");

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
        BaseMod.addCard(new ConcentrateEnergy());
        BaseMod.addCard(new Vellus());
        BaseMod.addCard(new Overawe());
        BaseMod.addCard(new PhantomBoxing());
        BaseMod.addCard(new ExtraPhantomBoxing());
        BaseMod.addCard(new Agglomerate());
        BaseMod.addCard(new BloodApeBoxing());
        BaseMod.addCard(new SpiralStrike());
        BaseMod.addCard(new DodgeTraining());
        BaseMod.addCard(new FullOfEnergy());
        BaseMod.addCard(new BothOffensiveAndDefensive());
        BaseMod.addCard(new RampantBlood());
        BaseMod.addCard(new FaceTheDifficulty());
        BaseMod.addCard(new Thump());
        BaseMod.addCard(new BloodDemonRoar());
        BaseMod.addCard(new BlownSand());
        BaseMod.addCard(new HelpfulHairsBamboo());
        BaseMod.addCard(new HelpfulHairsDrill());
        BaseMod.addCard(new FightingBuddhaForm());
        BaseMod.addCard(new GearCard());
        BaseMod.addCard(new BleedCard());
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
        BaseMod.loadCustomStringsFile(UIStrings.class, "zaomengxiyou/localization/" + lang + "/UIStrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, "zaomengxiyou/localization/" + lang + "/EventStrings.json");
    }



    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BloodthirstyFireEyes(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelicToCustomPool(new Bracelets(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelic(new FuriousRing(), RelicType.SHARED);
        BaseMod.addRelic(new GreedRing(), RelicType.SHARED);
        BaseMod.addRelic(new ObsessionRing(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new EmergingBoxingGloves(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelic(new BloodSirenShell(),RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new RuyiJingguBang(), AbstractCardEnum.MonkeyKing_RED);
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
    public static void initModConfigMenu() {
        float startingXPos = 350.0F;
        float settingXPos = startingXPos;
        float xSpacing = 250.0F;
        float settingYPos = 750.0F;
        float lineSpacing = 50.0F;
        ModPanel settingsPanel = new ModPanel();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("dreaming_journey_to_the_west:config");
        CONFIG_TEXT = uiStrings.TEXT;


        ModLabeledToggleButton act1bossButton = new ModLabeledToggleButton("The Act1 Boss 100% from Mod", 350.0F, settingYPos, Settings.CREAM_COLOR, FontHelper.charDescFont, act1boss, settingsPanel, label -> {},button -> {
            act1boss = button.enabled;
            try {
                MonkeyKingSpireConfig.setBool("TheBottomBoss100%fromMod", act1boss);
                MonkeyKingSpireConfig.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement((IUIElement)act1bossButton);
        settingYPos -= lineSpacing;
        ModLabeledToggleButton act2bossButton = new ModLabeledToggleButton("The Act2 Boss 100% from Mod", 350.0F, settingYPos, Settings.CREAM_COLOR, FontHelper.charDescFont, act1boss, settingsPanel, label -> {},button -> {
            act1boss = button.enabled;
            try {
                MonkeyKingSpireConfig.setBool("TheCityBoss100%fromMod", act2boss);
                MonkeyKingSpireConfig.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement((IUIElement)act2bossButton);
        settingYPos -= lineSpacing;
        ModLabeledToggleButton act3bossButton = new ModLabeledToggleButton("The Act3 Boss 100% from Mod", 350.0F, settingYPos, Settings.CREAM_COLOR, FontHelper.charDescFont, act1boss, settingsPanel, label -> {},button -> {
            act1boss = button.enabled;
            try {
                MonkeyKingSpireConfig.setBool("TheBeyondBoss100%fromMod", act3boss);
                MonkeyKingSpireConfig.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement((IUIElement)act3bossButton);
        settingYPos -= lineSpacing;
        ModLabeledToggleButton act4bossButton = new ModLabeledToggleButton("The Act4 Boss 100% from Mod", 350.0F, settingYPos, Settings.CREAM_COLOR, FontHelper.charDescFont, act1boss, settingsPanel, label -> {},button -> {
            act1boss = button.enabled;
            try {
                MonkeyKingSpireConfig.setBool("TheEndingBoss100%fromMod", act3boss);
                MonkeyKingSpireConfig.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement((IUIElement)act4bossButton);
        Texture config_texture = ImageMaster.loadImage("img/config/monkeyking_config.png");
        BaseMod.registerModBadge(config_texture,"Monkey King Mod", "Sprinkle Pen", "BOSS Settings for each stage",settingsPanel);
    }

    private void initializeEvents(){
        //BaseMod.addEvent(LoongPalace.ID,LoongPalace.class,Exordium.ID);
        BaseMod.addEvent(LoongPalaceNotImage.ID,LoongPalaceNotImage.class,Exordium.ID);
    }

    public void receivePostInitialize() {//Register for monsters, events
        initializeMonsters();
        //BaseMod.addPower(GearAttackPower.class,GearAttackPower.POWER_ID);
        initModConfigMenu();
        initializeEvents();

    }
    private void initializeMonsters() {
        //Exordium
        BaseMod.addMonster("dreaming_journey_to_the_west:KingYurong", KingYurong.NAME,() -> new MonsterGroup(new AbstractMonster[] { new KingYurong() }));
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(KingYurong.ID, 9F));

        BaseMod.addMonster("dreaming_journey_to_the_west:KingMonkey", KingMonkey.NAME,() -> new MonsterGroup(new AbstractMonster[] { new KingMonkey() }));
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(KingMonkey.ID, 9F));

        BaseMod.addMonster("dreaming_journey_to_the_west:KingLionCamel", KingLionCamel.NAME,() -> new MonsterGroup(new AbstractMonster[] { new KingLionCamel() }));
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(KingLionCamel.ID, 9F));

        BaseMod.addMonster("dreaming_journey_to_the_west:MonkeyKingPhantom", MonkeyKingPhantom.NAME,() -> new MonsterGroup(new AbstractMonster[] { new MonkeyKingPhantom() }));
        BaseMod.addMonster(ExterminateImmortalSword.ID, ExterminateImmortalSword.NAME,() -> new MonsterGroup(new AbstractMonster[] { new ExterminateImmortalSword() }));
        BaseMod.addMonster(AnnihilateImmortalSword.ID,AnnihilateImmortalSword.NAME,() -> new MonsterGroup(new AbstractMonster[] { new AnnihilateImmortalSword() }));
        BaseMod.addMonster(TrapImmortalSword.ID,TrapImmortalSword.NAME,() -> new MonsterGroup(new AbstractMonster[] { new TrapImmortalSword() }));
        BaseMod.addMonster(SlayingImmortalSword.ID,SlayingImmortalSword.NAME,() -> new MonsterGroup(new AbstractMonster[] { new SlayingImmortalSword() }));

        BaseMod.addMonster(TheSovereignofTheTongtianSect.ID,() -> new MonsterGroup(new AbstractMonster[] { new TheSovereignofTheTongtianSect() }));
        BaseMod.addBoss("TheEnding",TheSovereignofTheTongtianSect.ID,"img/monsters/boss/tongtian.png","img/monsters/boss/tongtian_outline.png");

        BaseMod.addMonster("dreaming_journey_to_the_west:DrakeDemonKing", DrakeDemonKing.NAME,() -> new MonsterGroup(new AbstractMonster[] { new DrakeDemonKing() }));
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(DrakeDemonKing.ID, 9F));

        BaseMod.addMonster(RocDemonKing.ID,() -> new MonsterGroup(new AbstractMonster[] { new RocDemonKing() }));
        BaseMod.addBoss(Exordium.ID, RocDemonKing.ID,"img/monsters/boss/roc_boss.png","img/monsters/boss/roc_boss_outline.png");

        BaseMod.addMonster(BullDemonKingHand.ID,BullDemonKingHand.NAME,() -> new MonsterGroup(new AbstractMonster[] { new BullDemonKingHand() }));

        BaseMod.addMonster(BullDemonKing.ID,() -> new MonsterGroup(new AbstractMonster[] { new BullDemonKing() }));
        BaseMod.addBoss(Exordium.ID, BullDemonKing.ID,"img/monsters/boss/bull_boss.png","img/monsters/boss/bull_boss_outline.png");

        BaseMod.addMonster(Shark.ID,Shark.NAME,() -> new MonsterGroup(new AbstractMonster[] { new Shark() }));

        BaseMod.addMonster(SharkDemonKing.ID,() -> new MonsterGroup(new AbstractMonster[] { new SharkDemonKing() }));
        BaseMod.addBoss(Exordium.ID, SharkDemonKing.ID,"img/monsters/boss/shark_boss.png","img/monsters/boss/shark_boss_outline.png");

        BaseMod.addMonster(Loong.ID,() -> new MonsterGroup(new AbstractMonster[] { new Loong() }));
        //CITY
        BaseMod.addMonster(Gear.ID,Gear.NAME,() -> new MonsterGroup(new AbstractMonster[] { new Gear() }));

        BaseMod.addMonster(WheelTurningKing.ID,() -> new MonsterGroup(new AbstractMonster[] { new WheelTurningKing() }));
        BaseMod.addBoss(TheCity.ID, WheelTurningKing.ID,"img/monsters/boss/zlw_boss.png","img/monsters/boss/zlw_boss_outline.png");

        BaseMod.addMonster(TheDeifiedDog.ID,TheDeifiedDog.NAME,() -> new MonsterGroup(new AbstractMonster[] { new TheDeifiedDog() }));
        BaseMod.addMonster(YangJian.ID,YangJian.NAME,() -> new MonsterGroup(new AbstractMonster[] { new YangJian() ,new TheDeifiedDog(-400.0F,0.0F)}));

        BaseMod.addBoss(TheBeyond.ID, YangJian.ID,"img/monsters/boss/yangjian_boss.png","img/monsters/boss/yangjian_outline.png");

        BaseMod.addMonster(UAV.ID,UAV.NAME,() -> new MonsterGroup(new AbstractMonster[] { new UAV() }));

        BaseMod.addMonster(QinGuangKing.ID,QinGuangKing.NAME,() -> new MonsterGroup(new AbstractMonster[] { new QinGuangKing() }));
        BaseMod.addEliteEncounter(TheCity.ID, new MonsterInfo(QinGuangKing.ID, 9F));

        BaseMod.addMonster(Lamp.ID,() -> new MonsterGroup(new AbstractMonster[] { new Lamp(0f,0f,7) }));
        BaseMod.addMonster(Judge.ID,() -> new MonsterGroup(new AbstractMonster[] { new Judge() }));
        BaseMod.addEliteEncounter(TheCity.ID, new MonsterInfo(Judge.ID, 9F));
    }
    @SpireEnum
    public static AbstractCard.CardTags BOXING;

    class Keywords {
        Keyword[] keywords;
    }
}
