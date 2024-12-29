package monsters.act1;

import cards.BlownSand;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateJumpAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import powers.BlownSandPower;

public class KingLionCamel extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:KingLionCamel";
    public static final String IMG = "img/monsters/kinglioncamel.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:KingLionCamel");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 110;
    private static final int HP_MAX = 120;
    private static final int A_2_HP_MIN = 115;
    private static final int A_2_HP_MAX = 129;
    private static final int DMG1 = 5;
    private static final int DMG2 = 11;
    private static final int DMG3 = 20;
    private static final int MAGIC_NUM  = 3;
    private static final int A_2_MAGIC_NUM  = 5;
    private static final int BLOCK_NUM  = 10;
    private static final int HITS = 2;
    private static final int A_2_DMG1 = 6;
    private static final int A_2_DMG2  = 12;
    private static final int A_2_DMG3  = 22;
    private static final int A_2_BLOCK_NUM  = 7;

    private int dmg1;
    private int dmg2;
    private int dmg3;
    private int hitTime;
    private int magic_num;
    private int block;
    public KingLionCamel() {
        this(0.0F, 0.0F);
    }
    public KingLionCamel(float x, float y) {//Elite
        super(NAME, ID, HP_MAX, 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
            this.block=A_2_BLOCK_NUM;
            } else {
                setHp(HP_MIN, HP_MAX);
                this.block=BLOCK_NUM;
            }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.dmg1 = A_2_DMG1;
            this.dmg2 = A_2_DMG2;
            this.dmg3 = A_2_DMG3;
            } else {
                this.dmg1 = DMG1;
                this.dmg2 = DMG2;
                this.dmg3 = DMG3;
            }
        this.hitTime=HITS;
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.magic_num = A_2_MAGIC_NUM;
        } else {
            this.magic_num = MAGIC_NUM;
        }
        this.damage.add(new DamageInfo(this, this.dmg1));
        this.damage.add(new DamageInfo(this, this.dmg2));
        this.damage.add(new DamageInfo(this, this.dmg3));
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:

                this.addToBot(new WaitAction(0.15F));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                this.addToBot(new GainBlockAction(this,this,this.block));
                break;
            case 3:
                if (turn_count==1){
                    this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
                }
                this.addToBot(new ApplyPowerAction(p,this,new PoisonPower(p,this,this.magic_num),this.magic_num));
                this.addToBot(new ApplyPowerAction(p,this,new VulnerablePower(p,2,true),2));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(this));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.5F, 0.1F, 0.6F, 0.5F))));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                this.addToBot(new DamageAction(p, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (AbstractDungeon.ascensionLevel >= 18) {
            if (this.lastMove((byte)3) ){
                this.setMove(MOVES[3],(byte)4, Intent.ATTACK, this.damage.get(2).base);
            }
            else if (this.lastMove((byte)4) ){
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK, this.damage.get(0).base,this.hitTime,true);
            }
            else if (this.lastMove((byte)1) ){
                this.setMove(MOVES[1], (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
            }
            else {
                this.setMove(MOVES[2], (byte)3,Intent.STRONG_DEBUFF);
            }
        }else {
            if (this.lastMove((byte)3) ){
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK, this.damage.get(0).base,this.hitTime,true);
            }
            else if (this.lastMove((byte)1) && !this.lastMoveBefore((byte)1)){
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK, this.damage.get(0).base,this.hitTime,true);
            }
            else if (this.lastTwoMoves((byte)1)){
                this.setMove(MOVES[1], (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
            }
            else if (this.lastMove((byte)2)){
                this.setMove(MOVES[3],(byte)4, Intent.ATTACK, this.damage.get(2).base);
            }
            else {
                    this.setMove(MOVES[2], (byte)3,Intent.STRONG_DEBUFF);
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
        this.playDeathSfx();
    }
}