package monsters.act4;

import cards.BlownSand;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import powers.PoisonousPillPower;
import vfx.ColorfulBloodShotEffect;
import vfx.ColorfulLaserBeamEffect;

import java.util.Iterator;

public class TheSovereignofTheTongtianSect extends AbstractMonster {
    public static final String ID = "dreaming_journey_to_the_west:TheSovereignofTheTongtianSect";
    public static final String IMG = "img/monsters/the_sovereign_of_the_tongtian_sect.png";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public int turn_count = 0;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 480;
    private static final int HP_MAX = 520;
    private static final int A_2_HP_MIN = 510;
    private static final int A_2_HP_MAX = 550;
    private static final int DMG1 = 2;
    private static final int A_2_DMG1 = 2;
    private static final int HITS = 12;
    private static final int A_4_HIT = 15;
    private static final int DMG2 =40;
    private static final int A_2_DMG2  = 50;
    private static final int BLOCK1 = 0;
    private static final int BLOCK2 = 0;
    private static final int MGC1 = 2;
    private static final int A_18_MGC1 = 2;
    private static final int MGC2 = 2;
    private static final int A_18_MGC2 = 3;
    private static final int STRENGTH = 6;
    private int Dmg1;
    private int Dmg2;
    private int HitTime;
    private int magicAmt1;
    private int magicAmt2;
    private int Invincible_Amount = 300;
    private int A_17_BLOCK_AMOUNT = 20;
    private int str = STRENGTH;
    private boolean s1 = false;
    private boolean s2 = false;
    private boolean s3 = false;
    private boolean s4 = false;
    private boolean has_sword = true;
    private int buffCount = 0;
    private boolean SMM_SWORD =false;
    private boolean isFirstMove = true;
    private boolean is_heal = false;
    private boolean first_break = true;

