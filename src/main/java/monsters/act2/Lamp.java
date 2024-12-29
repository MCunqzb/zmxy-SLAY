package monsters.act2;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

public class Lamp extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:Lamp";
    public static final String IMG = "img/monsters/lamp.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:Lamp");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 1;
    private static final int HP_MAX = 1;
    private static final int A_HP_MIN = 1;
    private static final int A_HP_MAX = 1;
    private static final int DMG1 = 1;
    private static final int A_DMG1 = 1;
    private static final int HITS = 1;
    private static final int DMG2 = 1;
    private static final int A_DMG2  = 1;
    private static final int BLOCK1 = 1;
    private static final int A_BLOCK1 = 1;
    private static final int BLOCK2 = 1;
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
    private int light_turn = 0 ;
    private boolean is_egg ;


    public Lamp() {
        this(0.0F, 0.0F,7);
    }
    public Lamp(float x, float y, int num) {
        super(NAME, ID, num, 0.0F, 0.0F, 10.0F, 10.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(num, num);
        } else {
            setHp(num, num);
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

        this.light_turn = num;

    }


    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 2:
                break;
            case 3:
                this.addToBot(new SuicideAction(this));
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }
    //this.drawX = (float)Settings.WIDTH * 0.75F + offsetX * Settings.xScale;
    //this.drawY = AbstractDungeon.floorY + offsetY * Settings.yScale;
    @Override
    protected void getMove(int i) {
        ++turn_count;
        BaseMod.logger.info("Lamp NUM"+this.maxHealth);
        BaseMod.logger.info("Lamp draw x"+this.drawX);
        BaseMod.logger.info("Lamp draw y"+this.drawY);
        BaseMod.logger.info("AbstractDungeon.floorY"+AbstractDungeon.floorY);
        BaseMod.logger.info("Settings.yScale"+Settings.yScale);
        BaseMod.logger.info("Lamp x"+((this.drawX-(float)Settings.WIDTH * 0.75F))/Settings.xScale);
        BaseMod.logger.info("Lamp y"+((this.drawY-AbstractDungeon.floorY)/Settings.yScale));
        if (this.turn_count == this.light_turn) {
            this.setMove((byte)3, Intent.UNKNOWN);
        }else {
            this.setMove((byte) 2,Intent.NONE);
        }
        if (turn_count>1) {
            this.addToBot(new DamageAction(this, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
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

    public void damage (DamageInfo info){
        super.damage(info);
        if (info.owner == AbstractDungeon.player){
            info.output = 0;
        }else {
            info.output = 1;
        }
    }
    public void renderHealth(SpriteBatch sb) {

    }

    public void renderPowerTips(SpriteBatch sb) {

    }

    public void renderTip(SpriteBatch sb) {

    }
}
