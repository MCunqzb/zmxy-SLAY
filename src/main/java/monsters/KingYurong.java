package monsters;

import Helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class KingYurong extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:KingYurong";
    public static final String IMG = "img/monsters/kingyurong.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:KingYurong");
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 60;
    private static final int HP_MAX = 76;
    private static final int A_2_HP_MIN = 64;
    private static final int A_2_HP_MAX = 80;
    private static final int T_DMG = 12;
    private static final int T_T_DMG = 1;
    private static final int FURY_HITS = 3;
    private static final int A_2_T_DMG = 15;
    private static final int A_2_T_T_DMG = 2;
    private int tDmg;
    private int t_t_Dmg;
    private int t_t_Hit;
    private int strengthAmt;
    private int blockAmount;
    private int BLOCK_AMOUNT = 15;
    private int A_17_BLOCK_AMOUNT = 20;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;
    public KingYurong() {
        this(0.0F, 0.0F);
    }
    public KingYurong(float x, float y) {//Elite
        super(NAME, ID, 70, 0.0F, 0.0F, 120.0F, 210.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(HP_MIN, HP_MAX);
        } else {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.tDmg = A_2_T_DMG;
            this.t_t_Dmg = A_2_T_T_DMG;
            this.t_t_Hit=6;
        } else {
            this.tDmg = T_DMG;
            this.t_t_Dmg = T_T_DMG;
            this.t_t_Hit=5;
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.strengthAmt = 2;
        } else {
            this.strengthAmt = 1;
        }
        this.damage.add(new DamageInfo(this, this.tDmg));
        this.damage.add(new DamageInfo(this, this.t_t_Dmg));
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:

                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                this.addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.05F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new StrengthPower(this,this.strengthAmt),this.strengthAmt));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                int i = 0;
                while(true) {
                    if (i >= t_t_Hit) {
                        break label;
                    }
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale), 0.2F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
                    ++i;
                }

        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.setMove(MOVES[1], (byte)2, Intent.BUFF);
        }else {
            if (this.lastMove((byte)2)){
                this.setMove(MOVES[2],(byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
            }
            if (this.lastTwoMoves((byte)1)){
                this.setMove(MOVES[1], (byte)2, Intent.BUFF);
            }
            else {
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            }
        }
    }

    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("CHOSEN_DEATH");
    }
}
