package monsters.act2;

import actions.SharkDamageAction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ExplosivePower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import monsters.act1.boss.SharkDemonKing;
import powers.FakeSharkBloodThirstPower;

public class UAV extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:UAV";
    public static final String IMG = "img/monsters/UAV.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:UAV");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 16;
    private static final int HP_MAX = 18;
    private static final int A_HP_MIN = 18;
    private static final int A_HP_MAX = 23;
    private static final int DMG1 = 5;
    private static final int A_DMG1 = 8;
    private static final int HITS = 2;
    private static final int DMG2 = 5;
    private static final int A_DMG2  = 6;
    private static final int BLOCK1 = 7;
    private static final int A_BLOCK1 = 9;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 2;
    private static final int A_MGC1 =3;
    private static final int MGC2 = 5;
    private static final int A_MGC2 = 4;
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


    public UAV() {
        this(0.0F, 0.0F);
    }
    public UAV(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 140.0F, 200.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(A_HP_MIN, A_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);
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
        if (AbstractDungeon.ascensionLevel >= 18) {
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


    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                //attack
                if (!this.hasPower(ExplosivePower.POWER_ID)){
                    this.addToBot(new ApplyPowerAction(this,this,new ExplosivePower(this,this.magicAmt2)));
                }
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;
            case 2:
                //debuff
                if (!this.hasPower(ExplosivePower.POWER_ID)){
                    this.addToBot(new ApplyPowerAction(this,this,new ExplosivePower(this,this.magicAmt2)));
                }
                AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
                    AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.2F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.1F));
                    AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.15F));
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.magicAmt1));
                break;
            case 3:
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (turn_count<this.magicAmt2+1){
            if (AbstractDungeon.ascensionLevel >= 18) {
                if (turn_count==1) {
                    this.setMove((byte) 2, Intent.DEBUFF);
                }else {
                    if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                        this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
                    } else {
                        this.setMove((byte) 2, Intent.DEBUFF);
                    }
                }
            }else {
                if (turn_count==1) {
                    this.setMove((byte) 2, Intent.DEBUFF);
                }else {
                    if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                        this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
                    } else {
                        this.setMove((byte) 2, Intent.DEBUFF);
                    }
                }
            }
        }else {
            this.setMove((byte)3, Intent.UNKNOWN);
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

    @Override
    public void die() {
        super.die();
        if (!this.isDead && !this.isDying) {
            this.addToTop(new HideHealthBarAction(this));
        }
    }


}
