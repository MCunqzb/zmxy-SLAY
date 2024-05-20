package monsters.act4;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import powers.CursePower;
import powers.DeathCursePower;

public class SlayingImmortalSword extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:SlayingImmortalSword";
    public static final String IMG = "img/monsters/slaying_immortal_sword.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 31;
    private static final int HP_MAX = 37;
    private static final int A_2_HP_MIN = 39;
    private static final int A_2_HP_MAX = 45;
    private static final int DMG1 = 2;
    private static final int A_2_DMG1 = 3;
    private static final int HITS = 4;
    private static final int DMG2 =17;
    private static final int A_2_DMG2  = 20;
    private static final int BLOCK1 = 0;
    private static final int BLOCK2 = 0;
    private static final int MGC1 = 1;
    private static final int A_18_MGC1 = 1;
    private static final int MGC2 = 3;
    private static final int A_18_MGC2 = 4;
    private int Dmg1;
    private int Dmg2;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int BLOCK_AMOUNT = 4;
    private int A_17_BLOCK_AMOUNT = 20;

    public SlayingImmortalSword() {
        this(0.0F, 0.0F);
    }
    public SlayingImmortalSword(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 160F, 160F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(HP_MIN, HP_MAX);
        } else {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.Dmg1 = A_2_DMG1;
            this.Dmg2 = A_2_DMG2;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
        }
        this.HitTime=HITS;
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.magicAmt1 = A_18_MGC1;
            this.magicAmt2 = A_18_MGC2;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
    }

    public void usePreBattleAction() {
        this.addToBot(new ApplyPowerAction(this,this,new DeathCursePower(this,this.magicAmt1),this.magicAmt1));

    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                //AT
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2:
                //BF
                this.addToBot(new ApplyPowerAction(p,this,new CursePower(this,this.magicAmt2),this.magicAmt2));
                if (!this.hasPower(DeathCursePower.POWER_ID)) {
                    this.addToBot(new ApplyPowerAction(this, this, new DeathCursePower(this, this.magicAmt1), this.magicAmt1));
                }
                if (!this.hasPower("Flight")) {
                    this.addToBot(new ApplyPowerAction(this, this, new FlightPower(this, 3), 3));
                }
                this.HitTime=this.HitTime+1;
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.setMove(MOVES[1], (byte) 2,Intent.STRONG_DEBUFF);
        }else {
            if (this.lastTwoMoves((byte)2)){
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
            }
            else {
                this.setMove(MOVES[1], (byte) 2,Intent.STRONG_DEBUFF);
            }
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
