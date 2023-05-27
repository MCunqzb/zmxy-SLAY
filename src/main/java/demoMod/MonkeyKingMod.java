package demoMod;

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
import com.megacrit.cardcrawl.cards.red.FlameBarrier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import pathes.AbstractCardEnum;
import pathes.ThmodClassEnum;
import powers.FieryEyesPower;
import relics.BloodthirstyFireEyes;
import relics.Bracelets;
import relics.FuriousRing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class MonkeyKingMod implements EditKeywordsSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, AddAudioSubscriber, SetUnlocksSubscriber {
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
        BaseMod.addCard(new FakePunch());
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
        //BaseMod.loadCustomStringsFile(Keyword.class, "zaomengxiyou/localization/" +lang +"/KeyWordsStrings.json");
        //BaseMod.loadCustomStringsFile(PotionStrings.class, "zaomengxiyou/localization/" + lang + "/potions.json");
        //BaseMod.loadCustomStringsFile(UIStrings.class, "zaomengxiyou/localization/" + lang + "/uiString.json");
    }



    @Override
    public void receiveEditRelics() {

        BaseMod.addRelicToCustomPool(new BloodthirstyFireEyes(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelicToCustomPool(new Bracelets(), AbstractCardEnum.MonkeyKing_RED);
        BaseMod.addRelic(new FuriousRing(), RelicType.SHARED);
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

    }

    @Override
    public void receiveSetUnlocks() {

    }

    @SpireEnum
    public static AbstractCard.CardTags BOXING;

    class Keywords {
        Keyword[] keywords;
    }
}
