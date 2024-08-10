package monsters.act1;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.rewards.RewardItem;
import powers.ReboundDamagePower;
import relics.BloodSirenShell;

public class DrakeDemonKing extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:DrakeDemonKing";
    public static final String IMG = "img/monsters/drake_demon_king.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:DrakeDemonKing");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;


    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 50;
    private static final int HP_MAX = 60;
    private static final int A_2_HP_MIN = 56;
    private static final int A_2_HP_MAX = 66;
    private static final int DMG1 = 14;
    private static final int DMG2 = 3;
    private static final int HITS = 3;
    private static final int A_DMG1 = 17;
    private static final int A_DMG2  = 4;
    private static final int BLOCK1 = 16;
    private static final int A_BLOCK1 = 20;
    private static final int BLOCK2 = 9;
    private static final int A_BLOCK2 = 11;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 2;
    private static final int MGC2 = 3;
    private static final int A_MGC2 = 4;
    private int Dmg1;
    private int Dmg2;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int b1;
    private int b2;
    public int turn_count = 0 ;
    private int block_turn = 0 ;

    public DrakeDemonKing() {
        this(0.0F, 0.0F);
    }
    public DrakeDemonKing(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
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
        this.img = ImageMaster.loadImage(IMG);
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 18){
            this.addToBot(new GainBlockAction(this,20));
            this.addToBot(new ApplyPowerAction(this,this,new ReboundDamagePower(this,4,true)));
        }else {
            this.addToBot(new ApplyPowerAction(this,this,new ReboundDamagePower(this,3,true)));
            this.addToBot(new GainBlockAction(this,17));
        }
        this.addToBot(new ApplyPowerAction(this,this,new BarricadePower(this)));

    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2:
                this.addToBot(new ApplyPowerAction(this,this,new ReboundDamagePower(this,this.magicAmt2,true)));
                break;
            case 3:
                this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,this.magicAmt2+1),this.magicAmt2+1));
                this.addToBot(new ApplyPowerAction(this,this,new PlatedArmorPower(this,this.magicAmt2)));
                this.addToBot(new GainBlockAction(this,this.b2));
                break;
            case 4:
                this.addToBot(new GainBlockAction(this,this.b1));
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {

        if (this.currentBlock>0){
            this.block_turn=this.block_turn+1;
        }
        if (AbstractDungeon.ascensionLevel >= 18 && turn_count ==0) {
            this.setMove(MOVES[1], (byte) 2,Intent.BUFF);
        }else {
            if (this.block_turn>=2){
                this.setMove(MOVES[2], (byte) 3,Intent.DEFEND_BUFF);
                this.block_turn=0;
            }else {
                if (!lastMove((byte) 1) && !lastMoveBefore((byte) 4)){
                    this.setMove(MOVES[0],(byte) 1 ,Intent.ATTACK, this.damage.get(0).base);
                }else if (lastMove((byte) 4) && lastMoveBefore((byte) 1)){
                    this.setMove(MOVES[1], (byte) 2,Intent.BUFF);
                }else {
                    this.setMove(MOVES[3], (byte) 4,Intent.DEFEND);
                }
            }
            if (!(this.currentBlock>0)){
                this.block_turn=-1;
            }
        }
        ++turn_count;
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
        this.playDeathSfx();
        //AbstractDungeon.getCurrRoom().rewards.clear();
        //if (!AbstractDungeon.player.hasRelic(BloodSirenShell.ID)) {
        //
        //    AbstractDungeon.getCurrRoom().addRelicToRewards(new relics.BloodSirenShell());
        //}

    }

    private AbstractRelic.RelicTier returnRandomRelicTier() {
        int roll = AbstractDungeon.relicRng.random(0, 99);
        if (ModHelper.isModEnabled("Elite Swarm")) {
            roll += 10;
        }

        if (roll < 50) {
            return AbstractRelic.RelicTier.COMMON;
        } else {
            return roll > 82 ? AbstractRelic.RelicTier.RARE : AbstractRelic.RelicTier.UNCOMMON;
        }
    }
}
