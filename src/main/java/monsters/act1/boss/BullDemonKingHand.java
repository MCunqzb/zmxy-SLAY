package monsters.act1.boss;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
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

public class BullDemonKingHand extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:BullDemonKingHand";
    public static final String IMG = "img/monsters/bull_demon_king_hand.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:BullDemonKingHand");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 50;
    private static final int HP_MAX = 52;
    private static final int A_HP_MIN = 52;
    private static final int A_HP_MAX = 54;
    private static final int DMG1 = 8;
    private static final int DMG2 = 5;
    private static final int HITS = 2;
    private static final int A_DMG1 = 10;
    private static final int A_DMG2  = 6;
    private static final int BLOCK1 = 5;
    private static final int A_BLOCK1 = 7;
    private static final int BLOCK2 = 7;
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
    private int egg_turn = 0 ;
    private boolean is_egg ;


    public BullDemonKingHand() {
        this(0.0F, 0.0F);
    }
    public BullDemonKingHand(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 150.0F, 250.0F, IMG,x,y);
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

        this.recover = false;
        this.is_egg = false;
        if (x < -500){
            this.flipHorizontal =true;
        }
    }


    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                //attack
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                //buff v
                this.addToBot(new ApplyPowerAction(p, this, new VulnerablePower(p, this.magicAmt1,true), this.magicAmt1));
                break;
            case 3:
                //buff w
                this.addToBot(new ApplyPowerAction(p, this, new WeakPower(p, this.magicAmt1,true), this.magicAmt1));
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (AbstractDungeon.aiRng.randomBoolean(0.7F)){
            this.setMove((byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
        }else{
            if (AbstractDungeon.aiRng.randomBoolean(0.5F)){
                this.setMove((byte) 2, Intent.DEBUFF);
            }else {
                this.setMove((byte) 3, Intent.DEBUFF);
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
        super.die();
        if (!this.isDead && !this.isDying) {
            this.addToTop(new HideHealthBarAction(this));
        }
    }




}
