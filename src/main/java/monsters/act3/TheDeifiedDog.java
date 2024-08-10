package monsters.act3;

import basemod.BaseMod;
import basemod.helpers.VfxBuilder;
import cards.GearCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import monsters.act2.Gear;
import powers.CanineTeethPower;
import powers.GearAttackPlayerPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TheDeifiedDog extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:TheDeifiedDog";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:TheDeifiedDog");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;


    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 225;
    private static final int HP_MAX = 230;
    private static final int A_HP_MIN = 231;
    private static final int A_HP_MAX = 240;
    private static final int DMG1 = 7;
    private static final int A_DMG1 = 9;
    private static final int DMG2 = 10;
    private static final int A_DMG2  = 12;
    private static final int DMG3 = 12;
    private static final int A_DMG3  = 14;
    private static final int HITS = 2;
    private static final int A_HITS = 6;
    private static final int BLOCK1 = 11;
    private static final int A_BLOCK1 = 11;
    private static final int BLOCK2 = 9;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 2;
    private static final int MGC2 = 2;
    private static final int A_MGC2 = 3;
    private boolean recover = false;
    private int Dmg1;
    private int Dmg2;
    private int Dmg3;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int b1;
    private int b2;
    public int turn_count = 0 ;
    private int gear_generate= 0 ;
    private int from ;
    private boolean dialogue = false;
    private int MOVE1and2 = 0;
    private int MOVE2and3 = 0;
    private int MOVE4 = 0;
    private boolean MOVE5 = false;


    public TheDeifiedDog() {
        this(0.0F, 0.0F);
    }
    public TheDeifiedDog(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, null,x,y);
        this.loadAnimation("img/monsters/dog.atlas","img/monsters/dog37.json",0.8F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //buff attack idle
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(A_HP_MIN, A_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.Dmg1 = A_DMG1;
            this.Dmg2 = A_DMG2;
            this.Dmg3 = A_DMG3;
            this.b1 =A_BLOCK1;
            this.b2 =A_BLOCK2;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
            this.Dmg3 = DMG3;
            this.b1 =BLOCK1;
            this.b2 =BLOCK2;

        }
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.magicAmt1 = A_MGC1;
            this.magicAmt2 = A_MGC2;
            this.HitTime=A_HITS;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
            this.HitTime=HITS;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
        this.damage.add(new DamageInfo(this, this.Dmg3));
        this.damage.add(new DamageInfo(this, this.b1));
        this.damage.add(new DamageInfo(this, this.b2));
        this.recover = false;
        this.from = 1;


    }

    public void usePreBattleAction() {
        //CardCrawlGame.music.unsilenceBGM();
        //AbstractDungeon.scene.fadeOutAmbiance();
        //AbstractDungeon.getCurrRoom().playBgmInstantly("zmxy/act2boss.ogg");
        //AbstractDungeon.getCurrRoom().cannotLose = true;
        this.addToBot(new ApplyPowerAction(this,this,new CanineTeethPower(this)));
    }




    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch (this.nextMove) {
            case 1:
                //ATTACK
                if (p != null) {
                    this.addToBot(new VFXAction(new ClashEffect(p.hb.cX, p.hb.cY), 0.1F));
                }
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.NONE, true));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                break ;
            case 2:
                //DEBUFF
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.MAROON, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
                this.addToBot(new MakeTempCardInDiscardAction(new Dazed(), this.magicAmt2));
                this.state.setAnimation(0, "buff", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                break ;
            case 3:
                //DBUFF
                this.addToBot(new VFXAction(new HeartMegaDebuffEffect()));
                this.addToBot(new ApplyPowerAction(p,this,new StrengthPower(p,-1),-1));
                if (AbstractDungeon.ascensionLevel >= 19) {
                    this.addToBot(new ApplyPowerAction(p, this, new WeakPower(p, this.magicAmt1, true), this.magicAmt1));
                }
                this.state.setAnimation(0, "buff", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                break ;
            case 4:
                //ATTACK DEBUFF
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                this.addToBot(new ApplyPowerAction(p, this, new DrawReductionPower(p, 1)));
                break ;

        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if (AbstractDungeon.ascensionLevel >= 19) {
            if (AbstractDungeon.aiRng.randomBoolean(0.33F)){
                this.setMove((byte) 2, Intent.DEBUFF);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.33F) && !lastMove((byte)3) && !lastMoveBefore((byte)3)){
                this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.33F)&& !lastMoveBefore((byte)4)){
                this.setMove((byte) 4, Intent.ATTACK_DEBUFF,(this.damage.get(1)).base);
            } else if (!lastTwoMoves((byte) 1)){
                this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(0)).base,2,true);
            }else {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            }
        }else {
            if (AbstractDungeon.aiRng.randomBoolean(0.33F)){
                this.setMove((byte) 2, Intent.DEBUFF);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.33F) && !lastMove((byte)3) && !lastMoveBefore((byte)3)){
                this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.33F)){
                this.setMove((byte) 4, Intent.ATTACK_DEBUFF,(this.damage.get(1)).base);
            } else if (!lastTwoMoves((byte) 1) || !lastMove((byte) 4)){
                this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(0)).base,2,true);
            }else {
                this.setMove((byte) 2, Intent.DEBUFF);
            }
        }
    }



    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("JAW_WORM_DEATH");
        } else {
            CardCrawlGame.sound.play("JAW_WORM_DEATH");
        }

    }

    private void movePosition(float x, float y){
        this.drawX = x;
        this.drawY = y;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.refreshHitboxLocation();
    }

    @Override
    public void die() {
        super.die();
        this.playDeathSfx();
    }




}
