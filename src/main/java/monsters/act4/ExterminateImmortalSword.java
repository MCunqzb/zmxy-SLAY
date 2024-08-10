package monsters.act4;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ExterminateImmortalSword extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:ExterminateImmortalSword";
    public static final String IMG = "img/monsters/exterminate_immortal_sword.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 51;
    private static final int HP_MAX = 57;
    private static final int A_2_HP_MIN = 59;
    private static final int A_2_HP_MAX = 65;
    private static final int DMG1 = 3;
    private static final int DMG2 = 3;
    private static final int HITS = 3;
    private static final int A_2_DMG1 = 4;
    private static final int A_2_DMG2  = 4;
    private static final int BLOCK1 = 0;
    private static final int BLOCK2 = 0;
    private static final int MGC1 = 1;
    private static final int A_18_MGC1 = 2;
    private static final int MGC2 = 0;
    private static final int A_18_MGC2 = 2;
    private int Dmg1;
    private int Dmg2;
    private int HitTime;
    private int strengthAmt;
    private int BLOCK_AMOUNT = 4;
    private int A_17_BLOCK_AMOUNT = 20;

    public ExterminateImmortalSword() {
        this(0.0F, 0.0F);
    }
    public ExterminateImmortalSword(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 160.0F, 160.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);
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
            this.strengthAmt = A_18_MGC1;
        } else {
            this.strengthAmt = MGC1;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));

                break;
            case 2:
                this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,this.strengthAmt),this.strengthAmt));
                if (!this.hasPower("Flight")) {
                    this.addToBot(new ApplyPowerAction(this, this, new FlightPower(this, 2), 2));
                }
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (AbstractDungeon.ascensionLevel >= 18 && this.turn_count<=1) {
            this.setMove(MOVES[1], (byte) 2,Intent.BUFF);
        }else {
            if (this.lastTwoMoves((byte)1)){
                this.setMove(MOVES[1], (byte) 2,Intent.BUFF);
            }
            else {
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
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
