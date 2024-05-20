package characters;

import Helpers.ModHelper;
import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import cards.FireBlitz;
import cards.FireMagicSlash;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.scene.IroncladVictoryFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.SlowFireParticleEffect;
import pathes.AbstractCardEnum;
import pathes.ThmodClassEnum;
import relics.BloodthirstyFireEyes;
import relics.Bracelets;
import util.RenderOnlyMonkeyKingPhantom;
import vfx.ColorfulLaserBeamEffect;
import vfx.ColorfulSlowFireParticleEffect;
import vfx.ColorfulVictoryFlameEffect;

import java.util.ArrayList;
import java.util.List;

public class MonkeyKing extends CustomPlayer {
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("dreaming_journey_to_the_west:MonkeyKing");
    private static final int ENERGY_PER_TURN = 3;
    private float effectTimer = 0.0F;
    private static final String MonkeyKing_SHOULDER_2 = "img/chars/shoulderR.png";
    private static final String MonkeyKing_SHOULDER_1 = "img/chars/shoulder.png";
    private static final String MonkeyKing_CORPSE = "img/chars/corpse.png";
    private static final String MonkeyKing_STAND = "img/chars/MonkeyKing.png";

    private static final String[] ORB_TEXTURES = new String[] { "img/chars/orb/layer5.png", "img/chars/orb/layer4.png", "img/chars/orb/layer3.png", "img/chars/orb/layer2.png", "img/chars/orb/layer1.png", "img/chars/orb/layer6.png", "img/chars/orb/layer5d.png", "img/chars/orb/layer4d.png", "img/chars/orb/layer3d.png", "img/chars/orb/layer2d.png", "img/chars/orb/layer1d.png" };
    private static final String ORB_VFX = "img/chars/orb/vfx.png";
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };

    private static final int STARTING_HP = 75;
    private static final int MAX_HP = 75;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    public RenderOnlyMonkeyKingPhantom monkeyKingPhantom;

    public static final Color OrangeRed = CardHelper.getColor(212, 126, 107);

    public MonkeyKing(String name) {
        super(name, ThmodClassEnum.MonkeyKing_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, (String)null, (String)null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(MonkeyKing_STAND, MonkeyKing_SHOULDER_2, MonkeyKing_SHOULDER_1, MonkeyKing_CORPSE,
                getLoadout(),
                0.0F, 5.0F, 240.0F, 300.0F,
                new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public ArrayList<String> getStartingDeck() {

        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Attack_MonkeyKing");
        retVal.add("Attack_MonkeyKing");
        retVal.add("Attack_MonkeyKing");
        retVal.add("BoxingCard");
        retVal.add("Defend_MonkeyKing");
        retVal.add("Defend_MonkeyKing");
        retVal.add("Defend_MonkeyKing");
        retVal.add("Defend_MonkeyKing");
        retVal.add("Dodge");
        retVal.add("Anger");

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BloodthirstyFireEyes.ID);
        retVal.add(Bracelets.ID);
        UnlockTracker.markRelicAsSeen("BloodthirstyFireEyes");
        UnlockTracker.markRelicAsSeen("Bracelets");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0], STARTING_HP, MAX_HP,HAND_SIZE , STARTING_GOLD, ASCENSION_MAX_HP_LOSS, this, getStartingRelics(), getStartingDeck(), false);
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override

    public AbstractCard.CardColor getCardColor() {

        return AbstractCardEnum.MonkeyKing_RED;
    }

    @Override
    public Color getCardRenderColor() {
        return OrangeRed;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new FireMagicSlash();
    }

    @Override
    public Color getCardTrailColor() {
        return OrangeRed;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(ModHelper.MakePath("fire_demon_slash"), 0.1F);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }
    public void updateOrb(int orbCount) {
        this.energyOrb.updateOrb(orbCount);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return ModHelper.MakePath("fire_demon_slash");
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return (AbstractPlayer)new MonkeyKing(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return OrangeRed;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.SLASH_VERTICAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL };
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        this.effectTimer -= Gdx.graphics.getDeltaTime();
        if (this.effectTimer < 0.0F) {
            effects.add(new ColorfulSlowFireParticleEffect(new Color(MathUtils.random(0.7F, 0.9F), MathUtils.random(0.1F, 0.4F), MathUtils.random(0.05F, 0.2F), 0.8F),1.0f,3.0f));
            effects.add(new ColorfulSlowFireParticleEffect(new Color(MathUtils.random(0.9F, 1.0F), MathUtils.random(0.4F, 0.7F), MathUtils.random(0.05F, 0.2F), 0.8F),1.0f,3.0f));
            effects.add(new ColorfulVictoryFlameEffect(new Color(MathUtils.random(0.9F, 1.0F), MathUtils.random(0.1F, 0.4F), MathUtils.random(0.05F, 0.2F), 0.8F),4.0f,6.0f));
            effects.add(new ColorfulVictoryFlameEffect(new Color(MathUtils.random(0.9F, 1.0F), MathUtils.random(0.4F, 0.7F), MathUtils.random(0.05F, 0.2F), 0.8F),1.0f,3.0f));
            effects.add(new ColorfulVictoryFlameEffect(new Color(MathUtils.random(0.01F, 0.1f), MathUtils.random(0.3F, 0.6F), MathUtils.random(0.8F, 1.0F), 0.7F),0.5f,1f));
            this.effectTimer = 0.1F;
        }
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("img/chars/victory1.png"));
        panels.add(new CutscenePanel("img/chars/victory2.png","ATTACK_MAGIC_BEAM_SHORT"));
        panels.add(new CutscenePanel("img/chars/victory3.png"));
        panels.add(new CutscenePanel("img/chars/victory4.png", "ATTACK_HEAVY"));
        panels.add(new CutscenePanel("img/chars/victory5.png","MONSTER_AUTOMATON_SUMMON"));
        panels.add(new CutscenePanel("img/chars/victory6.png","MONSTER_GUARDIAN_DESTROY"));
        panels.add(new CutscenePanel("img/chars/victory7.png","ATTACK_MAGIC_BEAM"));
        return panels;
    }
    @Override
    public String getVampireText() {
        return characterStrings.TEXT[2];
    }
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }

    private static final float monkeyKingPhantom_XDIFF = 220.0F * Settings.scale;

    private static final float monkeyKingPhantom_YDIFF = 0.0F * Settings.scale;

    public static boolean isInCombat() {
        return (CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }
    
    //public void combatUpdate() {
    //    super.combatUpdate();
    //        if (this.isInCombat() && ((Integer) TempHPField.tempHp.get(AbstractDungeon.player)).intValue() > 0) {
    //            AbstractDungeon.player.img = ImageMaster.loadImage("img/chars/MonkeyKingPhantom.png");
    //        }
    //        else
    //            AbstractDungeon.player.img = ImageMaster.loadImage("img/chars/MonkeyKing.png");
//
    //}

}


