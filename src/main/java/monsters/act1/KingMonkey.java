package monsters.act1;

import cards.BlownSand;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import powers.BlownSandPower;

public class KingMonkey extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:KingMonkey";
    public static final String IMG = "img/monsters/kingmonkey.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:KingMonkey");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;
    public static KingMonkey bg;

    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 110;
    private static final int HP_MAX = 120;
    private static final int A_2_HP_MIN = 120;
    private static final int A_2_HP_MAX = 130;
    private static final int DMG1 = 18;
    private static final int DMG2 = 3;
    private static final int HITS = 4;
    private static final int A_2_DMG1 = 21;
    private static final int A_2_DMG2  = 4;
    private int Dmg1;
    private int Dmg2;
    private int HitTime;
    private int strengthAmt;
    private int magic;
    private int BLOCK_AMOUNT = 9;
    private int A_17_BLOCK_AMOUNT = 20;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;
    public KingMonkey() {
        this(0.0F, 0.0F);
    }
    public KingMonkey(float x, float y) {
        //Elite
        super(NAME, ID, 80, 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
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
            this.strengthAmt = 2;
            this.magic= 5;
        } else {
            this.strengthAmt = 1;
            this.magic= 4;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
        bg = this;
    }


    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                this.addToBot(new GainBlockAction(this,BLOCK_AMOUNT));
                break;
            case 2:
                this.addToBot(new SFXAction("VO_NEMESIS_1C"));
                this.addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, new Color(1F,1F,1F,0.5F), ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
                this.addToBot(new WaitAction(0.25F));
                //this.addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new FrailPower(AbstractDungeon.player,this.strengthAmt,true),this.strengthAmt));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new VulnerablePower(AbstractDungeon.player,this.strengthAmt,true),this.strengthAmt));
                this.addToBot(new MakeTempCardInDiscardAction(new BlownSand(), this.magic));

                break;
            case 3:
                this.addToBot(new WaitAction(0.15F));
                for(int i = 0; i < this.HitTime; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
                    this.addToBot(new DamageAction(p, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
                }
                break;
            case 4:
                this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
                this.addToBot(new ApplyPowerAction(this,this,new BlownSandPower(this,4),4));
                break;

        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (AbstractDungeon.ascensionLevel >= 18 && !this.hasPower(BlownSandPower.POWER_ID)) {
            this.setMove(MOVES[3], (byte) 4,Intent.BUFF);
        }else {
            if (this.lastMove((byte)2)){
                this.setMove(MOVES[2],(byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base,this.HitTime,true);
            }
            else if (this.lastTwoMoves((byte)1)){
                this.setMove(MOVES[1], (byte)2, Intent.STRONG_DEBUFF);
            }
            else {
                if (!this.hasPower(BlownSandPower.POWER_ID)) {
                    this.setMove(MOVES[3], (byte) 4,Intent.BUFF);
                }else {
                    this.setMove(MOVES[0], (byte) 1, Intent.ATTACK_DEFEND, ((DamageInfo) this.damage.get(0)).base);
                }
            }
        }

    }

    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("VO_SLAVERRED_2A");
    }
}
