package monsters.act1;

import actions.WaitActionPassFast;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import powers.IceThornPower;
import powers.ReboundDamagePower;

public class Loong extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:Loong";
    public static final String IMG = "img/monsters/loong.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:Loong");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String ICE = "img/vfxs/ice.png";
    public static final String ICE2 = "img/vfxs/ice2.png";
    public static final String RAIN = "img/vfxs/ice_chip.png";


    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 125;
    private static final int HP_MAX = 125;
    private static final int A_2_HP_MIN = 150;
    private static final int A_2_HP_MAX = 150;
    private static final int DMG1 = 8;
    private static final int DMG2 = 3;
    private static final int DMG3 = 21;
    private static final int MAGIC_NUM  = 7;
    private static final int A_2_MAGIC_NUM  = 9;
    private static final int BLOCK_NUM  = 10;
    private static final int HITS = 6;
    private static final int A_2_DMG1 = 10;
    private static final int A_2_DMG2  = 4;
    private static final int A_2_DMG3  = 24;
    private static final int A_2_BLOCK_NUM  = 7;

    private int dmg1;
    private int dmg2;
    private int dmg3;
    private int hitTime;
    private int magic_num;
    private int block;

    public Loong() {
        this(0.0F, 0.0F);
    }
    public Loong(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
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

    public void usePreBattleAction() {


    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                //x b
                AbstractGameEffect I = new VfxBuilder(ImageMaster.loadImage(ICE), 0.5f)
                        .scale(3.5F, 4.0f, VfxBuilder.Interpolations.SWING)
                        .moveX(this.drawX,p.drawX, VfxBuilder.Interpolations.POW2OUT_INVERSE)
                        .moveY(p.drawY+25F,p.drawY+25F, VfxBuilder.Interpolations.POW2OUT_INVERSE)
                        .playSoundAt(0.35F,"ORB_FROST_EVOKE")
                        .build();
                this.addToBot(new VFXAction(I));
                this.addToBot(new WaitActionPassFast(0.5F));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                //qnbx
                for (int j=0;j<5;j++) {
                    AbstractGameEffect R = new VfxBuilder(ImageMaster.loadImage(RAIN), 0.3f)
                            .scale(1.8f, 2.0f, VfxBuilder.Interpolations.SWING)
                            .moveX(p.drawX + (100 + (float) Math.random() * 200), p.drawX + (-50 - (float) Math.random() * 100), VfxBuilder.Interpolations.EXP5IN)
                            .moveY(p.drawY + 900f, p.drawY - 10f, VfxBuilder.Interpolations.BOUNCEOUT)
                            .build();
                    this.addToBot(new VFXAction(R));

                }
                this.addToBot(new WaitActionPassFast(0.1F));
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(p,this,new AttackBurnPower(p,1)));
                this.addToBot(new ApplyPowerAction(p,this,new WeakPower(p,2,true)));
                break;
            case 3:
                //slht
                this.addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.5F));
                this.addToBot(new DamageAction(p, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                this.addToBot(new ApplyPowerAction(this,this,new IceThornPower(this,magic_num,true)));
                break;
            case 4:
                int i = 0;
                while(true) {
                    if (i >= this.HITS) {
                        break label;
                    }
                    AbstractGameEffect I2 = new VfxBuilder(ImageMaster.loadImage(ICE2),p.hb.cX+(float) (500F*Math.cos(i*180)),this.drawY, 0.5f)
                            .scale(1.5F, 2f, VfxBuilder.Interpolations.SWING)
                            .playSoundAt(0.35F,"ORB_FROST_CHANNEL")
                            .build();
                    this.addToBot(new VFXAction(I2));
                    this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    i++;
                }


        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if (AbstractDungeon.ascensionLevel <= 19){
            if ((AbstractDungeon.aiRng.randomBoolean(0.3F) && !lastMove((byte) 4))){//0.3
                this.setMove(MOVES[4],(byte) 4, Intent.ATTACK, (this.damage.get(1)).base,this.HITS,true);//0.25
            }else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 1)){//0.28
                this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 2)){//0.21
                this.setMove(MOVES[2],(byte) 2, Intent.ATTACK_DEBUFF, (this.damage.get(1)).base);
            }else {//0.21
                this.setMove(MOVES[3],(byte) 3, Intent.ATTACK_BUFF, (this.damage.get(2)).base);
            }
        }else {
            if ((AbstractDungeon.aiRng.randomBoolean(0.25F) && !lastMove((byte) 4))){
                this.setMove(MOVES[4],(byte) 4, Intent.ATTACK, (this.damage.get(1)).base,this.HITS,true);//0.25
            }else if (AbstractDungeon.aiRng.randomBoolean(0.33F) && !lastTwoMoves((byte) 1)){//0.25
                this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base);
            }else if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 2)){//0.25
                this.setMove(MOVES[2],(byte) 2, Intent.ATTACK_DEBUFF, (this.damage.get(1)).base);
            }else {//0.25
                this.setMove(MOVES[3],(byte) 3, Intent.ATTACK_BUFF, (this.damage.get(2)).base);
            }
        }
        if (turn_count==0){
            this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
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
