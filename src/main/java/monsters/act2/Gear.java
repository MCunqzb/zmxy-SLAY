package monsters.act2;


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import powers.GearAttackPlayerPower;
import powers.GearAttackPower;

import javax.xml.bind.Element;

public class Gear extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:Gear";
    public static final String IMG = "img/monsters/gear.png";
    public static final String GIF = "img/monsters/gear.gif";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:Gear");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 23;
    private static final int HP_MAX = 24;
    private static final int A_HP_MIN = 26;
    private static final int A_HP_MAX = 27;
    private static final int DMG1 = 5;
    private static final int A_DMG1 = 6;
    private static final int DMG2 = 6;
    private static final int A_DMG2  = 7;
    private static final int HITS = 2;
    private static final int BLOCK1 = 8;
    private static final int A_BLOCK1 = 9;
    private static final int BLOCK2 = 9;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 =2;
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
    private boolean is_from2;

    private boolean usedStasis = false;

    public Gear(){
        this(0.0F, 0.0F,2.5f,false,false);
    }

    public Gear(float x, float y,float scale,boolean flip_h,boolean from2) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 150.0F, 150.0F,  (String) null,x,y);
        this.loadAnimation("img/monsters/gear.atlas","img/monsters/gear.json",scale);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "gear", true);
        e.setTime(e.getEndTime() * MathUtils.random());
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
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.magicAmt1 = A_MGC1;
            this.magicAmt2 = A_MGC2;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
        this.flipHorizontal =flip_h;
        this.is_from2 = from2;
    }

    public void usePreBattleAction() {
        this.addToBot(new ApplyPowerAction(this,this,new GearAttackPower(this,!this.flipHorizontal)));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new GearAttackPlayerPower(AbstractDungeon.player)));
    }


    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                //attack
                if (!is_from2){
                    this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }else {
                    this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
                break;
            case 2:
                //block
                this.addToBot(new GainBlockAction(AbstractDungeon.getMonsters().getMonster(WheelTurningKing.ID), this, 4));
                this.addToBot(new GainBlockAction(this, this, this.b1));
                break;
            case 3:
                this.addToBot(new ApplyStasisAction(this));
                break;
            case 4:
                if (!this.hasPower(GearAttackPower.POWER_ID)){
                    this.addToBot(new ApplyPowerAction(this, this, new GearAttackPower(this, !this.flipHorizontal)));
            }
                break;
            case 5:
                this.addToBot(new GainBlockAction(AbstractDungeon.getMonsters().getMonster(WheelTurningKing.ID), this, 4));
                break;

        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (!is_from2){
            if (!this.usedStasis &&i >= 25) {
                this.setMove((byte)3, Intent.STRONG_DEBUFF);
                this.usedStasis = true;
            } else if (i >= 70 && !this.lastTwoMoves((byte)2)) {
                this.setMove((byte)2, Intent.DEFEND);
            } else if (!this.lastTwoMoves((byte)1)) {
                this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
            } else {
                this.setMove((byte)2, Intent.DEFEND);
            }
        }else {
            if (this.hasPower(GearAttackPower.POWER_ID)){
                if (!this.lastTwoMoves((byte)1)){
                    this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(1)).base);
                }else if(!this.lastTwoMoves((byte)5)) {
                    this.setMove((byte) 5,Intent.DEFEND);
                }else {
                    this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(1)).base);
                }
            }else
                this.setMove((byte) 4,Intent.UNKNOWN);

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
        super.die();
        if (!this.isDead && !this.isDying) {
            this.addToTop(new HideHealthBarAction(this));
        }
    }






}
