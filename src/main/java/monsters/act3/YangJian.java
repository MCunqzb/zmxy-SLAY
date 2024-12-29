package monsters.act3;

import actions.WaitActionPassFast;
import vfx.YangJianAdditiveSlashImpactEffect;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import powers.ThreeEyesSealPower;

public class YangJian extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:YangJian";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:YangJian");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;


    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 270;
    private static final int HP_MAX = 275;
    private static final int A_HP_MIN = 288;
    private static final int A_HP_MAX = 297;
    private static final int DMG1 = 12;
    private static final int A_DMG1 = 14;
    private static final int DMG2 = 5;
    private static final int A_DMG2  = 5;
    private static final int DMG3 = 12;
    private static final int A_DMG3  = 14;
    private static final int HITS = 4;
    private static final int A_HITS = 5;
    private static final int BLOCK1 = 11;
    private static final int A_BLOCK1 = 11;
    private static final int BLOCK2 = 9;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 2;
    private static final int A_MGC1 = 3;
    private static final int MGC2 = 1;
    private static final int A_MGC2 = 2;
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
    private int eye= 0 ;
    private int from ;
    private boolean talked =false;
    private boolean dialogue = false;
    private int MOVE1and2 = 0;
    private int MOVE2and3 = 0;
    private int MOVE4 = 0;
    private boolean MOVE5 = false;


    public YangJian() {
        this(0.0F, 0.0F);
    }
    public YangJian(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, null,x,y);
        this.loadAnimation("img/monsters/yangjian.atlas","img/monsters/yangjian37.json",0.8F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //idle attack_0 eye disappear
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
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("zmxy/act3boss.ogg");
    }




    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch (this.nextMove) {
            case 1:
                //ATTACK
                this.addToBot(new WaitActionPassFast(0.4f));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY, false));
                this.addToBot(new WaitActionPassFast(0.3f));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, false));
                this.state.setAnimation(0, "attack_0", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                break ;
            case 2:
                //ATTACK HITS
                this.state.setAnimation(0, "disappear", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                this.addToBot(new VFXAction(new YangJianAdditiveSlashImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 1.0F));
                CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.5F);
                for(int i = 0; i < this.HitTime; ++i) {
                    this.addToBot(new VFXAction(new YangJianAdditiveSlashImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.05F));
                    this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
                }
                break ;
            case 3:
                //BUFF
                this.addToBot(new VFXAction(new HeartMegaDebuffEffect()));
                this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,this.magicAmt1)));
                break ;
            case 4:
                //DEBUFF
                this.state.setAnimation(0, "eye", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                switch (this.eye) {
                    case 0:
                        this.addToBot(new ApplyPowerAction(p, this, new WeakPower(p, this.magicAmt2, true), this.magicAmt2));
                        this.addToBot(new ApplyPowerAction(p, this, new VulnerablePower(p, this.magicAmt2, true), this.magicAmt2));
                        this.addToBot(new ApplyPowerAction(p, this, new FrailPower(p, this.magicAmt2, true), this.magicAmt2));
                        break ;
                    default:
                        this.addToBot(new ApplyPowerAction(p, this, new ThreeEyesSealPower(p, 1), 1));

                }
                this.eye++;
                break ;

        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if (!talked){
            this.talked =true;
            this.addToBot((new TalkAction(this,DIALOG[0], 2.0F, 2.0F)));
            this.addToBot(new WaitActionPassFast(1.0f));
        }
        if (AbstractDungeon.ascensionLevel >= 19) {
            if (AbstractDungeon.aiRng.randomBoolean(0.4F)&& !lastMove((byte)2)){
                this.setMove(MOVES[2],(byte) 2, Intent.ATTACK, (this.damage.get(1)).base,this.HitTime,true);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.6F)){
                this.setMove(MOVES[4],(byte) 4, Intent.STRONG_DEBUFF);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.35F) && !lastMove((byte)3) && !lastMoveBefore((byte)3)){
                this.setMove(MOVES[3],(byte) 3, Intent.BUFF);
            } else if (!lastTwoMoves((byte) 1)){
                this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,2,true);
            }else {
                this.setMove(MOVES[3],(byte) 3, Intent.BUFF);
            }
        }else {
            if (AbstractDungeon.aiRng.randomBoolean(0.45F) && !lastMove((byte)2) && !lastMoveBefore((byte)2)){
                this.setMove(MOVES[2],(byte) 2, Intent.ATTACK, (this.damage.get(1)).base,this.HitTime,true);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.6F) && !lastMoveBefore((byte)4)){
                this.setMove(MOVES[4],(byte) 4, Intent.STRONG_DEBUFF);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.3F) && !lastMove((byte)3) && !lastMoveBefore((byte)3)){
                this.setMove(MOVES[3],(byte) 3, Intent.BUFF);
            } else if (!lastTwoMoves((byte) 1)){
                this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,2,true);
            }else {
                this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base, 2, true);
            }
        }
    }



    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_LOOTER_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_LOOTER_2B");
        } else {
            CardCrawlGame.sound.play("VO_LOOTER_2C");
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
