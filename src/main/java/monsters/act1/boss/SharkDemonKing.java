package monsters.act1.boss;

import actions.SpawnSharkWithPowerAction;
import actions.WaitActionPassFast;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import powers.BloodSeaArmorPower;

import java.util.Iterator;

public class SharkDemonKing extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:SharkDemonKing";
    public static final String FROM1 = "img/monsters/shark_demon_king.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:SharkDemonKing");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String BUBBLE = "img/vfxs/bubble.png";
    public static final String SHARK_UP = "img/vfxs/shark_up.png";
    public static final String SHARK_DOWN = "img/vfxs/shark_down.png";
    public static final String WIND = "img/vfxs/wind.png";
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 230;
    private static final int HP_MAX = 240;
    private static final int A_HP_MIN = 240;
    private static final int A_HP_MAX = 250;
    private static final int DMG1 = 3;
    private static final int A_DMG1 = 3;
    private static final int DMG2 = 16;
    private static final int A_DMG2  = 18;
    private static final int DMG3 = 3;
    private static final int A_DMG3  = 3;
    private static final int HITS = 4;
    private static final int A_HITS = 5;
    private static final int BLOCK1 = 16;
    private static final int A_BLOCK1 = 18;
    private static final int BLOCK2 = 7;
    private static final int A_BLOCK2 = 10;
    private static final int MGC1 = 1;
    private static final int A_MGC1 = 1;
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


    public SharkDemonKing() {
        this(0.0F, 0.0F);
    }
    public SharkDemonKing(float x, float y) {//Elite
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
        this.not_remove = 0;

    }

    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        this.addToBot(new ApplyPowerAction(this,this,new  BloodSeaArmorPower(this,1)));

    }

    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label:
        switch(this.nextMove) {
            case 1:
                //attack hits
                AbstractGameEffect S_U = new VfxBuilder(ImageMaster.loadImage(SHARK_UP), 1.2f)
                        .scale(4.0f, 4.0f, VfxBuilder.Interpolations.SWING)
                        .moveX(this.drawX,this.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(this.drawY,this.drawY+1500f, VfxBuilder.Interpolations.EXP5IN)
                        .build();
                this.addToBot(new VFXAction(S_U));
                this.addToBot(new WaitActionPassFast(1.0F));
                int i = 0;
                while(true) {
                    if (i >= this.HitTime) {
                        break label;
                    }
                    AbstractGameEffect S_D = new VfxBuilder(ImageMaster.loadImage(SHARK_DOWN), 0.3f)
                            .scale(1.8f, 2.0f, VfxBuilder.Interpolations.SWING)
                            .moveX(p.drawX+(-100+(float) Math.random()*200),p.drawX+(-100+(float) Math.random()*200), VfxBuilder.Interpolations.EXP5IN)
                            .moveY(p.drawY+900f,p.drawY-10f, VfxBuilder.Interpolations.EXP5IN)
                            .build();
                    this.addToBot(new VFXAction(S_D));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 30.0F) * Settings.scale, Color.BLUE.cpy()), 0.1F));
                    this.addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    i++;
                }
            case 2:
                //attack
                AbstractGameEffect W = new VfxBuilder(ImageMaster.loadImage(WIND), 0.8f)
                        .scale(3.5F, 4.0f, VfxBuilder.Interpolations.SWING)
                        .moveX(this.drawX,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(p.drawY,p.drawY, VfxBuilder.Interpolations.EXP5IN)
                        .playSoundAt(0.35F,"WIND")
                        .build();
                this.addToBot(new VFXAction(W));
                this.addToBot(new WaitActionPassFast(0.7F));
                this.addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;

            case 3:
                //DEBUFF
                AbstractGameEffect B = new VfxBuilder(ImageMaster.loadImage(BUBBLE), 2.0f)
                        .scale(1.5f, 4.0f, VfxBuilder.Interpolations.SWING)
                        .moveX(p.drawX,p.drawX, VfxBuilder.Interpolations.EXP5IN)
                        .moveY(p.drawY+50f,p.drawY+60f, VfxBuilder.Interpolations.EXP5IN)
                        .playSoundAt(0.35F,"ATTACK_MAGIC_BEAM")
                        .build();
                this.addToBot(new VFXAction(B));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new EntanglePower(AbstractDungeon.player)));
                break;

            case 4:
                //remove strength
                if (this.hasPower("Strength") &&  this.getPower("Strength") !=null){
                    this.strength_num = this.getPower("Strength").amount;
                }
                this.addToBot(new ApplyPowerAction(this,this, new StrengthPower(this,-this.strength_num), -this.strength_num));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.getMonsters().getMonster(Shark.ID),this, new StrengthPower(this,this.strength_num), this.strength_num));
                break;
            case 5:
                //shark
                this.addToBot(new SpawnSharkWithPowerAction(new Shark(-250.0F, 150.0F), true));
                this.shark=true;
                break ;




        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        ++turn_count;
        if (!shark){
            this.setMove(MOVES[5], (byte) 5, Intent.UNKNOWN);
        }else{
            if (AbstractDungeon.ascensionLevel >= 19){
                if (AbstractDungeon.aiRng.randomBoolean(0.2F) || (not_remove>=5  && AbstractDungeon.aiRng.randomBoolean(0.05F+0.1F*not_remove)) ){//0.2
                    this.setMove(MOVES[4], (byte) 4, Intent.BUFF);
                    this.not_remove = 0;
                }else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 1)){//0.32
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.6F) && !lastTwoMoves((byte) 2)){//0.288
                    this.setMove(MOVES[2],(byte) 2, Intent.ATTACK, (this.damage.get(1)).base);
                }else {//0.192
                    this.setMove(MOVES[3],(byte) 3, Intent.STRONG_DEBUFF);
                }
            }else {
                if ((AbstractDungeon.aiRng.randomBoolean(0.25F) && !lastMove((byte) 4) )|| (not_remove>=4  && AbstractDungeon.aiRng.randomBoolean(0.1F+0.1F*not_remove))){
                    this.setMove(MOVES[4], (byte) 4, Intent.BUFF);//0.25
                    this.not_remove = 0;
                }else if (AbstractDungeon.aiRng.randomBoolean(0.4F) && !lastTwoMoves((byte) 1)){//0.3
                    this.setMove(MOVES[1],(byte) 1, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                }else if (AbstractDungeon.aiRng.randomBoolean(0.7F) && !lastTwoMoves((byte) 2)){//0.315
                    this.setMove(MOVES[2],(byte) 2, Intent.ATTACK, (this.damage.get(1)).base);
                }else {//0.185
                    this.setMove(MOVES[3],(byte) 3, Intent.STRONG_DEBUFF);
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




}