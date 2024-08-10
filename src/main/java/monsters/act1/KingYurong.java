package monsters.act1;

import Helpers.ModHelper;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import vfx.ColourfulLightingEffect;

public class KingYurong extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:KingYurong";
    public static final String IMG = "img/monsters/kingyurong.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:KingYurong");
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;
    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 110;
    private static final int HP_MAX = 120;
    private static final int A_2_HP_MIN = 120;
    private static final int A_2_HP_MAX = 130;
    private static final int DMG1 = 15;
    private static final int DMG2 = 2;
    private static final int FURY_HITS = 3;
    private static final int A_2_DMG1 = 17;
    private static final int A_2_DMG2 = 3;
    private int Dmg1;
    private int Dmg2;
    private int HitNum;
    private int strengthAmt;
    private int blockAmount;
    private int BLOCK_AMOUNT = 8;
    private int A_17_BLOCK_AMOUNT = 20;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;
    public KingYurong() {
        this(0.0F, 0.0F);
    }
    public KingYurong(float x, float y) {//Elite
        super(NAME, ID, 80, 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.Dmg1 = A_2_DMG1;
            this.Dmg2 = A_2_DMG2;
            this.HitNum=6;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
            this.HitNum=5;
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.strengthAmt = 2;
        } else {
            this.strengthAmt = 1;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.strengthAmt, true), this.strengthAmt));
                this.addToBot(new WaitAction(0.25F));
                AbstractGameEffect LIGHT_ = new VfxBuilder(ImageMaster.vfxAtlas.findRegion("combat/lightning"),0.8f)
                        .scale(1.5f, 1f, VfxBuilder.Interpolations.SWING)
                        .moveY(p.drawY,p.drawY, VfxBuilder.Interpolations.EXP5IN)
                        .moveX(p.drawX,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .playSoundAt(0.35F,"ORB_LIGHTNING_EVOKE")
                        .setColor(Color.GOLDENROD)
                        .build();
                this.addToBot(new VFXAction(LIGHT_));
                if (turn_count==1){
                    this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
                }
                break;
            case 2:
                this.addToBot(new WaitAction(0.25F));
                this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,this.strengthAmt),this.strengthAmt));
                break;
            case 3:
                this.addToBot(new GainBlockAction(this,BLOCK_AMOUNT));
                this.addToBot(new WaitAction(0.15F));
                int i = 0;
                while(true) {
                    if (i >= HitNum) {
                        break label;
                    }
                    AbstractGameEffect LIGHT = new VfxBuilder(ImageMaster.vfxAtlas.findRegion("combat/lightning"),0.8f)
                            .scale(1.5f, 1f, VfxBuilder.Interpolations.SWING)
                            .moveY(p.drawY,p.drawY, VfxBuilder.Interpolations.EXP5IN)
                            .moveX(p.drawX,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                            .playSoundAt(0.35F,"ORB_LIGHTNING_EVOKE")
                            .setColor(new Color(0.5F, 0.1F, 0.7F, 0.0F))
                            .build();
                    this.addToBot(new VFXAction(LIGHT));
                    this.addToBot(new DamageAction(p, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
                    ++i;
                }

        }

        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {

        if (AbstractDungeon.ascensionLevel >= 18 && turn_count == 0) {
            this.setMove(MOVES[1], (byte)2, Intent.BUFF);
        }else {
            if (this.lastMove((byte)2)){
                this.setMove(MOVES[2],(byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base,this.HitNum,true);
            }
            else if (this.lastTwoMoves((byte)1)){
                this.setMove(MOVES[1], (byte)2, Intent.BUFF);
            }
            else {
                this.setMove(MOVES[0],(byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            }
        }
        ++turn_count;
    }

    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("CHOSEN_DEATH");
    }
}
