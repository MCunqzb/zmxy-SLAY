package monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import vfx.ColourfulLightingEffect;

public class MonkeyKingPhantom extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:MonkeyKingPhantom";
    public static final String IMG = "img/monsters/monkeykingphantom.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:MonkeyKingPhantom");
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;
    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 10;
    private static final int HP_MAX = 12;
    private static final int A_2_HP_MIN = 12;
    private static final int A_2_HP_MAX = 16;
    private static final int T_DMG = 12;
    private static final int T_T_DMG = 1;
    private static final int FURY_HITS = 3;
    private static final int A_2_T_DMG = 14;
    private static final int A_2_T_T_DMG = 3;
    private int tDmg;
    private int t_t_Dmg;
    private int t_t_Hit;
    private int strengthAmt;
    private int blockAmount;
    private int BLOCK_AMOUNT = 8;
    private int A_17_BLOCK_AMOUNT = 20;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;
    public MonkeyKingPhantom() {
        this(0.0F, 0.0F);
    }
    public MonkeyKingPhantom(float x, float y) {
        super(NAME, ID, 10, 0.0F, 0.0F, 150.0F, 280.0F, IMG,x,y);
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
        switch(this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, 7));
            default:
        }
    }

    @Override
    protected void getMove(int i) {
        this.setMove((byte)1, Intent.ATTACK, 7);
    }

    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("CHOSEN_DEATH");
    }
}
