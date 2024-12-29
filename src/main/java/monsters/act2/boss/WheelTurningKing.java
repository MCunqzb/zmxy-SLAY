package monsters.act2.boss;

import basemod.BaseMod;
import basemod.helpers.VfxBuilder;
import cards.GearCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import powers.GearAttackPlayerPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WheelTurningKing extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:WheelTurningKing";

    public static final String GEAR = "img/monsters/gear.png";
    public static final String BLUE_GEAR_VFX = "img/vfxs/blue_gear.png";
    private static TextureAtlas.AtlasRegion gear = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(GEAR), 0, 0, 462, 462);
    public static final String BIG_GEAR = "img/vfxs/big_gear.png";
    public static final String MAGIC_GEAR_VFX = "img/vfxs/magic_gear.png";
    private static TextureAtlas.AtlasRegion big_gear = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(BIG_GEAR), 0, 0, 154, 137);
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:WheelTurningKing");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;


    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 150;
    private static final int HP_MAX = 155;
    private static final int A_HP_MIN = 160;
    private static final int A_HP_MAX = 165;
    private static final int DMG1 = 7;
    private static final int A_DMG1 = 9;
    private static final int DMG2 = 12;
    private static final int A_DMG2  = 14;
    private static final int DMG3 = 12;
    private static final int A_DMG3  = 14;
    private static final int HITS = 2;
    private static final int A_HITS = 6;
    private static final int BLOCK1 = 11;
    private static final int A_BLOCK1 = 11;
    private static final int BLOCK2 = 9;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 2;
    private static final int MGC2 = 2;
    private static final int A_MGC2 = 3;
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
    private int gear_generate= 0 ;
    private int from ;
    private boolean dialogue = false;
    private int MOVE1and2 = 0;
    private int MOVE2and3 = 0;
    private int MOVE4 = 0;
    private boolean MOVE5 = false;


    public WheelTurningKing() {
        this(0.0F, 0.0F);
    }
    public WheelTurningKing(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, null,x,y);
        this.loadAnimation("img/monsters/wheel_turning_king_from1.atlas","img/monsters/wheel_turning_king_from137.json",1F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "gear_6", true);
        e.setTime(e.getEndTime() * MathUtils.random());
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
        if (AbstractDungeon.ascensionLevel >= 19) {
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
        this.from = 1;


    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("zmxy/act2boss.ogg");
        AbstractDungeon.getCurrRoom().cannotLose = true;
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch (this.nextMove) {
            case 1:
                //generate gear -1 strength
                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, -1)));
                if (gear_generate == 0) {
                    AnimationState.TrackEntry e ;
                    e = this.state.setAnimation(0, "gear_3", true);
                    e.setTime(e.getEndTime() * MathUtils.random());
                    gear_generate++;
                    int i = 0;
                    while (true) {
                        if (i >= 3) {
                            break label;
                        }
                        this.addToBot(new SpawnMonsterAction(new Gear(450F*(float) (Math.cos(Math.toRadians(180F-i * 30F))), 450F * (float) (Math.sin(Math.toRadians(180F-i * 30F))), 2.0f, false, false), true));
                        i++;
                    }
                } else if (gear_generate == 1) {
                    gear_generate++;
                    AnimationState.TrackEntry e ;
                    e = this.state.setAnimation(0, "no_gear", true);
                    e.setTime(e.getEndTime() * MathUtils.random());
                    int i = 0;
                    while (true) {
                        if (i >= 3) {
                            break label;
                        }
                        this.addToBot(new SpawnMonsterAction(new Gear(700F*(float) (Math.cos(Math.toRadians(180F-i * 20F))), 700F * (float) (Math.sin(Math.toRadians(180F-i * 20F))), 2.0f, false, false), true));
                        i++;
                    }
                }
            case 2:
                //attack hits
                AbstractGameEffect GEARVFX = new VfxBuilder(gear,0.8f)
                        .scale(0.8f, 2.2f, VfxBuilder.Interpolations.SWING)
                        .moveX(p.drawX+200F,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(p.drawY+10F,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .rotate(-400f)
                        .build();
                this.addToBot(new VFXAction(GEARVFX ));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractGameEffect GEARVFX1 = new VfxBuilder(gear, 0.8f)
                        .scale(0.8f, 2.2f, VfxBuilder.Interpolations.SWING)
                        .moveX(p.drawX-200F,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(p.drawY+10F,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .rotate(-400f)
                        .build();
                this.addToBot(new VFXAction(GEARVFX1));
                this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break ;
            case 3:
                //defend
                this.addToBot(new GainBlockAction(this,this.b1));
                AbstractGameEffect big = new VfxBuilder(big_gear,1.5f)
                        .scale(2f, 3f, VfxBuilder.Interpolations.SWING)
                        .moveX(this.drawX,this.drawX+0.5F, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(this.drawY,this.drawY+5f, VfxBuilder.Interpolations.EXP5IN)
                        .build();
                this.addToBot(new VFXAction(big));
                this.addToBot(new ApplyPowerAction(this,this,new PlatedArmorPower(this,this.magicAmt1+3)));
                break ;
            case 4:
                //Before absorb
                if (!dialogue)
                    this.addToBot(new ShoutAction(this, DIALOG[1]));
                this.dialogue=true;
                break ;
            case 5:
                int strength = 0;
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var1.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var1.next();
                    if (mo instanceof Gear && !mo.isDying){
                        strength++;
                        AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(mo));
                        AbstractDungeon.actionManager.addToTop(new SuicideAction(mo));
                        AbstractDungeon.actionManager.addToTop(new VFXAction(mo, new InflameEffect(mo), 0.2F));
                    }

                }
                BaseMod.logger.info("Gear Num"+strength);
                if (strength > 0 && from==1){
                    AnimationState.TrackEntry e ;
                    e = this.state.setAnimation(0, "gear_absorb", true);
                    e.setTime(e.getEndTime() * MathUtils.random());
                    BaseMod.logger.info("absorb_gear animation");
                }
                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strength)));
                break ;
            case 6:
                //recover
                this.loadAnimation("img/monsters/big_wheel_turning_king.atlas","img/monsters/big_wheel_turning_king37.json",1F);
                AnimationState.TrackEntry e = this.state.setAnimation(0, "gear_9", true);
                e.setTime(e.getEndTime() * MathUtils.random());
                this.recover =true;
                this.maxHealth = AbstractDungeon.monsterHpRng.random(this.maxHealth+7, this.maxHealth+10);
                this.addToBot(new HealAction(this, this, this.maxHealth));
                this.from = 2;
                ((AbstractPlayer) p).movePosition((float) Settings.WIDTH / 2.0F, 280.0F * Settings.yScale);
                movePosition((float) Settings.WIDTH / 2.0F, 550.0F * Settings.yScale);
                this.updateHitbox(0.0F, 50.0F, 800.0F, 180.0F);
                AbstractDungeon.getCurrRoom().cannotLose = false;
                this.halfDead = false;
                this.addToBot(new MakeTempCardInDrawPileAction(new GearCard(), 1, true, true));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new GearAttackPlayerPower(AbstractDungeon.player)));
                break ;
            case 7:
                //strong debuff
                AbstractGameEffect BLUE_GEAR = new VfxBuilder(ImageMaster.loadImage(BLUE_GEAR_VFX), 0.8f)
                        .scale(2f, 2.2f, VfxBuilder.Interpolations.SWING)
                        .moveX(p.drawX+500F,p.drawX-500F, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(p.drawY+1F,p.drawY-1F, VfxBuilder.Interpolations.EXP5IN)
                        .rotate(1080f)
                        .playSoundAt(0.35F,"ATTACK_MAGIC_BEAM")
                        .build();
                this.addToBot(new VFXAction(BLUE_GEAR));
                this.addToBot(new ApplyPowerAction(p,this,new WeakPower(p,this.magicAmt1,true)));
                this.addToBot(new ApplyPowerAction(p,this,new DrawReductionPower(p,1)));
                break ;
            case 8:
                //attack hits
                int i = 0;
                while (true) {
                    if (i >= this.HitTime) {
                        break label;
                    }
                    float r = (float) (200f*Math.random())-100F;
                    AbstractGameEffect MAGIC_GEAR = new VfxBuilder(ImageMaster.loadImage(MAGIC_GEAR_VFX),0.8f)
                            .scale(1.5f, 1f, VfxBuilder.Interpolations.SWING)
                            .moveY(p.drawY+400F,p.drawY-400F, VfxBuilder.Interpolations.EXP5IN)
                            .moveX(p.drawX+r,p.drawX-r, VfxBuilder.Interpolations.EXP5IN)
                            .rotate(1080f)
                            .playSoundAt(0.35F,"MONSTER_GUARDIAN_DESTROY")
                            .build();
                    this.addToBot(new VFXAction(MAGIC_GEAR));
                    this.addToBot(new DamageAction(p, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    i++;
                }
            case 9:
                this.addToBot(new ApplyPowerAction(this, this, new WeakPower(this, 1,true)));
                //generate gear from2
                List<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
                List<Integer> gearXPositions = new ArrayList<>();
                int j = 0;
                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var2.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var2.next();
                    if (mo instanceof Gear && !mo.isDying){
                        j++;
                        BaseMod.logger.info("Gear draw X"+(int)(mo.drawX));
                        gearXPositions.add((int)(mo.drawX));
                        BaseMod.logger.info("Gear x positions"+gearXPositions);
                        //this.drawX = (float)Settings.WIDTH * 0.75F + offsetX * Settings.xScale;
                        //240=1920*0.75-1200
                    }
                }
                if (j <= 5) {
                    float y = 30f;
                    if (!gearXPositions.isEmpty()) {
                        List<Integer> resultList = new ArrayList<>();
                        int first_position = (int) (Settings.WIDTH * 0.75F + (-1200F) * Settings.xScale);
                        float space = 200 * Settings.xScale;
                        BaseMod.logger.info("first_position "+first_position);
                        BaseMod.logger.info("space "+space);
                        for (float position : gearXPositions) {
                            int result = (int)((position - first_position) / space);
                            resultList.add(result);
                        }
                        BaseMod.logger.info("Gear INT"+resultList);
                        int k=0;
                        for(int l=0;l<8;l++){
                            if (!resultList.contains(l)){
                                this.addToBot(new SpawnMonsterAction(new Gear(200F * l - 1200F, y, 2f, AbstractDungeon.aiRng.randomBoolean(), true), true));
                                    k++;
                                    if (k>2){
                                        break ;
                                    }
                                }
                            }

                    }else {
                        int k = j;
                            while (true) {
                                if (k >= 3 + j) {
                                    break label;
                                }
                                this.addToBot(new SpawnMonsterAction(new Gear(200F * k - 1200F, y, 2f, AbstractDungeon.aiRng.randomBoolean(), true), true));
                                k++;
                            }
                    }
                }

        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (from==1){
            if (AbstractDungeon.ascensionLevel >= 19){
                if (this.lastMove((byte) 2) && this.lastMoveBefore((byte) 1)){
                    this.MOVE1and2++;
                }
                if (this.lastMove((byte) 3) && this.lastMoveBefore((byte) 2)){
                    this.MOVE2and3++;
                }
                if (this.MOVE5){
                    if (!this.lastTwoMoves((byte) 2)){
                        this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                    }else if(!this.lastTwoMoves((byte) 3)){
                        this.setMove(MOVES[3], (byte) 3, Intent.DEFEND_BUFF);
                    }else
                        this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
                else if (this.lastMove((byte) 1) && this.MOVE1and2<2){
                    this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }else if (this.lastMove((byte) 2) && this.MOVE1and2<2){
                    this.setMove(MOVES[1], (byte) 1, Intent.UNKNOWN);
                }else if (this.MOVE1and2>=2 && this.MOVE2and3<2 && this.lastMove((byte) 2)){
                    this.setMove(MOVES[3], (byte) 3, Intent.DEFEND_BUFF);
                }else if (this.MOVE1and2>=2 && this.MOVE2and3<2 && this.lastMove((byte) 3)){
                    this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }else if (this.MOVE1and2>=2 && this.MOVE2and3>=2 && this.MOVE4<=1){
                    this.setMove(MOVES[4], (byte) 4, Intent.UNKNOWN);
                    this.MOVE4++;
                }else if (this.MOVE1and2>=2 && this.MOVE2and3>=2 && this.MOVE4>1){
                    this.setMove(MOVES[5], (byte) 5, Intent.BUFF);
                    this.MOVE5=true;
                }
                else
                    this.setMove(MOVES[1], (byte) 1, Intent.UNKNOWN);
                     
            }else {
                if (this.lastMove((byte) 2) && this.lastMoveBefore((byte) 1)){
                    this.MOVE1and2++;
                }
                if (this.lastMove((byte) 3) && this.lastMoveBefore((byte) 2)){
                    this.MOVE2and3++;
                }
                if (this.MOVE5){
                    if (!this.lastTwoMoves((byte) 2)){
                        this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                    }else if(!this.lastTwoMoves((byte) 3)){
                        this.setMove(MOVES[3], (byte) 3, Intent.DEFEND_BUFF);
                    }else
                        this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }
                else if (this.lastMove((byte) 1) && this.MOVE1and2<2){
                    this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }else if (this.lastMove((byte) 2) && this.MOVE1and2<2){
                    this.setMove(MOVES[1], (byte) 1, Intent.UNKNOWN);
                }else if (this.MOVE1and2>=2 && this.MOVE2and3<2 && this.lastMove((byte) 2)){
                    this.setMove(MOVES[3], (byte) 3, Intent.DEFEND_BUFF);
                }else if (this.MOVE1and2>=2 && this.MOVE2and3<2 && this.lastMove((byte) 3)){
                    this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }else if (this.MOVE1and2>=2 && this.MOVE2and3>=2 && this.MOVE4<=2){
                    this.setMove(MOVES[4], (byte) 4, Intent.UNKNOWN);
                    this.MOVE4++;
                }else if (this.MOVE1and2>=2 && this.MOVE2and3>=2 && this.MOVE4>2){
                    this.setMove(MOVES[5], (byte) 5, Intent.BUFF);
                    this.MOVE5=true;
                }
                else
                    this.setMove(MOVES[1], (byte) 1, Intent.UNKNOWN);
            }
        }else {
            int j = 0;
            Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
            while (true){
                if (!var2.hasNext()) {
                    break;
                }
                AbstractMonster mo = (AbstractMonster)var2.next();
                if (mo instanceof Gear && !mo.isDying){
                    j++;
                }
            }
            if (j<=5 && !this.lastTwoMoves((byte) 9)){
                this.setMove(MOVES[9], (byte) 9, Intent.UNKNOWN);
            }else if (!this.lastTwoMoves((byte) 7)){
                this.setMove(MOVES[7], (byte) 7, Intent.STRONG_DEBUFF);
            }else if (!this.lastTwoMoves((byte) 8) || this.lastMove((byte) 5)){
                this.setMove(MOVES[8], (byte) 8, Intent.ATTACK, (this.damage.get(2)).base,this.HitTime,true);
            }else if (!this.lastTwoMoves((byte) 5)){
                this.setMove(MOVES[5], (byte) 5, Intent.BUFF);
            }
            else
                this.setMove(MOVES[7], (byte) 7, Intent.STRONG_DEBUFF);

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

    private void movePosition(float x, float y){
        this.drawX = x;
        this.drawY = y;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.refreshHitboxLocation();
    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.playDeathSfx();
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            this.onBossVictoryLogic();
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

    public void damage (DamageInfo info){
        super.damage(info);
        if (this.currentHealth <= 0 && AbstractDungeon.getCurrRoom().cannotLose) {
            this.halfDead = true;
            Iterator s = this.powers.iterator();

            AbstractPower p;
            while (s.hasNext()) {
                p = (AbstractPower) s.next();
                p.onDeath();
            }

            s = AbstractDungeon.player.relics.iterator();

            while (s.hasNext()) {
                AbstractRelic r = (AbstractRelic) s.next();
                r.onMonsterDeath(this);
            }

            this.addToTop(new ClearCardQueueAction());
            s = this.powers.iterator();

            while (true) {
                do {
                    if (!s.hasNext()) {
                        if (this.from == 1) {
                            this.setMove((byte) 6, Intent.UNKNOWN);
                            this.createIntent();
                            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[2]));
                            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 6, Intent.UNKNOWN));
                            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                            while (true){
                                if (!var1.hasNext()) {
                                    break;
                                }
                                AbstractMonster mo = (AbstractMonster)var1.next();
                                if (mo instanceof Gear && !mo.isDying){
                                    AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(mo));
                                    AbstractDungeon.actionManager.addToTop(new SuicideAction(mo));
                                    AbstractDungeon.actionManager.addToTop(new VFXAction(mo, new InflameEffect(mo), 0.01F));
                                }

                            }
                            this.applyPowers();
                            return;
                        }
                    }

                    p = (AbstractPower) s.next();
                } while (p.type != AbstractPower.PowerType.DEBUFF);

                s.remove();
            }
        }
    }


}
