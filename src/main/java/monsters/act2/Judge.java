package monsters.act2;

import actions.MovePositionAction;
import actions.WaitActionPassFast;
import basemod.BaseMod;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
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
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import powers.DodgePower;
import vfx.ColorfulSmokeBombEffect;
import vfx.ColourfulLightingEffect;

import java.util.Iterator;

public class Judge extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:Judge";
    public static final String FROM1 = "img/monsters/judge.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:Judge");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String CHARM1 = "img/vfxs/charm1.png";
    public static final String CHARM2 = "img/vfxs/charm2.png";
    public static final String WATER0 = "img/vfxs/water_vfx_0.png";
    public static final String WATER1 = "img/vfxs/water_vfx_1.png";
    public static final String WATER2 = "img/vfxs/water_vfx_2.png";
    public static final String WATER3 = "img/vfxs/water_vfx_3.png";
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 150;
    private static final int HP_MAX = 158;
    private static final int A_HP_MIN = 155;
    private static final int A_HP_MAX = 165;
    private static final int DMG1 = 10;
    private static final int A_DMG1 = 12;
    private static final int DMG2 = 13;
    private static final int A_DMG2  = 14;
    private static final int DMG3 = 7777777;
    private static final int A_DMG3  = 7777777;
    private static final int HITS = 3;
    private static final int A_HITS = 3;
    private static final int BLOCK1 = 4;
    private static final int A_BLOCK1 = 6;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 1;
    private static final int MGC2 = 2;
    private static final int A_MGC2 = 2;
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
    private boolean foggy;
    private boolean hasmirage;
    public AbstractMonster[] uav = new AbstractMonster[3];
    public static  float[] POSX={-366.0F, -170.0F, -532.0F};
    public static  float[] POSY={-4.0F, 6.0F, 0.0F};
    public float origDX;
    public float origdY;
    public float orighX;
    public float orighY;
    public float orighW;
    public float orighH;


    public Judge() {
        this(0.0F, 0.0F);
    }
    public Judge(float x, float y) {//Elite
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
        this.origDX = this.drawX;
        this.origdY = this.drawY;
        this.orighX = this.hb.x;
        this.orighY = this.hb.y;
        this.orighW = this.hb_w;
        this.orighH = this.hb_h;
        this.foggy=false;
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("zmxy/zmxy2-2.ogg");
    }

    private void movePosition(float x, float y){
        this.drawX = x;
        this.drawY = y;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.refreshHitboxLocation();
    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                //smooth light
                this.addToBot(new SpawnMonsterAction(new Lamp(-400.0F, 615.0F,2 ), true));
                this.addToBot(new SpawnMonsterAction(new Lamp(-160.0F, 615.0F,3 ), true));
                this.addToBot(new SpawnMonsterAction(new Lamp(-60.0F, 590.0F,4 ), true));
                this.addToBot(new SpawnMonsterAction(new Lamp(-500.0F, 590.0F,5 ), true));
                this.addToBot(new SpawnMonsterAction(new Lamp(-680.0F, 548.0F,6 ), true));
                this.addToBot(new SpawnMonsterAction(new Lamp(115F, 548.0F,7 ), true));
                this.addToBot(new SpawnMonsterAction(new Lamp(-280.0F, 440.0F,8 ), true));
                this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
                //BaseMod.logger.info("f dialog x"+this.dialogX);//0
                //BaseMod.logger.info("f dialog x"+this.dialogY);//0
                break ;
            case 2:
                //mirage judge
                //
                //this.drawX = (float)Settings.WIDTH * 0.75F + offsetX * Settings.xScale;
                //this.drawY = AbstractDungeon.floorY + offsetY * Settings.yScale;
                this.foggy=true;
                //this.addToBot this.addToTop
                for (float i = 0; i < 2000f; i=i+100f) {
                    this.addToTop( new VFXAction(new ColorfulSmokeBombEffect(this.orighX - 600F+i, this.orighY + 50.0F
                            ,0.5f, 0.6f, 0.0f, 0.2f, 0.5f, 0.7f)));
                }
                //this.addToBot(new WaitActionPassFast(3.0F));
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    this.addToBot(new SpawnMonsterAction(new MirageJudge(-450.0F,0.0F),false));
                    this.addToBot(new MovePositionAction(this,(float)Settings.WIDTH * 0.75F + 70 * Settings.xScale,AbstractDungeon.floorY));
                    //movePosition((float)Settings.WIDTH * 0.75F + 70 * Settings.xScale,AbstractDungeon.floorY);
                }else {
                    this.addToBot(new SpawnMonsterAction(new MirageJudge(70.0F,0.0F),false));
                    this.addToBot(new MovePositionAction(this,(float)Settings.WIDTH * 0.75F + -450 * Settings.xScale,AbstractDungeon.floorY));
                    //movePosition((float)Settings.WIDTH * 0.75F + -450 * Settings.xScale,AbstractDungeon.floorY);
                }
                //if (AbstractDungeon.aiRng.randomBoolean()){
                //    moveMiragePosition((float)Settings.WIDTH * 0.75F + -450 * Settings.xScale,AbstractDungeon.floorY);
                //    movePosition((float)Settings.WIDTH * 0.75F + 70 * Settings.xScale,AbstractDungeon.floorY);
                //}else {
                //    moveMiragePosition((float)Settings.WIDTH * 0.75F + 70 * Settings.xScale,AbstractDungeon.floorY);
                //    movePosition((float)Settings.WIDTH * 0.75F + -450 * Settings.xScale,AbstractDungeon.floorY);
                //}
                break ;
            case 3:
                //dodge
                this.addToBot(new ApplyPowerAction(this,this,new  DodgePower(this,true)));
                break ;
            case 4:
                //attack lighting
                AbstractGameEffect C1 = new VfxBuilder(ImageMaster.loadImage(CHARM1), 1.0f)
                        .scale(2.0f, 0.2f, VfxBuilder.Interpolations.SWING)
                        .moveX(this.drawX,this.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(this.drawY,this.drawY+1500f, VfxBuilder.Interpolations.EXP5IN)
                        .build();
                this.addToBot(new VFXAction(C1));
                this.addToBot(new WaitActionPassFast(1.0f));
                this.addToBot(new VFXAction(new ColourfulLightingEffect(p.drawX,p.drawY,Color.PURPLE)));
                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(p,this,new StrengthPower(p,-this.magicAmt1),-this.magicAmt1));
                break ;
            case 5:
                WaterVFX();
                this.addToBot(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.magicAmt2, true), this.magicAmt2));
                break ;
            case 6:
                int j = 0;
                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var2.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var2.next();
                    if (mo instanceof Lamp && !mo.isDying){
                        j++;
                    }
                }
                if (j==0){
                    this.addToBot((new TalkAction(this , DIALOG[1], 2.5F, 2.5F)));
                    this.addToBot(new VFXAction(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F))));
                    this.addToBot(new VFXAction(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F))));
                    this.addToBot(new VFXAction(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F))));
                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM");
                    this.addToBot(new VFXAction(new HeartMegaDebuffEffect()));
                    this.addToBot(new WaitActionPassFast(2.0F));
                    this.addToBot(new LoseHPAction(p, p, this.Dmg3));
                }
                BaseMod.logger.info("u dialog x"+this.dialogX);
                BaseMod.logger.info("u dialog y"+this.dialogY);
                break;


        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        AbstractCreature p = AbstractDungeon.player;
        int j = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
        while (true){
            if (!var2.hasNext()) {
                break;
            }
            AbstractMonster mo = (AbstractMonster)var2.next();
            if (mo instanceof Lamp && !mo.isDying){
                j++;
            }
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            if (turn_count==1){
                this.setMove(MOVES[1],(byte) 1,Intent.UNKNOWN);
            }else {
                if (j==0 && !lastTwoMoves((byte) 6) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[6], (byte) 6, Intent.ATTACK, (this.damage.get(2)).base);
                    }else {
                        this.setMove(MOVES[11], (byte) 6, Intent.NONE);
                    }
                } else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 2) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[7], (byte) 2, Intent.BUFF);
                    }else {
                        this.setMove(MOVES[7], (byte) 2, Intent.NONE);
                    }
                }else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 3) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[3],(byte) 3,Intent.BUFF);
                    }else {
                        this.setMove(MOVES[8],(byte) 3,Intent.NONE);
                    }
                }else if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 4) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[4],(byte) 4,Intent.ATTACK_DEBUFF,(this.damage.get(0)).base);
                    }else {
                        this.setMove(MOVES[9],(byte) 4,Intent.NONE);
                    }
                }else if (!lastTwoMoves((byte) 5) ){
                    this.setMove(MOVES[5],(byte) 5,Intent.ATTACK_DEBUFF,(this.damage.get(1)).base);
                    if (!hasMirage()) {
                        this.setMove(MOVES[5],(byte) 5,Intent.ATTACK_DEBUFF,(this.damage.get(1)).base);
                    }else {
                        this.setMove(MOVES[10],(byte) 5,Intent.NONE);
                    }
                }
            }
        }else {
            if (turn_count==1){
                this.setMove(MOVES[1],(byte) 1,Intent.UNKNOWN);
            }else {
                if (j==0 && !lastTwoMoves((byte) 6) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[6], (byte) 6, Intent.ATTACK, (this.damage.get(2)).base);
                    }else {
                        this.setMove(MOVES[11],(byte) 6, Intent.NONE);
                    }
                } else if (AbstractDungeon.aiRng.randomBoolean(0.5F) && !lastTwoMoves((byte) 2) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[7], (byte) 2, Intent.BUFF);
                    }else {
                        this.setMove(MOVES[7],(byte) 2, Intent.NONE);
                    }
                }else if (AbstractDungeon.aiRng.randomBoolean(0.45F) && !lastTwoMoves((byte) 3) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[3],(byte) 3,Intent.BUFF);
                    }else {
                        this.setMove(MOVES[8],(byte) 3,Intent.NONE);
                    }
                }else if (AbstractDungeon.aiRng.randomBoolean(0.45F) && !lastTwoMoves((byte) 4) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[4],(byte) 4,Intent.ATTACK_DEBUFF,(this.damage.get(0)).base);
                    }else {
                        this.setMove(MOVES[9],(byte) 4,Intent.NONE);
                    }
                }else if (!lastTwoMoves((byte) 5) ){
                    if (!hasMirage()) {
                        this.setMove(MOVES[5],(byte) 5,Intent.ATTACK_DEBUFF,(this.damage.get(1)).base);
                    }else {
                        this.setMove(MOVES[10],(byte) 5,Intent.NONE);
                    }
                }
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
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.playDeathSfx();
            this.useFastShakeAnimation(5.0F);
            this.addToTop(new HideHealthBarAction(this));
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (m instanceof MirageJudge)
                    m.isDead = true;
            }
        }
    }


    private void WaterVFX(){
        AbstractCreature p = AbstractDungeon.player;
        AbstractGameEffect C2 = new VfxBuilder(ImageMaster.loadImage(CHARM2), 1.2f)
                .scale(2.0f, 0.2f, VfxBuilder.Interpolations.SWING)
                .moveX(this.drawX,this.drawX, VfxBuilder.Interpolations.LINEAR)
                .moveY(this.drawY,this.drawY+1500f, VfxBuilder.Interpolations.LINEAR)
                .rotate(300f)
                .build();
        this.addToBot(new VFXAction(C2));
        this.addToBot(new WaitActionPassFast(1.0F));
        AbstractGameEffect W0 = new VfxBuilder(ImageMaster.loadImage(WATER0), 0.2f)
                .scale(4.0f, 4.0f, VfxBuilder.Interpolations.SWING)
                .moveX(p.drawX+800.0f, p.drawX+600.0f, VfxBuilder.Interpolations.LINEAR)
                .moveY(this.drawY, p.drawY, VfxBuilder.Interpolations.LINEAR)
                .build();
        this.addToBot(new VFXAction(W0));
        AbstractGameEffect W1 = new VfxBuilder(ImageMaster.loadImage(WATER1), 0.2f)
                .scale(4.0f, 4.0f, VfxBuilder.Interpolations.SWING)
                .moveX(p.drawX+600.0f, p.drawX+400.0f, VfxBuilder.Interpolations.LINEAR)
                .moveY(this.drawY, p.drawY, VfxBuilder.Interpolations.LINEAR)
                .build();
        this.addToBot(new VFXAction(W1));
        AbstractGameEffect W2 = new VfxBuilder(ImageMaster.loadImage(WATER2), 0.2f)
                .scale(4.0f, 4.0f, VfxBuilder.Interpolations.SWING)
                .moveX(p.drawX+400.0f, p.drawX+200.0f, VfxBuilder.Interpolations.LINEAR)
                .moveY(this.drawY, p.drawY, VfxBuilder.Interpolations.LINEAR)
                .build();
        this.addToBot(new VFXAction(W2));
        AbstractGameEffect W3 = new VfxBuilder(ImageMaster.loadImage(WATER3), 0.2f)
                .scale(4.0f, 4.0f, VfxBuilder.Interpolations.SWING)
                .moveX(p.drawX+200.0f, p.drawX, VfxBuilder.Interpolations.LINEAR)
                .moveY(this.drawY, p.drawY, VfxBuilder.Interpolations.LINEAR)
                .build();
        this.addToBot(new VFXAction(W3));
    }



    private void moveMiragePosition(float x, float y){
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
        while (true){
            if (!var1.hasNext()) {
                break;
            }
            AbstractMonster mo = (AbstractMonster)var1.next();
            if (mo instanceof MirageJudge && !mo.isDying){
                mo.drawX = x;
                mo.drawY = y;
                mo.dialogX = mo.drawX + 0.0F * Settings.scale;
                mo.dialogY = mo.drawY + 170.0F * Settings.scale;
                mo.hb.move(mo.drawX + mo.hb_x + mo.animX, mo.drawY + mo.hb_y + mo.hb_h / 2.0F);
                mo.healthHb.move(mo.hb.cX, mo.hb.cY - mo.hb_h / 2.0F - mo.healthHb.height / 2.0F);
            }
        }

    }

    private float getMirageXPosition(){
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
        while (true){
            if (!var1.hasNext()) {
                break;
            }
            AbstractMonster mo = (AbstractMonster)var1.next();
            if (mo instanceof MirageJudge && !mo.isDying){
                return ((mo.drawX-(float)Settings.WIDTH * 0.75F))/Settings.xScale;

            }
        }
        return 0;
    }

    private float getMirageYPosition(){
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
        while (true){
            if (!var1.hasNext()) {
                break;
            }
            AbstractMonster mo = (AbstractMonster)var1.next();
            if (mo instanceof MirageJudge && !mo.isDying){
                return ((this.drawY-AbstractDungeon.floorY)/Settings.yScale);
            }
        }
        return 0;
    }

    private boolean hasMirage(){
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
        while (true){
            if (!var1.hasNext()) {
                break;
            }
            AbstractMonster mo = (AbstractMonster)var1.next();
            if (mo instanceof MirageJudge && !mo.isDying){
                return true;
            }
        }
        return false;
    }

    public void damage (DamageInfo info) {
        super.damage(info);
        if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != null && (mo instanceof MirageJudge)) {
                    this.addToBot(new SuicideAction(mo));
                    this.foggy=false;
                }
            }
        }
        hasmirage =false;
        if (this.moveName != null && MOVES != null && MOVES[2] != null && (this.moveName.equals(MOVES[2])||this.moveName.equals(MOVES[7]))) {
            this.setMove(MOVES[2], (byte) 2, Intent.BUFF);
            this.addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, Intent.BUFF));
        }else if (this.moveName != null && MOVES != null && MOVES[3] != null && (this.moveName.equals(MOVES[3])||this.moveName.equals(MOVES[8]))){
            this.setMove(MOVES[3],(byte) 3,Intent.BUFF);
            this.addToBot(new SetMoveAction(this, MOVES[3], (byte) 3, Intent.BUFF));
        }else if (this.moveName != null && MOVES != null && MOVES[4] != null && (this.moveName.equals(MOVES[4])||this.moveName.equals(MOVES[9]))){
            this.setMove(MOVES[4],(byte) 4,Intent.ATTACK_DEBUFF,(this.damage.get(0)).base);
            this.addToBot(new SetMoveAction(this, MOVES[4], (byte) 4, Intent.ATTACK, (this.damage.get(0)).base));
        }else if (this.moveName != null && MOVES != null && MOVES[5] != null && (this.moveName.equals(MOVES[5])||this.moveName.equals(MOVES[10]))){
            this.setMove(MOVES[5],(byte) 5,Intent.ATTACK_DEBUFF,(this.damage.get(1)).base);
            this.addToBot(new SetMoveAction(this, MOVES[5], (byte) 5, Intent.ATTACK, (this.damage.get(1)).base));
        }else if (this.moveName != null && MOVES != null && MOVES[6] != null && (this.moveName.equals(MOVES[6])||this.moveName.equals(MOVES[11]))){
            this.setMove(MOVES[6], (byte) 6, Intent.ATTACK, (this.damage.get(2)).base);
            this.addToBot(new SetMoveAction(this, MOVES[6], (byte) 6, Intent.ATTACK, (this.damage.get(2)).base));
        }
        this.createIntent();
    }



}