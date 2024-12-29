package monsters.act2;

import actions.SpawnSharkWithPowerAction;
import actions.WaitActionPassFast;
import basemod.BaseMod;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import monsters.act1.boss.Shark;
import monsters.act2.boss.Gear;
import powers.BloodSeaArmorPower;
import powers.ChasingGhostPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class QinGuangKing extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:QinGuangKing";
    public static final String FROM1 = "img/monsters/qinguangking.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:QinGuangKing");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String POISONBALL = "img/vfxs/poison_ball.png";
    public static final String DAMAGEBACK = "img/vfxs/ghost.png";
    public static final String SHARK_DOWN = "img/vfxs/shark_down.png";
    public static final String WIND = "img/vfxs/wind.png";
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 140;
    private static final int HP_MAX = 148;
    private static final int A_HP_MIN = 145;
    private static final int A_HP_MAX = 155;
    private static final int DMG1 = 6;
    private static final int A_DMG1 = 7;
    private static final int DMG2 = 16;
    private static final int A_DMG2  = 18;
    private static final int DMG3 = 3;
    private static final int A_DMG3  = 3;
    private static final int HITS = 3;
    private static final int A_HITS = 3;
    private static final int BLOCK1 = 4;
    private static final int A_BLOCK1 = 6;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 2;
    private static final int MGC2 = 2;
    private static final int A_MGC2 = 1;
    private boolean recover = false;
    private int Dmg1;
    private int Dmg2;
    private int Dmg3;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int b1;
    private int b2;
    public int turn_count = 0 ;
    private int strength_num = 0 ;
    private int not_remove ;
    private boolean shark;
    public AbstractMonster[] uav = new AbstractMonster[3];
    public static  float[] POSX={-366.0F, -170.0F, -532.0F};
    public static  float[] POSY={-4.0F, 6.0F, 0.0F};


    public QinGuangKing() {
        this(0.0F, 0.0F);
    }
    public QinGuangKing(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, FROM1,x,y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(A_HP_MIN, A_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);

        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.Dmg1 = A_DMG1;
            this.Dmg2 = A_DMG2;
            this.Dmg3 = A_DMG3;
            this.b1 =A_BLOCK1;
            this.b2 =A_BLOCK2;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
            this.Dmg3 = DMG3;
            this.b1 =BLOCK1;
            this.b2 =BLOCK2;

        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.magicAmt1 = A_MGC1;
            this.magicAmt2 = A_MGC2;
            this.HitTime=A_HITS;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
            this.HitTime=HITS;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
        this.damage.add(new DamageInfo(this, this.Dmg3));
        this.damage.add(new DamageInfo(this, this.b1));
        this.damage.add(new DamageInfo(this, this.b2));
        this.recover = false;
        this.not_remove = 0;

    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("zmxy/zmxy2-2.ogg");
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                //attack hits
                int i = 0;
                while(true) {
                    if (i >= this.HitTime) {
                        break label;
                    }
                    AbstractGameEffect P_B = new VfxBuilder(ImageMaster.loadImage(POISONBALL), 0.5f)
                            .scale(4.0f, 4.0f, VfxBuilder.Interpolations.SWING)
                            .moveX((float) (this.drawX+Math.random()*-200.0F+100F), (float) (p.drawX+Math.random()*-200.0F+100F), VfxBuilder.Interpolations.SWINGIN)
                            .moveY((float) (this.drawY+Math.random()*-200.0F+100F), (float) (p.drawY+Math.random()*-200.0F+100F), VfxBuilder.Interpolations.SWINGOUT)
                            .build();
                    this.addToBot(new VFXAction(P_B));
                    this.addToBot(new WaitActionPassFast(0.5F));
                    this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.POISON));
                    i++;
                }
            case 2:
                //buff
                AbstractGameEffect D_B = new VfxBuilder(ImageMaster.loadImage(DAMAGEBACK), 1.0f)
                        .scale(1.5f, 4.0f, VfxBuilder.Interpolations.SWING)
                        .moveX(this.drawX,this.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(this.drawY+25f,this.drawY+200f, VfxBuilder.Interpolations.EXP5IN)
                        .playSoundAt(0.35F,"ATTACK_MAGIC_BEAM")
                        .build();
                this.addToBot(new VFXAction(D_B));
                this.addToBot(new ApplyPowerAction(this,this,new ChasingGhostPower(this,1,true)));
                this.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this,this.magicAmt1)));
                this.addToBot(new GainBlockAction(this,this.b1));
                break;
            case 3:
                //smooth
                int j = 0;
                List<Integer> UAVXPositions = new ArrayList<>();
                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var2.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var2.next();
                    if (mo instanceof UAV && !mo.isDying){
                        j++;
                        BaseMod.logger.info("UAV num"+j);
                        BaseMod.logger.info("UAV draw X"+mo.drawX);
                        UAVXPositions.add((int) Math.floor(mo.drawX));
                        BaseMod.logger.info("UAV"+UAVXPositions);
                        BaseMod.logger.info("Settings.xScale"+Settings.xScale);
                        BaseMod.logger.info("Settings.WIDTH"+Settings.WIDTH);
                    }
                }
                //this.drawX = (float)Settings.WIDTH * 0.75F + offsetX * Settings.xScale;
                //this.drawY = AbstractDungeon.floorY + offsetY * Settings.yScale;
                boolean P1 = UAVXPositions.contains((int)(float)Settings.WIDTH * 0.75F - 532.0 * Settings.xScale);//908 = (float)Settings.WIDTH * 0.75F + 532.0 * Settings.xScale
                boolean P2 = UAVXPositions.contains((int)(float)Settings.WIDTH * 0.75F - 366.0 * Settings.xScale);
                boolean P3 = UAVXPositions.contains((int)(float)Settings.WIDTH * 0.75F - 150.0 * Settings.xScale);
                BaseMod.logger.info("P1:"+P1+";P2:"+P2+";P3:"+P3);
                if (this.numAliveUAV()==0) {
                    this.addToBot(new SpawnMonsterAction(new UAV((-170.0F), (float) (150.0F + Math.random() *100.F)), true));// 1270 p3
                    this.addToBot(new SpawnMonsterAction(new UAV((-366.0F), (float) (150.0F + Math.random() * 100.F)), true));// 1074 p2
                    //this.addToBot(new SpawnMonsterAction(new UAV(-532.0F, (float) (150.0F + Math.random() * -80.F)), true));// 908 p1
                }else {
                    if (this.numAliveUAV()==1) {
                        if (P2){
                            this.addToBot(new SpawnMonsterAction(new UAV(-532.0F, (float) (150.0F + Math.random() * -80.F)), true));// 756 p1
                            this.addToBot(new SpawnMonsterAction(new UAV(-170.0F, (float) (150.0F + Math.random() * 100.F)), true));// 1058 p3
                        }else if (P3){
                            this.addToBot(new SpawnMonsterAction(new UAV(-366.0F, (float) (150.0F + Math.random() *100.F)), true));// 895 p2
                            this.addToBot(new SpawnMonsterAction(new UAV(-532.0F, (float) (150.0F + Math.random() * -80.F)), true));// 756
                        } else {
                            this.addToBot(new SpawnMonsterAction(new UAV(-170.0F, (float) (150.0F + Math.random() * 100.F)), true));// 1058 p3
                            this.addToBot(new SpawnMonsterAction(new UAV(-366.0F, (float) (150.0F + Math.random() * 100.F)), true));// 895 p2
                        }
                    }else if (this.numAliveUAV()==2) {
                        if (P2 && P3) {
                            this.addToBot(new SpawnMonsterAction(new UAV(-532.0F, (float) (150.0F + Math.random() * -80.F)), true));//756 p1
                        }else if (P1 && P2) {
                            this.addToBot(new SpawnMonsterAction(new UAV((-170.0F), (float) (150.0F + Math.random() * 100.F)), true));// 1058 p3
                        }else if (P1 && P3){
                            this.addToBot(new SpawnMonsterAction(new UAV((-366.0F), (float) (150.0F + Math.random() * 100.F)), true));// 895 p2
                        }
                    }
                }
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (AbstractDungeon.ascensionLevel >= 18){
            if (this.numAliveUAV()==0){
                if (AbstractDungeon.aiRng.randomBoolean(0.55F)){
                    this.setMove(MOVES[3],(byte) 3, Intent.UNKNOWN);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.6F) && !lastTwoMoves((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }else if (this.numAliveUAV()==1){
                if (AbstractDungeon.aiRng.randomBoolean(0.45F)){
                    this.setMove(MOVES[3],(byte) 3, Intent.UNKNOWN);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }else if (this.numAliveUAV()==2){
                if (AbstractDungeon.aiRng.randomBoolean(0.2F)){
                    this.setMove(MOVES[3],(byte) 3, Intent.UNKNOWN);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }else {
                if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }
        }else {
            if (this.numAliveUAV()==0){
                if (AbstractDungeon.aiRng.randomBoolean(0.65F)){
                    this.setMove(MOVES[3],(byte) 3, Intent.UNKNOWN);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.6F) && !lastTwoMoves((byte) 2) && !lastMove((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }else if (this.numAliveUAV()==1){
                if (AbstractDungeon.aiRng.randomBoolean(0.5F)){
                    this.setMove(MOVES[3],(byte) 3, Intent.UNKNOWN);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 2) && !lastMove((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }else if (this.numAliveUAV()==2){
                if (AbstractDungeon.aiRng.randomBoolean(0.4F)){
                    this.setMove(MOVES[3],(byte) 3, Intent.UNKNOWN);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 2) && !lastMove((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }else {
                if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 2) && !lastMove((byte) 2)){
                    this.setMove(MOVES[2],(byte) 2, Intent.DEFEND_BUFF);
                }else {
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
            }
        }
        ++not_remove;
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
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.playDeathSfx();
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                    AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
                }
            }
        }
    }

    private int numAliveUAV() {
        int count = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var2.hasNext()) {
            AbstractMonster m = (AbstractMonster)var2.next();
            if (m != null && m != this && !m.isDying && m instanceof UAV) {
                ++count;
            }
        }

        return count;
    }




}