    public TheSovereignofTheTongtianSect() {
        this(0.0F, 0.0F);
    }
    public TheSovereignofTheTongtianSect(float x, float y) {//Elite
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0.0F, 0.0F, 280.0F, 300.0F, IMG,x,y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(HP_MIN, HP_MAX);
        } else {
            setHp(A_2_HP_MIN, A_2_HP_MAX);
        }
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.Dmg1 = A_2_DMG1;
            this.Dmg2 = A_2_DMG2;
            this.HitTime = A_4_HIT;
        } else {
            this.Dmg1 = DMG1;
            this.Dmg2 = DMG2;
            this.HitTime=HITS;
        }
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.magicAmt1 = A_18_MGC1;
            this.magicAmt2 = A_18_MGC2;
            this.Invincible_Amount = 200;
        } else {
            this.magicAmt1 = MGC1;
            this.magicAmt2 = MGC2;
            this.Invincible_Amount = 240;
        }
        this.damage.add(new DamageInfo(this, this.Dmg1));
        this.damage.add(new DamageInfo(this, this.Dmg2));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.playTempBgmInstantly("MINDBLOOM", true);
        this.addToBot(new ApplyPowerAction(this,this,new InvinciblePower(this,this.Invincible_Amount),this.Invincible_Amount));
        this.addToBot(new ApplyPowerAction(this,this,new PoisonousPillPower(this)));
        this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,-5),-5));

    }


    @Override
    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        switch(this.nextMove) {
            case 1:
                //???
                this.addToBot((new TalkAction(this,DIALOG[0], 2.5F, 2.5F)));
                this.addToBot(new WaitAction(3F));
                this.addToBot((new TalkAction(this,DIALOG[1], 2.5F, 2.5F)));
                this.addToBot(new WaitAction(3F));

                this.addToBot(new SpawnMonsterAction(new ExterminateImmortalSword(-300F, 75.0F), true));
                this.addToBot(new SpawnMonsterAction(new AnnihilateImmortalSword(-200.0F, 340.0F), true));
                this.addToBot(new SpawnMonsterAction(new TrapImmortalSword(200.0F, 340F), true));
                this.addToBot(new SpawnMonsterAction(new SlayingImmortalSword(300.0F, 75.0F), true));
                break;
            case 2:
                //ATK_HITS
                if (this.first_break){
                    this.addToBot((new TalkAction(this,DIALOG[2], 2.5F, 2.5F)));
                    this.addToBot(new WaitAction(3.5F));
                    this.first_break = false;
                }
                if (Settings.FAST_MODE) {
                    this.addToBot(new VFXAction(new ColorfulBloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.HitTime), 0.25F));
                } else {
                    this.addToBot(new VFXAction(new ColorfulBloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.HitTime), 0.6F));
                }
                for(int i = 0; i < this.HitTime; ++i) {
                    this.addToBot(new DamageAction(p, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                }
                break;
            case 3:
                //ATK JI QI REN GUANG BO
                if (this.first_break){
                    this.addToBot((new TalkAction(this,DIALOG[2], 2.5F, 2.5F)));
                    this.addToBot(new WaitAction(3.5F));
                    this.first_break = false;
                }
                this.addToBot(new VFXAction(new ColorfulLaserBeamEffect(this.hb.cX, this.hb.cY + 60.0F * Settings.scale,Color.PURPLE), 1.5F));
                this.addToBot(new DamageAction(p, (DamageInfo) this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                break;
            case 4:
                //BF to Sword BUFF_DEFENCE
                this.str = STRENGTH ;//5
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var1.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var1.next();
                    if (mo instanceof ExterminateImmortalSword && !mo.isDying){//诛仙剑
                        this.str = this.str-1;
                    }
                    if (mo instanceof AnnihilateImmortalSword && !mo.isDying){//绝仙剑
                        this.str = this.str-1;
                    }
                    if (mo instanceof TrapImmortalSword && !mo.isDying){//陷仙剑
                        this.str = this.str-1;
                    }
                    if (mo instanceof SlayingImmortalSword && !mo.isDying){//戮仙剑
                        this.str = this.str-1;
                    }
                }
                while(true) {
                    if (!var1.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var1.next();
                    this.addToBot(new ApplyPowerAction(mo,this,new StrengthPower(mo,Math.max(this.str,1)),Math.max(this.str,1)));
                }
                this.addToBot(new GainBlockAction(this,12));
                break;
            case 5:
                //ADD  CARD or DEBUFF
                this.addToBot(new VFXAction(new HeartMegaDebuffEffect()));
                this.s1=false;
                this.s2=false;
                this.s3=false;
                this.s4=false;
                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
                while (true){
                    if (!var2.hasNext()) {
                        break;
                    }
                    AbstractMonster mo = (AbstractMonster)var2.next();
                    if (mo instanceof ExterminateImmortalSword && !mo.isDying){//诛仙剑
                        this.s1=true;
                    }
                    if (mo instanceof AnnihilateImmortalSword && !mo.isDying){//绝仙剑
                        this.s2=true;
                    }
                    if (mo instanceof TrapImmortalSword && !mo.isDying){//陷仙剑
                        this.s3=true;
                    }
                    if (mo instanceof SlayingImmortalSword && !mo.isDying){//戮仙剑
                        this.s4=true;
                    }
                }
                if (this.s1){
                    this.addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false, true, (float) Settings.WIDTH * 0.2F, (float)Settings.HEIGHT / 2.0F));
                }else {
                    this.addToBot(new ApplyPowerAction(p,this,new  VulnerablePower(p,this.magicAmt1,true),this.magicAmt1));
                }
                if (this.s2){
                    this.addToBot(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, false, true, (float) Settings.WIDTH * 0.2F, (float)Settings.HEIGHT / 2.0F));
                }else {
                    this.addToBot(new ApplyPowerAction(p,this,new WeakPower(p,this.magicAmt1,true),this.magicAmt1));
                }
                if (this.s3){
                    this.addToBot(new MakeTempCardInDrawPileAction(new BlownSand(), 1, true, false, true, (float) Settings.WIDTH * 0.2F, (float)Settings.HEIGHT / 2.0F));
                }else {
                    this.addToBot(new ApplyPowerAction(p,this,new NoBlockPower(p,1,true)));
                }
                if (this.s4){
                    this.addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, false, true, (float) Settings.WIDTH * 0.2F, (float)Settings.HEIGHT / 2.0F));
                }else {
                    this.addToBot(new ApplyPowerAction(p,this,new FrailPower(p,this.magicAmt1,true),this.magicAmt1));
                }
                break;
            case 6:
                //AMPLIFY NONE SWORD BUFF
                this.has_sword = false;
                this.s1=false;
                this.s2=false;
                this.s3=false;
                this.s4=false;
                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var3.hasNext()){
                    AbstractMonster mo = (AbstractMonster)var3.next();
                    if (mo instanceof ExterminateImmortalSword && !mo.isDying){//诛仙剑
                        this.s1=true;
                    }
                    if (mo instanceof AnnihilateImmortalSword && !mo.isDying){//绝仙剑
                        this.s2=true;
                    }
                    if (mo instanceof TrapImmortalSword && !mo.isDying){//陷仙剑
                        this.s3=true;
                    }
                    if (mo instanceof SlayingImmortalSword && !mo.isDying){//戮仙剑
                        this.s4=true;
                    }
                    if (this.s1 || this.s2 || this.s3 || this.s4){
                        this.has_sword = true;
                    }
                }
                if (this.has_sword){
                    switch(this.buffCount) {
                        case 0:
                            this.addToBot(new VFXAction(new BorderFlashEffect(new Color(0.86F,0.29F,0.03F,0.7F))));
                            this.addToBot(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                            this.addToBot(new ApplyPowerAction(this,this,new ArtifactPower(this,this.magicAmt1),this.magicAmt1));
                            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                            break;
                        case 1:
                            this.addToBot(new VFXAction(new BorderFlashEffect(new Color(0.35F,0.98F,0.86F,0.7F))));
                            this.addToBot(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                            break;
                        case 2:
                            this.addToBot(new VFXAction(new BorderFlashEffect(new Color(0.65F,0.35F,0.98F,0.7F))));
                            this.addToBot(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
                            break;
                        case 3:
                            this.addToBot(new VFXAction(new BorderFlashEffect(new Color(1.0F,0.12F,0.67F,0.7F))));
                            this.addToBot(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 4), 4));
                            break;
                        default:
                            if (is_heal){
                                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 12), 12));
                            }
                            else {
                                this.addToBot(new VFXAction(new BorderFlashEffect(new Color(1.0F,0.1F,0.1F,0.6F))));
                                this.addToBot(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 5), 5));
                                this.addToBot(new RemoveSpecificPowerAction(this, this, "dreaming_journey_to_the_west:PoisonousPillPower"));
                                this.addToBot(new HealAction(this, this, (int) (this.maxHealth * 0.75)));
                                this.addToBot((new TalkAction(this, DIALOG[3], 2.5F, 2.5F)));
                                this.is_heal = true;
                            }
                    }
                    ++this.buffCount;
                }
                else {
                    this.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, this.magicAmt2+this.buffCount), this.magicAmt2+this.buffCount));
                    this.addToBot(new ApplyPowerAction(this,this,new BeatOfDeathPower(this,1),1));
                    this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,1),1));
                }
                break;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(this.isFirstMove){
            this.setMove(MOVES[0], (byte) 1,Intent.UNKNOWN);
            this.isFirstMove = false;
        }else {
            if (this.has_sword){
                if ((lastMove((byte) 4)&&lastMoveBefore((byte) 5))||(lastMove((byte) 5)&&lastMoveBefore((byte) 4))){
                    this.setMove(MOVES[5], (byte) 6, Intent.BUFF);
                }else {
                    if (AbstractDungeon.aiRng.randomBoolean()) {
                        this.setMove(MOVES[3], (byte) 4, Intent.DEFEND_BUFF);
                    } else {
                        this.setMove(MOVES[4], (byte) 5, Intent.STRONG_DEBUFF);
                    }
                    if (this.lastMove((byte) 4)) {
                        this.setMove(MOVES[4], (byte) 5, Intent.STRONG_DEBUFF);
                    } else {
                        this.setMove(MOVES[3], (byte) 4, Intent.DEFEND_BUFF);
                    }
                }
            }else {
                if ((lastMove((byte) 2)&& lastMoveBefore((byte) 3))||(lastMove((byte) 3)&&lastMoveBefore((byte) 2))){
                    this.setMove(MOVES[6], (byte) 6, Intent.BUFF);
                }else {
                    if (this.lastMove((byte) 3)) {
                        this.setMove(MOVES[1], (byte) 2, Intent.ATTACK, (this.damage.get(0)).base,this.HitTime,true);
                    } else {
                        this.setMove(MOVES[2], (byte) 3, Intent.ATTACK, (this.damage.get(1)).base);

                    }

                }
            }
        }
        ++this.turn_count;
    }

    @Override
    public void die() {
        CardCrawlGame.screenShake.rumble(4.0F);
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.onBossVictoryLogic();
            this.onFinalBossVictoryLogic();
            CardCrawlGame.stopClock = true;
        }

    }

    public static boolean isSword(AbstractMonster monster) {
        String ID = monster.id;
        if (    ID.equals("dreaming_journey_to_the_west:ExterminateImmortalSword")||
                ID.equals("dreaming_journey_to_the_west:AnnihilateImmortalSword")||
                ID.equals("dreaming_journey_to_the_west:TrapImmortalSword")||
                ID.equals("dreaming_journey_to_the_west:SlayingImmortalSword")
        ){
            return true;
        }else {
            return false;
        }
    }


}
