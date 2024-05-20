package monsters.act1.boss;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;
import vfx.ColorfulThrowDaggerEffect;

import java.util.Iterator;

public class RocDemonKing extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:RocDemonKing";
    public static final String IMG = "img/monsters/roc_demon_king.png";
    public static final String EGG = "img/monsters/egg.png";
    public static final String LAND = "img/monsters/roc_demon_king_land.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:RocDemonKing");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;


    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 90;
    private static final int HP_MAX = 94;
    private static final int A_HP_MIN = 97;
    private static final int A_HP_MAX = 101;
    private static final int DMG1 = 13;
    private static final int DMG2 = 6;
    private static final int HITS = 2;
    private static final int A_DMG1 = 17;
    private static final int A_DMG2  = 7;
    private static final int BLOCK1 = 5;
    private static final int A_BLOCK1 = 7;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 3;
    private static final int A_MGC1 = 4;
    private static final int MGC2 = 5;
    private static final int A_MGC2 = 7;
    private boolean recover = false;
    private int Dmg1;
    private int Dmg2;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int b1;
    private int b2;
    public int turn_count = 0 ;
    private int egg_turn = 0 ;
    private boolean is_egg ;


    public RocDemonKing() {
        this(0.0F, 0.0F);
    }
    public RocDemonKing(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(HP_MIN, HP_MAX);
        } else {
            setHp(A_HP_MIN, A_HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.Dmg1 = A_DMG1;
            this.Dmg2 = A_DMG2;
            this.b1 =A_BLOCK1;
            this.b2 =A_BLOCK2;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
            this.b1 =BLOCK1;
            this.b2 =BLOCK2;
        }
        this.HitTime=HITS;
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.magicAmt1 = A_MGC1;
            this.magicAmt2 = A_MGC2;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));

        this.recover = false;
        this.is_egg = false;
    }

    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        AbstractDungeon.getCurrRoom().cannotLose = true;
        this.addToBot(new ApplyPowerAction(this,this,new FlightPower(this,3),3));
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                //fly BUFF
                this.addToBot(new ApplyPowerAction(this,this,new FlightPower(this,this.magicAmt1),this.magicAmt1));
                this.img = ImageMaster.loadImage(IMG);
                break;
            case 2:
                //att_1hit ATTACK
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SMASH));
                break;
            case 3:
                //Attack hits
                this.addToTop(new VFXAction(new ColorfulThrowDaggerEffect(p.hb.cX, p.hb.cY, Color.ORANGE,true)));
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                this.addToTop(new VFXAction(new ColorfulThrowDaggerEffect(p.hb.cX, p.hb.cY, Color.ORANGE,true)));
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                break;
            case 4:
                //DEBUFF

                this.addToBot(new VFXAction(new RedFireballEffect(this.hb.cX, this.hb.cY, p.hb.cX, p.hb.cY,1), 0.5F));
                this.addToBot(new ApplyPowerAction(p, this, new VulnerablePower(p, this.magicAmt1-1,true), this.magicAmt1-1));
                break;
            case 5:
                //KILLED UNKNOWN
                this.halfDead = false;
                this.addToBot(new CanLoseAction());
                this.img = ImageMaster.loadImage(EGG);
                this.is_egg =true;
                this.addToBot(new RemoveSpecificPowerAction(this,this,"Flight"));
                this.addToBot(new HealAction(this, this, (int) (this.maxHealth * 0.4)));
                break;
            case 6:
                //recover
                this.recover = true;
                this.is_egg = false;
                this.img = ImageMaster.loadImage(IMG);
                if (!this.hasPower("Flight")){
                    this.addToBot(new ApplyPowerAction(this,this,new FlightPower(this,this.magicAmt1),this.magicAmt1));
                }
                this.addToBot(new HealAction(this, this, (int) (this.maxHealth * 0.55)));
                this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,this.magicAmt2),this.magicAmt2));
                break;
            case 7:
                //egg count
                this.egg_turn++;
                this.addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.5F));
                this.addToBot(new MakeTempCardInDiscardAction(new Burn(), this.magicAmt1));
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (this.hasPower("Flame Barrier")) {
            this.addToBot(new RemoveSpecificPowerAction(this, this, "Flame Barrier"));
        }
        if (!this.is_egg) {
            if (this.hasPower("Flight")) {
                if (AbstractDungeon.ascensionLevel >= 19 && turn_count ==1) {
                    this.setMove(MOVES[3], (byte) 4, Intent.DEBUFF);
                } else {
                    if (AbstractDungeon.aiRng.randomBoolean(0.1F) || ((lastMove((byte) 2) && lastMoveBefore((byte) 3)) || (lastMove((byte) 3) && lastMoveBefore((byte) 2)))) {
                        this.setMove(MOVES[3], (byte) 4, Intent.DEBUFF);
                    } else if (lastMove((byte) 2)) {
                        this.setMove(MOVES[2], (byte) 3, Intent.ATTACK_BUFF, (this.damage.get(1)).base, this.HitTime, true);
                    } else if (lastMove((byte) 3)) {
                        this.setMove(MOVES[1], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base);
                    } else {
                        if ( this.getPower("Flight").amount > 2 ) {
                            this.setMove(MOVES[1], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base);
                        } else {
                            this.setMove(MOVES[2], (byte) 3, Intent.ATTACK_BUFF, (this.damage.get(1)).base, this.HitTime, true);
                        }
                    }
                }
            } else {
                if (!this.recover){
                this.setMove(MOVES[0], (byte) 1, Intent.BUFF);
                }else {
                    if (lastMove((byte) 2)) {
                        this.setMove(MOVES[2], (byte) 3, Intent.ATTACK_BUFF, (this.damage.get(1)).base, this.HitTime, true);
                    } else if (lastMove((byte) 3)) {
                        this.setMove(MOVES[1], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base);
                    }
                }
            }
        }else {
            if (this.egg_turn < 2) {
                this.is_egg =true;
                this.setMove(MOVES[6], (byte) 7, Intent.STRONG_DEBUFF);
            }else {
                this.setMove(MOVES[5], (byte) 6, Intent.BUFF);

            }
        }
    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2A");
        } else {
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2B");
        }

    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.playDeathSfx();
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            this.onBossVictoryLogic();
        }
    }

    public void damage (DamageInfo info){
        super.damage(info);
        if (!this.is_egg) {
            if (this.hasPower("Flight") && this.getPower("Flight").amount >1 ) {
                this.img = ImageMaster.loadImage(IMG);
            } else {
                this.img = ImageMaster.loadImage(LAND);
            }
        }
        if (this.currentHealth <= 0 && !this.halfDead && this.is_egg ==false) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                this.halfDead = true;
                this.img = ImageMaster.loadImage(EGG);
            }

            Iterator s = this.powers.iterator();

            AbstractPower p;
            while(s.hasNext()) {
                p = (AbstractPower)s.next();
                p.onDeath();
            }

            s = AbstractDungeon.player.relics.iterator();

            while(s.hasNext()) {
                AbstractRelic r = (AbstractRelic)s.next();
                r.onMonsterDeath(this);
            }

            this.addToTop(new ClearCardQueueAction());
            s = this.powers.iterator();

            while(true) {
                do {
                    if (!s.hasNext()) {
                        this.setMove((byte)5, Intent.UNKNOWN);
                        this.createIntent();
                        AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
                        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)5, Intent.UNKNOWN));
                        this.applyPowers();

                        return;
                    }

                    p = (AbstractPower)s.next();
                } while(p.type != AbstractPower.PowerType.DEBUFF);

                s.remove();
            }
        }
    }


}
